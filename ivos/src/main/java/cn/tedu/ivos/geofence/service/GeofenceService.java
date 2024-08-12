package cn.tedu.ivos.geofence.service;

import cn.tedu.ivos.geofence.pojo.dto.GeofenceQuery;
import cn.tedu.ivos.geofence.pojo.vo.GeofenceVO;

import java.util.List;

public interface GeofenceService {
    //查询围栏列表数据
    List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery);
}
