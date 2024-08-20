package cn.tedu.ivos.geofence.service.impl;

import cn.tedu.ivos.base.exception.ServiceException;
import cn.tedu.ivos.base.response.StatusCode;
import cn.tedu.ivos.geofence.mapper.GeofenceMapper;
import cn.tedu.ivos.geofence.pojo.dto.GeofenceParam;
import cn.tedu.ivos.geofence.pojo.dto.GeofenceQuery;
import cn.tedu.ivos.geofence.pojo.entity.Geofence;
import cn.tedu.ivos.geofence.pojo.vo.GeofenceVO;
import cn.tedu.ivos.geofence.service.GeofenceService;
import cn.tedu.ivos.vehicle.mapper.VehicleMapper;
import cn.tedu.ivos.vehicle.pojo.dto.VehicleQuery;
import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class GeofenceServiceImpl implements GeofenceService {

    @Autowired
    GeofenceMapper geofenceMapper;
    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery) {
        //查询围栏列表
        List<GeofenceVO> geofenceVOS = geofenceMapper.selectGeofence(geofenceQuery);
        //查询对应每一个电子围栏中的 车辆总数 以及可用车辆
        for (int i = 0; i < geofenceVOS.size(); i++) {
            //依次取出每个遍历到的围栏对象
            GeofenceVO geofenceVO = geofenceVOS.get(i);
            //根据围栏的id  查询电子围栏中车辆的数据
            VehicleQuery vehicleQuery = new VehicleQuery();
            //条件为 电子围栏id
            vehicleQuery.setGeofenceId(geofenceVO.getId());
            List<VehicleVO> vehicleVOS = vehicleMapper.selectVehicle(vehicleQuery);
            //获取集合的总数据  总共的车辆数据
            int totalNum = vehicleVOS.size();
            //获取可以车辆数据  车辆状态  0占用 1 空闲
            int availableNum = 0;
            for (VehicleVO vehicleVO:vehicleVOS) {
                if (vehicleVO.getStatus().equals("1")){
                    ++availableNum;
                }
            }
            if (availableNum>0){
                //有空闲的车
                geofenceVO.setAvailableNum(availableNum);
            }else {
                //没有空闲的车
                geofenceVO.setAvailableNum(0);
            }
            geofenceVO.setTotalNum(totalNum);
            geofenceVO.setVehicleList(vehicleVOS);

        }
        //记得返回数据
        return geofenceVOS;
    }

    @Override
    public void saveGeofence(GeofenceParam geofenceParam) {
        //初始化围栏对象
        Geofence geofence = new Geofence();
        //两个对象之前 属性定义 名称相同 可以实现一个值复制
        BeanUtils.copyProperties(geofenceParam,geofence);
        //针对位置 前端k-v  但是数据库 String 进行一个对象转换
//        {k:v}--->字符串 ---ObjectMapper
        HashMap<String, String> position = geofenceParam.getPosition();
        ObjectMapper objectMapper = new ObjectMapper();
        try { //需要try  catch
            String positionStr = objectMapper.writeValueAsString(position);
            //做业务
            if (geofenceParam.getId()!=null){//修改  状态
                geofenceMapper.update(geofence);
            }else {// 1启用  0 禁用
                geofence.setStatus("1");
                geofence.setPosition(positionStr);
                geofence.setCreateTime(new Date());
                geofenceMapper.insert(geofence);                //新增
            }
        } catch (JsonProcessingException e) {
           //JSON转换失败抛出业务层异常
            throw new ServiceException(StatusCode.OPERATION_FAILED);
        }
    }

    @Override
    public void deleteGeofence(Long id) {
        VehicleQuery vehicleQuery = new VehicleQuery();
        vehicleQuery.setGeofenceId(id);
        //查询一下 围栏中的车辆信息---》车辆表 查询围栏geofence_id
        List<VehicleVO> vehicleVOS = vehicleMapper.selectVehicle(vehicleQuery);
        if (vehicleVOS!=null&&vehicleVOS.size()>0){
            //如果有车抛出业务层异常
            throw new ServiceException(StatusCode.OPERATION_FAILED);
        }else {
            //没有车才可以删除
            geofenceMapper.deleteById(id);
        }

    }
}
