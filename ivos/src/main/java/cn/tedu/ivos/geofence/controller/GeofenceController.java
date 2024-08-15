package cn.tedu.ivos.geofence.controller;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.geofence.pojo.dto.GeofenceParam;
import cn.tedu.ivos.geofence.pojo.dto.GeofenceQuery;
import cn.tedu.ivos.geofence.pojo.vo.GeofenceVO;
import cn.tedu.ivos.geofence.service.GeofenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/geofence")
public class GeofenceController {
    @Autowired
    GeofenceService geofenceService;

    /**
     * 查询围栏列表
     */
    @GetMapping("/select")
    public JsonResult selectGeofence(GeofenceQuery geofenceQuery){
        log.debug("根据条件查询围栏列表数据geofenceQuery:{}",geofenceQuery);
        List<GeofenceVO> geofenceVOS = geofenceService.selectGeofence(geofenceQuery);
        //记得返回数据
        return JsonResult.ok(geofenceVOS);
    }

    @PostMapping("/save")
    public JsonResult saveGeofence(GeofenceParam geofenceParam){
        log.debug("收到前端传过来的围栏信息:{}",geofenceParam);
        geofenceService.saveGeofence(geofenceParam);
        return JsonResult.ok();
    }

    @PostMapping("/delete/{id}")
    public JsonResult deleteGeofence(@PathVariable Long id){
        log.debug("删除的围栏id:{}",id);
        geofenceService.deleteGeofence(id);
        return JsonResult.ok();
    }

}
