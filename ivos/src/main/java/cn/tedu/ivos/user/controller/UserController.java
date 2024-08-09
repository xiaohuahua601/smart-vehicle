package cn.tedu.ivos.user.controller;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import cn.tedu.ivos.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Slf4j
@Api(tags = "用户管理")
//@Controller+@ResponseBody
public class UserController {
    //注入类型的是接口   实现接口的对象
    @Autowired
    UserService userService;
    // post+对象---》@RequestBody 从请求体里面来接受数据
    @PostMapping("/login")
    @ApiOperation("登录功能")
//   去掉  @RequestBody
    public JsonResult login( UserLoginParam userLoginParam){
        log.info("登录的用户对象是什么:{}",userLoginParam);
        UserVO userVO = userService.login(userLoginParam);
        return JsonResult.ok(userVO);
    }
    //todo 增删改查
}
