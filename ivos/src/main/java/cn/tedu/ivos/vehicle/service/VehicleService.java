package cn.tedu.ivos.vehicle.service;

import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleSaveParam;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;

import java.util.List;

public interface VehicleService {
   //查询业务
   List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery);
   //新增的业务
   void saveVehicle(VehicleSaveParam vehicleSaveParam);
   //删除方法
   void deleteVehicle(Long id);
   //车辆解绑围栏业务 修改
   void unbindVehicle(Long vehicleId);
   //车辆绑定围栏
   void bindVehicle(Long geofenceId,Long vehicleId);
}
