package cn.tedu.ivos.geofence.service;

import cn.tedu.ivos.geofence.pojo.dto.GeofenceParam;
import cn.tedu.ivos.geofence.pojo.dto.GeofenceQuery;
import cn.tedu.ivos.geofence.pojo.vo.GeofenceVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GeofenceService {
    //查询围栏列表数据
    List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery);
    //新增和修改围栏的接口方法
    void saveGeofence(GeofenceParam geofenceParam);
    //删围栏
    void deleteGeofence(Long id);
}
