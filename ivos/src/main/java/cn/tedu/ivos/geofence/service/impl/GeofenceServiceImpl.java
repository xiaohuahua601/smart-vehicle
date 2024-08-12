package cn.tedu.ivos.geofence.service.impl;

import cn.tedu.ivos.geofence.mapper.GeofenceMapper;
import cn.tedu.ivos.geofence.pojo.dto.GeofenceQuery;
import cn.tedu.ivos.geofence.pojo.vo.GeofenceVO;
import cn.tedu.ivos.geofence.service.GeofenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GeofenceServiceImpl implements GeofenceService {

    @Autowired
    GeofenceMapper geofenceMapper;

    @Override
    public List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery) {
        //查询围栏列表
        List<GeofenceVO> geofenceVOS = geofenceMapper.selectGeofence(geofenceQuery);
        //记得返回数据
        return geofenceVOS;
    }
}
