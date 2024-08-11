package cn.tedu.ivos.vehicle.mapper;

import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.entity.Vehicle;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleMapper {
    //根据条件查询车辆的基本信息
    List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery);

    List<VehicleVO> selectVehicle(String license,String code);
    //新增车辆信息
    void insert(Vehicle vehicle);
    //修改车辆信息
    void update(Vehicle vehicle);
    //删除车辆信息
    void deleteVehicle(Long id);

    void updateNullValue(Vehicle vehicle);
}
