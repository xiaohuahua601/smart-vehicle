package cn.tedu.ivos.user.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQuery {
    @ApiModelProperty(value="id")
    private Long id;
    @ApiModelProperty(value="用户名")
    private String username;
    @ApiModelProperty(value="用户状态")
    private String status;
    @ApiModelProperty(value="用户职级")
    private String level;
    @ApiModelProperty(value = "上级id")
    private Long parentId;
}