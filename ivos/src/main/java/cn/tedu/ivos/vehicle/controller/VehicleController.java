package cn.tedu.ivos.vehicle.controller;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleSaveParam;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import cn.tedu.ivos.vehicle.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicle")
@Slf4j
@Api(tags = "车辆管理")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;
    /**
     * 查询车辆信息
     * GET http://localhost:8080/v1/vehicle/select
     * @param vehicleQuery
     * @return
     */
    @ApiOperation("查询车辆信息")
    @GetMapping("/select")
    public JsonResult selectVehicle(VehicleQuery vehicleQuery){
        log.debug("查询车辆参数vehicleQuery:{}",vehicleQuery);
        List<VehicleVO> vehicleVOS = vehicleService.selectVehicle(vehicleQuery);
        //记得返回数据
        return JsonResult.ok(vehicleVOS);
    }

    @PostMapping("/save")
    @ApiOperation("车辆的新增和修改")
    public JsonResult saveVehicle(VehicleSaveParam vehicleSaveParam){
        log.debug("新增车辆参数vehicleSaveParam:{}",vehicleSaveParam);
        vehicleService.saveVehicle(vehicleSaveParam);
        return JsonResult.ok();
    }
    //post http://localhost:8080/v1/vehicle/delete/1
    @PostMapping("/delete/{id}")
    @ApiOperation("删除车辆信息")
    //@PathVariable 接受请求路径上的参数 映射到方法的参数对应的位置
    public JsonResult deleteVehicle(@PathVariable Long id){
        log.debug("删除车辆参数id:{}",id);
        vehicleService.deleteVehicle(id);
        return JsonResult.ok();
    }

}
