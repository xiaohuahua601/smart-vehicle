package cn.tedu.ivos.vehicle.mapper;

import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.entity.Vehicle;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleMapper {
    List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery);
    List<VehicleVO> selectVehicle(String license,String code);

    void insert(Vehicle vehicle);

    void update(Vehicle vehicle);

    void deleteVehicle(Long id);

    void updateNullValue(Vehicle vehicle);
}
