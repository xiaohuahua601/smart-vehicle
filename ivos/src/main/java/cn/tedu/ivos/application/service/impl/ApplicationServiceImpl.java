package cn.tedu.ivos.application.service.impl;

import cn.tedu.ivos.application.mapper.ApplicationMapper;
import cn.tedu.ivos.application.pojo.dto.ApplicationSaveParam;
import cn.tedu.ivos.application.pojo.entity.Application;
import cn.tedu.ivos.application.service.ApplicationService;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.enums.ApplicationStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    AuditService auditService;
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
}
