package cn.tedu.ivos.geofence.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GeofenceQuery {
    @ApiModelProperty(value = "围栏编号")
    private Long id;
    @ApiModelProperty(value = "围栏名称")
    private String name;
    @ApiModelProperty(value = "围栏状态")
    private String status;
    @ApiModelProperty(value = "围栏坐标点")
    private String position;
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}