package cn.tedu.ivos.user.controller;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Slf4j
@Api(tags = "用户管理")
//@Controller+@ResponseBody
public class UserController {
    // post+对象---》@RequestBody 从请求体里面来接受数据
    @PostMapping("/login")
    @ApiOperation("登录功能")
    public JsonResult login(@RequestBody UserLoginParam userLoginParam){
        log.info("登录的用户对象是什么:{}",userLoginParam);
        //暂不做处理直接返回结果
        return JsonResult.ok();
    }
}
