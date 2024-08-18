package cn.tedu.ivos.audit.service.impl;

import cn.tedu.ivos.application.pojo.entity.Application;
import cn.tedu.ivos.audit.mapper.AuditMapper;
import cn.tedu.ivos.audit.pojo.entity.Audit;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.enums.AuditStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {
    @Autowired
    AuditMapper auditMapper;
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
}
