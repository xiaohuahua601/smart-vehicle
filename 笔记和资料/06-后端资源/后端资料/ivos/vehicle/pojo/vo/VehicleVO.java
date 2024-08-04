package cn.tedu.ivos.vehicle.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VehicleVO {
    @ApiModelProperty(value = "车辆id")
    private Long id;
    @ApiModelProperty(value = "车辆品牌")
    private String brand;
    @ApiModelProperty(value = "车牌号")
    private String license;
    @ApiModelProperty(value = "车辆型号")
    private String model;
    @ApiModelProperty(value = "车辆识别码")
    private String code;
    @ApiModelProperty(value = "车辆排量")
    private String displacement;
    @ApiModelProperty(value = "车辆状态")
    private String status;
    @ApiModelProperty(value = "车辆类型")
    private String type;
    @ApiModelProperty(value = "车辆颜色")
    private String color;
    @ApiModelProperty(value = "车辆里程数")
    private String kilometers;
    @ApiModelProperty(value = "车辆上牌时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date regTime;
    @ApiModelProperty(value = "车辆购买时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date buyTime;
    @ApiModelProperty(value = "车辆购买价格")
    private String buyMoney;
    @ApiModelProperty(value = "车辆电池类型")
    private String batteryType;
    @ApiModelProperty(value = "车辆创建时间")
    private Date createTime;
    @ApiModelProperty(value = "车辆更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "电子围栏绑定状态")
    private String geofenceBindStatus;
    @ApiModelProperty(value = "电子围栏id")
    private Long geofenceId;
}