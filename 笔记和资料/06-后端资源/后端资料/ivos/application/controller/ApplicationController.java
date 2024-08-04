package cn.tedu.ivos.application.controller;

import cn.tedu.ivos.application.pojo.dto.ApplicationQuery;
import cn.tedu.ivos.application.pojo.dto.ApplicationSaveParam;
import cn.tedu.ivos.application.pojo.vo.ApplicationVO;
import cn.tedu.ivos.application.service.ApplicationService;
import cn.tedu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;

    @PostMapping("save")
    public JsonResult saveApplication(ApplicationSaveParam applicationSaveParam){
        log.debug("新增用车申请:applicationSaveParam={}",applicationSaveParam);
        applicationService.save(applicationSaveParam);
        return JsonResult.ok();
    }

    @GetMapping("select")
    public JsonResult select(ApplicationQuery applicationQuery){
        log.debug("查询车辆申请单:applicationQuery={}",applicationQuery);
        List<ApplicationVO> list = applicationService.selectApplication(applicationQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("cancel/{id}")
    public JsonResult cancel(@PathVariable Long id){
        log.debug("撤销申请:id={}",id);
        applicationService.cancel(id);
        return JsonResult.ok();
    }

    @PostMapping("distribute/{applicationId}/{vehicleId}")
    public JsonResult distribute(@PathVariable Long applicationId,
                                 @PathVariable Long vehicleId){
        log.debug("分配车辆,申请单编号="+applicationId+"车辆编号="+vehicleId);
        applicationService.distribute(applicationId,vehicleId);
        return JsonResult.ok();
    }

    @PostMapping("back/{applicationId}/{vehicleId}")
    public JsonResult back(@PathVariable Long applicationId,
                                 @PathVariable Long vehicleId){
        log.debug("归还车辆,申请单编号="+applicationId+"车辆编号="+vehicleId);
        applicationService.back(applicationId,vehicleId);
        return JsonResult.ok();
    }

}
