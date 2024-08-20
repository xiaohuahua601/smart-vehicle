package cn.tedu.ivos.application.controller;

import cn.tedu.ivos.application.pojo.dto.ApplicationQuery;
import cn.tedu.ivos.application.pojo.dto.ApplicationSaveParam;
import cn.tedu.ivos.application.pojo.vo.ApplicationVO;
import cn.tedu.ivos.application.service.ApplicationService;
import cn.tedu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    //新增的业务逻辑
    @PostMapping("/save")
    public JsonResult saveApplication(ApplicationSaveParam applicationSaveParam){
        log.debug("{}",applicationSaveParam);
        applicationService.save(applicationSaveParam);
        return JsonResult.ok();
    }
    //查询的业务逻辑
    @GetMapping("/select")
    public JsonResult selectApplication(ApplicationQuery applicationQuery){
        log.debug("查询车辆申请单:applicationQuery={}",applicationQuery);
        List<ApplicationVO> list = applicationService.selectApplication(applicationQuery);
        //返回数据
        return JsonResult.ok(list);
    }
    // axios.post(BASE_URL+'/v1/application/cancel/'+id)
    @PostMapping("/cancel/{id}")
    public JsonResult cancel(@PathVariable Long id){
        log.debug("撤销申请:id={}",id);
        applicationService.cancel(id);
        return JsonResult.ok();
    }
    //分配用车   根据我们申请单id  修改当前的车辆id字段
    @PostMapping("/distribute/{applicationId}/{vehicleId}")
    public JsonResult distribute(@PathVariable Long applicationId
            ,@PathVariable Long vehicleId){
        log.debug("分配车辆：申请单编号="+applicationId+"车辆编号="+vehicleId);
        applicationService.distribute(applicationId,vehicleId);
        return JsonResult.ok();
    }
    //还车   根据我们申请单id  修改工单状态  已结束  修改车辆的状态---》占用==》空闲
    @PostMapping("/back/{applicationId}/{vehicleId}")
    public JsonResult back(@PathVariable Long applicationId
            ,@PathVariable Long vehicleId){
        log.debug("还车车辆：申请单编号="+applicationId+"车辆编号="+vehicleId);
        applicationService.back(applicationId,vehicleId);
        return JsonResult.ok();
    }
}
