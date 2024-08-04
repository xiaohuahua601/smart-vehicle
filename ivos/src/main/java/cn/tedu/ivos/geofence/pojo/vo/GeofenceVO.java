package cn.tedu.ivos.geofence.pojo.vo;

import cn.tedu.ivos.vehicle.pojo.vo.VehicleVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GeofenceVO {
    @ApiModelProperty(value = "围栏编号")
    private Long id;
    @ApiModelProperty(value = "围栏名称")
    private String name;
    @ApiModelProperty(value = "围栏状态")
    private String status;
    @ApiModelProperty(value = "围栏坐标点")
    private String position;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "围栏绑定车辆列表")
    private List<VehicleVO> vehicleList;
    @ApiModelProperty(value = "围栏绑定车辆总数")
    private Integer totalNum;
    @ApiModelProperty(value = "围栏可用的绑定车辆总数")
    private Integer availableNum;
}