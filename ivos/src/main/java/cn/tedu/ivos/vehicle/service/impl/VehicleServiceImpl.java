package cn.tedu.ivos.vehicle.service.impl;

import cn.tedu.ivos.vehicle.mapper.VehicleMapper;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleSaveParam;
import cn.tedu.ivos.vehicle.pojo.entity.Vehicle;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import cn.tedu.ivos.vehicle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public void saveVehicle(VehicleSaveParam vehicleSaveParam) {
        Vehicle vehicle = new Vehicle();
        // 直接把一个对象中的属性值 复制到另一个对象中
        BeanUtils.copyProperties(vehicleSaveParam,vehicle);
        //判断业务逻辑 是新增 还是修改
        if (vehicleSaveParam.getId() == null) {
            //新增---状态 空闲 （1）
            vehicle.setStatus("1");
            //new Date()获取当前的系统时间
            vehicle.setCreateTime(new Date());
            vehicleMapper.insert(vehicle);
        }else {
            //做修改
            vehicle.setUpdateTime(new Date());
            vehicleMapper.update(vehicle);
        }
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleMapper.deleteVehicle(id);
    }
}
