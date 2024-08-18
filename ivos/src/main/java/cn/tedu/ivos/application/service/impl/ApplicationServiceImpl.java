package cn.tedu.ivos.application.service.impl;

import cn.tedu.ivos.application.mapper.ApplicationMapper;
import cn.tedu.ivos.application.pojo.dto.ApplicationQuery;
import cn.tedu.ivos.application.pojo.dto.ApplicationSaveParam;
import cn.tedu.ivos.application.pojo.entity.Application;
import cn.tedu.ivos.application.pojo.vo.ApplicationVO;
import cn.tedu.ivos.application.service.ApplicationService;
import cn.tedu.ivos.audit.mapper.AuditMapper;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.enums.ApplicationStatusEnum;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;


@Service
@Slf4j
@Transactional
//事务注解 一次请求 要不都成功 要不都失败
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    AuditService auditService;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public void save(ApplicationSaveParam applicationSaveParam) {
        Application application = new Application();
        //数据数据初始化
        BeanUtils.copyProperties(applicationSaveParam,application);
        //枚举状态  新增的状态一定是 已发起
        application.setStatus(ApplicationStatusEnum.PENDING.getCode());
        application.setCreateTime(new Date());
        //插入我们的审核表  申请 id===审批人的id  ===>入库 审核表
        applicationMapper.insert(application);
        //调用审核模块的业务层方法,新增当前申请单对应的审批单
        auditService.insertAudit(application);
    }

    @Override
    public List<ApplicationVO> selectApplication(ApplicationQuery applicationQuery) {
        List<ApplicationVO> list = applicationMapper.selectApplication(applicationQuery);
        for (int i = 0;i<list.size();i++){
            ApplicationVO applicationVO = list.get(i);
            //以下逻辑封装到一个内容方法中
            assignAuditUserList(applicationVO );
        }
        //记得返回数据
        return list;
    }
    /**
     * 给applicationVO的auditUserIdList与auditUsernameList字段赋值
     * 也就是处理当前申请单的审批人相关信息
     * @param applicationVO
     */
    private void assignAuditUserList(ApplicationVO applicationVO) {
        //准备空集合,分别用来存当前申请单对应的审批人姓名与审批人id
        List<String> auditUsernameList = new ArrayList<>();
        List<Long> auditUserIdList = new ArrayList<>();
        List<AuditVO> auditVOS = auditMapper.selectAuditByApplicationId(applicationVO.getId());
        for (int i = 0;i<auditVOS.size();i++){
            AuditVO auditVO = auditVOS.get(i);
             Long id = auditVO.getUserId();
            auditUserIdList.add(id);
            UserVO userVO = userMapper.selectById(id);
            auditUsernameList.add(userVO.getUsername());
        }
        //将准备好的审批人id集合数据赋值给applicationVO
        applicationVO.setAuditUserIdList(auditUserIdList);
        //需要准备一个工具,帮我们进行值与值的拼接
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String username : auditUsernameList) {
            stringJoiner.add(username);
        }
        //将拼接好的审批人name列表转为一个完整的逗号分隔的字符串赋值给申请单VO的审批人姓名list属性中
        applicationVO.setAuditUsernameList(stringJoiner.toString());
    }
}
