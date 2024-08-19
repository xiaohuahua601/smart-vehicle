package cn.tedu.ivos.audit.controller;

import cn.tedu.ivos.audit.pojo.dto.AuditQuery;
import cn.tedu.ivos.audit.pojo.vo.AuditVO;
import cn.tedu.ivos.audit.service.AuditService;
import cn.tedu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/audit")
public class AuditController {
    @Autowired
    AuditService auditService;

    @GetMapping("/select")
    public JsonResult selectAudit(AuditQuery auditQuery){
        log.debug("查询审批表的查询对象:{}",auditQuery);
        List<AuditVO> list = auditService.selectAudit(auditQuery);
        //记得返回数据
        return JsonResult.ok(list);
    }
}
