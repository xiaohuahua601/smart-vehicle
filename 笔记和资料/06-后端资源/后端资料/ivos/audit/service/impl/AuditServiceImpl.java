package cn.tedu.ivos.audit.service.impl;

import cn.tedu.ivos.application.mapper.ApplicationMapper;
import cn.tedu.ivos.application.pojo.entity.Application;
import cn.tedu.ivos.audit.mapper.AuditMapper;
import cn.tedu.ivos.audit.pojo.dto.AuditQuery;
import cn.tedu.ivos.audit.pojo.dto.AuditSaveParam;
import cn.tedu.ivos.audit.pojo.entity.Audit;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.enums.ApplicationStatusEnum;
import cn.tedu.ivos.base.enums.AuditStatusEnum;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Transactional
@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ApplicationMapper applicationMapper;


    @Override
    public void insertAudit(Application application) {
        log.debug("新增审批单:application={}",application);
        //application传输的审批人idList(上级 上上级)
        List<Long> userIdList =  application.getAuditUserIdList();
        for(int i = 0; i<userIdList.size(); i++){
            Audit audit = new Audit();
            //为当前审批单设置对应的申请单id
            audit.setApplicationId(application.getId());
            //为当前审批单设置审批人用户id
            audit.setAuditUserId(userIdList.get(i));
            if(i==0){
                //i==0表示第一个审批单,这个审批单需要设置为"待我审核"(当前审批人是申请人的上级)
                audit.setAuditStatus(AuditStatusEnum.MY_PENDING.getCode());
            }else{
                //其他审批单状态为"待他人审核"(当前审批人是申请人的上上级)
                audit.setAuditStatus(AuditStatusEnum.PENDING.getCode());
            }
            //设置审批顺序 上级为0 上上级为1
            audit.setAuditSort(i);
            //审批单数据入库
            auditMapper.insert(audit);
        }
    }

    @Override
    public List<AuditVO> selectAudit(AuditQuery auditQuery) {
        log.debug("查询审批单:auditQuery={}",auditQuery);
        //selectAudit这里要audit与application关联查询大部分数据,但审批人数据需要加工
        List<AuditVO> auditVOList = auditMapper.selectAudit(auditQuery);
        //循环取出每一个查询到的审批单对象
        for (int i = 0; i<auditVOList.size() ; i++){
            AuditVO auditVO = auditVOList.get(i);
            //准备审批人数据
            assignAuditUserList(auditVO);
        }
        return auditVOList;
    }

    //注意:这里就只是更新审批单,新增审批单在新增申请单save()时就已经完成了
    @Override
    public void updateAudit(AuditSaveParam auditSaveParam) {
        //将前端传来的数据赋值给当前的audit对象
        Audit audit = new Audit();
        BeanUtils.copyProperties(auditSaveParam,audit);
        //准备当前审批单对应的申请单对象
        Application application = new Application();
        application.setId(audit.getApplicationId());
        //前端点击"通过"按钮,传过来的数据是auditDialogData.value.auditStatus = 30;
        //如果前端传过来审批单的状态是"已审核"
        if(audit.getAuditStatus().equals(AuditStatusEnum.AUDITED.getCode())){
            //更新入库当前审批单对象的状态
            auditMapper.update(audit);
            /*继续查询其他审批单*/
            AuditQuery auditQuery = new AuditQuery();
            auditQuery.setApplicationId(audit.getApplicationId());
            //根据申请单id查询所有未审批的审批单数量(注意:当前数据已经审批完成!)
            Integer count = auditMapper.selectRestAuditCount(auditQuery);
            if( count>0 ){//还有未审核的数据
                //下一条审批单的sort就是当前审批单的sort+1,这个sort是新增审批单时就设置好的
                auditQuery.setAuditSort(audit.getAuditSort()+1);
                //查询下一条审批单的数据
                List<AuditVO> auditVOList = auditMapper.selectAudit(auditQuery);
                //确保列表不为空,至少包含一条记录
                if(auditVOList !=null && auditVOList.size()>0){
                    //从auditVOList中取出第一条记录(索引为0)
                    AuditVO auditVO = auditVOList.get(0);
                    //创建一个新的audit实体对象,准备更新数据库
                    Audit audit1 = new Audit();
                    audit1.setId(auditVO.getId());
                    audit1.setAuditStatus(AuditStatusEnum.MY_PENDING.getCode());
                    auditMapper.update(audit1);
                }
                //设置车辆申请单的状态是审核中
                application.setStatus(ApplicationStatusEnum.AUDIT.getCode());
                applicationMapper.update(application);
            }else{//没有未审核的数据
                //设置车辆申请单的状态是已审核
                application.setStatus(ApplicationStatusEnum.AUDITED.getCode());
                applicationMapper.update(application);
            }
        }
        else if(audit.getAuditStatus().equals(AuditStatusEnum.REJECT.getCode())){
         //如果前端传过来的审批单状态是40,auditDialogData.value.auditStatus = 40;
         //也就是REJECT,走驳回流程
            auditMapper.update(audit);//更新当前的审批单
            AuditQuery auditQuery = new AuditQuery();
            auditQuery.setApplicationId(audit.getApplicationId());
            //根据申请单id查到所有的审核单
            List<AuditVO> auditVOList = auditMapper.selectAudit(auditQuery);
            //判断是否能查到审批单数据
            if(auditVOList!=null && auditVOList.size()>0){
                //遍历所有审核单
                for(int i = 0 ; i<auditVOList.size();i++){
                    AuditVO auditVO = auditVOList.get(i);
                    //如果审核单处于"待他人审核",也就是还没审核的时候,删除
                    if(AuditStatusEnum.PENDING.getCode().equals(auditVO.getAuditStatus())){
                        auditMapper.deleteById(auditVO.getId());
                    }
                }
            }
            //更新申请单的状态为驳回,并给申请单设置驳回原因
            application.setStatus(ApplicationStatusEnum.REJECT.getCode());
            application.setRejectReason(auditSaveParam.getRejectReason());
            //更新车辆申请单
            applicationMapper.update(application);
        }
    }

    //查对应审批单的审批人数据
    //auditUserIdList=[3,1] auditUsernameList=lily,tom(String)
    private void assignAuditUserList(AuditVO auditVO) {
        List<String> usernameList = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();
        //需查询当前登录用户审批的当前申请单下的所有审批人
        List<AuditVO> auditVOList =
        auditMapper.selectAuditByApplicationId(auditVO.getApplicationId());
        //依次遍历每个审批人,获取审批人的id与username,分别装入上方的两个list中
        for(int i = 0; i<auditVOList.size() ; i++){
            Long userId = auditVOList.get(i).getAuditUserId();
            userIdList.add(userId);
            UserVO user = userMapper.selectById(userId);
            usernameList.add(user.getUsername());
        }
        //姓名是String类型,需要使用逗号分隔
        StringJoiner stringJoiner = new StringJoiner(",");
        for(String username : usernameList){
            stringJoiner.add(username);
        }
        //把准备好的审核人id与审核人姓名list数据存入auditVO中
        auditVO.setAuditUserIdList(userIdList);
        auditVO.setAuditUsernameList(stringJoiner.toString());
    }
}
