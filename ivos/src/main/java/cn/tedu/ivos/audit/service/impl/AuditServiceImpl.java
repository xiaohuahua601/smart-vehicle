package cn.tedu.ivos.audit.service.impl;

import cn.tedu.ivos.application.pojo.entity.Application;
import cn.tedu.ivos.application.pojo.vo.ApplicationVO;
import cn.tedu.ivos.audit.mapper.AuditMapper;
import cn.tedu.ivos.audit.pojo.dto.AuditQuery;
import cn.tedu.ivos.audit.pojo.entity.Audit;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.enums.AuditStatusEnum;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {
    @Autowired
    AuditMapper auditMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public void insertAudit(Application application) {
        log.debug("新增审批单:application={}",application);
        List<Long> auditUserIdList = application.getAuditUserIdList();
        for (int i = 0;i<auditUserIdList.size();i++){
            Audit audit = new Audit();
            //为当前审批单设置对应的申请单id
            audit.setApplicationId(application.getId());
            //为当前审批单设置审批人用户id
            audit.setAuditUserId(auditUserIdList.get(i));
            // i == 0 表示第一个审批单,这个审批单需要设置为"待我审核"(当前审批人是申请人的上级)
            if (i==0){
                audit.setAuditStatus(AuditStatusEnum.MY_PENDING.getCode());
            }else {
                audit.setAuditStatus(AuditStatusEnum.PENDING.getCode());
            }
            audit.setAuditSort(i);   // 审批排序
            auditMapper.insert(audit);  //新增
        }
    }

    @Override
    public List<AuditVO> selectAudit(AuditQuery auditQuery) {
        List<AuditVO> list = auditMapper.selectAudit(auditQuery);
        for (int i = 0;i<list.size();i++){
            AuditVO auditVO = list.get(i);
            //以下逻辑封装到一个内容方法中 获取审批人姓名集合  和id集合
            assignAuditUserList(auditVO );
        }
        //记得返回数据
        return list;
    }

    private void assignAuditUserList(AuditVO auditVO) {
        //准备空集合,分别用来存当前申请单对应的审批人姓名与审批人id
        List<String> auditUsernameList = new ArrayList<>();
        List<Long> auditUserIdList = new ArrayList<>();
//        修改地方auditVO.getApplicationId()
        List<AuditVO> auditVOS = auditMapper.selectAuditByApplicationId(auditVO.getApplicationId());
        System.out.println(auditVOS);
        for (int i = 0;i<auditVOS.size();i++){
            //修改代码
            Long id = auditVOS.get(i).getAuditUserId();
            System.out.println(id);
            auditUserIdList.add(id);
            UserVO userVO = userMapper.selectById(id);
            auditUsernameList.add(userVO.getUsername());
        }
        //将准备好的审批人id集合数据赋值给applicationVO
        auditVO.setAuditUserIdList(auditUserIdList);
        //需要准备一个工具,帮我们进行值与值的拼接
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String username : auditUsernameList) {
            stringJoiner.add(username);
        }
        //将拼接好的审批人name列表转为一个完整的逗号分隔的字符串赋值给申请单VO的审批人姓名list属性中
        auditVO.setAuditUsernameList(stringJoiner.toString());
    }
}
