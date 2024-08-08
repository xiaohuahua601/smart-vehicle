package cn.tedu.ivos.vehicle.service;

import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;

import java.util.List;

public interface VehicleService {
   List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery);
}
