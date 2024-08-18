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
}
