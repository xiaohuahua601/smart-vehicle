package cn.tedu.ivos.application.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ApplicationVO {
    @ApiModelProperty(value = "申请单id")
    private Long id;
    @ApiModelProperty(value = "申请人id")
    private Long userId;
    @ApiModelProperty(value = "申请人名称")
    private String username;
    @ApiModelProperty(value = "用车开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @ApiModelProperty(value = "用车结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    @ApiModelProperty(value = "驾照图片")
    private String imgUrl;
    @ApiModelProperty(value = "出发地")
    private String departureAddr;
    @ApiModelProperty(value = "目的地")
    private String destinationAddr;
    @ApiModelProperty(value = "申请原因")
    private String reason;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "申请单状态")
    private Integer status;
    @ApiModelProperty(value = "车辆id")
    private Long vehicleId;
    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    @ApiModelProperty(value = "审批人id集合")
    private List<Long> auditUserIdList;
    @ApiModelProperty(value = "审批人昵称集合")
    private String auditUsernameList;
}