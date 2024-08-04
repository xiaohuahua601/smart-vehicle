package cn.tedu.ivos.audit.controller;

import cn.tedu.ivos.audit.pojo.dto.AuditQuery;
import cn.tedu.ivos.audit.pojo.dto.AuditSaveParam;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/audit")
public class AuditController {
    @Autowired
    AuditService auditService;

    @GetMapping("select")
    public JsonResult selectAudit(AuditQuery auditQuery){
        log.debug("查询审批单:auditQuery={}",auditQuery);
        List<AuditVO> list = auditService.selectAudit(auditQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("update")
    public JsonResult updateAudit(AuditSaveParam auditSaveParam){
        log.debug("更新审批单:auditSaveParam={}",auditSaveParam);
        auditService.updateAudit(auditSaveParam);
        return JsonResult.ok();
    }
}




