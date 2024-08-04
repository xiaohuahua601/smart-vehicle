package cn.tedu.ivos.audit.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditSaveParam {
    @ApiModelProperty(value = "审批单id")
    private Long id;
    @ApiModelProperty(value = "申请单id")
    private Long applicationId;
    @ApiModelProperty(value = "审批人id")
    private Long auditUserId;
    @ApiModelProperty(value = "审批状态")
    private String auditStatus;
    @ApiModelProperty(value = "审批排序")
    private Integer auditSort;
    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;
}