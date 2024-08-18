package cn.tedu.ivos.audit.service;

import cn.tedu.ivos.application.pojo.entity.Application;

public interface AuditService {
    void insertAudit(Application application);
}
