package cn.tedu.ivos.application.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApplicationQuery {
    @ApiModelProperty(value = "申请人名称")
    private String username;
    @ApiModelProperty(value = "申请单状态")
    private Integer status;
    @ApiModelProperty(value = "出发地")
    private String departureAddr;
    @ApiModelProperty(value = "目的地")
    private String destinationAddr;
}
