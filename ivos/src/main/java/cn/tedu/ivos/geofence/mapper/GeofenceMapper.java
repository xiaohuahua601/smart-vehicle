package cn.tedu.ivos.geofence.mapper;

import cn.tedu.ivos.geofence.pojo.dto.GeofenceQuery;
import cn.tedu.ivos.geofence.pojo.entity.Geofence;
import cn.tedu.ivos.geofence.pojo.vo.GeofenceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeofenceMapper {
   //查询围栏列表
    List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery);
    //修改围栏
    void update(Geofence geofence);
    //新增围栏
    void insert(Geofence geofence);
    //根据id删除围栏信息
    void deleteById(Long id);
}
