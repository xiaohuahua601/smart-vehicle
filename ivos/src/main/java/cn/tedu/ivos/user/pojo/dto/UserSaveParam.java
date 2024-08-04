package cn.tedu.ivos.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "UserSaveParam", description = "新增/更新用户信息请求参数")
public class UserSaveParam {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "年龄")
    @NotBlank(message = "年龄不能为空")
    private Integer age;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空")
    private String gender;

    @ApiModelProperty(value = "用户职级")
    @NotBlank(message = "用户职级不能为空")
    private String level;

    @ApiModelProperty(value = "用户状态")
    @NotBlank(message = "用户状态不能为空")
    private String status;

    @ApiModelProperty(value = "上级id")
    @NotBlank(message = "上级id")
    private Long parentId;
}