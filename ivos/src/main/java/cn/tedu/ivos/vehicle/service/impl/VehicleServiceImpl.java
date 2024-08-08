package cn.tedu.ivos.vehicle.service.impl;

import cn.tedu.ivos.vehicle.mapper.VehicleMapper;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import cn.tedu.ivos.vehicle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery) {
        List<VehicleVO> vehicleVOS = vehicleMapper.selectVehicle(vehicleQuery);
        //记得返回数据
        return vehicleVOS;
    }
}
