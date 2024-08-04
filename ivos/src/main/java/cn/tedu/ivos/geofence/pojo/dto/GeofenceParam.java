package cn.tedu.ivos.geofence.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;

@Data
public class GeofenceParam {
    @ApiModelProperty(value = "围栏编号")
    private Long id;
    @ApiModelProperty(value = "围栏名称")
    private String name;
    @ApiModelProperty(value = "围栏状态")
    private String status;
    //前端传过来的数据是K-V结构
    //{
    // name:'运输围栏',
    // position:'{type:'circle', longitude: 116, latitude: 39, radius: 661}'
    // }
    //{type:'rectangle', recPoints: '116-39,116-39,116-39,116-39'}
    @ApiModelProperty(value = "围栏坐标点")
    private HashMap<String,String> position;
}