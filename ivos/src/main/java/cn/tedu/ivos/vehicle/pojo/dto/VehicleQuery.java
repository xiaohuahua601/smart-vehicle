package cn.tedu.ivos.vehicle.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleQuery {
    @ApiModelProperty(value = "车辆id")
    private Long id;
    @ApiModelProperty(value = "车辆品牌")
    private String brand;
    @ApiModelProperty(value = "车牌号")
    private String license;
    @ApiModelProperty(value = "电子围栏绑定状态")
    private String geofenceBindStatus;
    @ApiModelProperty(value = "电子围栏id")
    private Long geofenceId;
}