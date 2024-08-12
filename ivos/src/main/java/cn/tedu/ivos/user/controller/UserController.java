package cn.tedu.ivos.user.controller;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import cn.tedu.ivos.user.pojo.dto.UserQuery;
import cn.tedu.ivos.user.pojo.dto.UserSaveParam;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import cn.tedu.ivos.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    /**
     * 新增用户
     * POST http://localhost:8080/v1/user/save?username=kk&password=kk&email=kk&phone=kk&age=18&gender=1&status=1&level=10&parentId=6
     * 修改用户
     * POST http://localhost:8080/v1/user/save?id=20&email=kk2&phone=kk2&age=20&gender=0&status=0&level=20&parentId=3
     * @param userSaveParam
     * @return
     */
    @PostMapping("save")
    public JsonResult saveUser(UserSaveParam userSaveParam){
        log.debug("保存用户,userSaveParam={}",userSaveParam);
        userService.saveUser(userSaveParam);
        return JsonResult.ok();
    }

    /**
     * 查询用户列表
     * GET http://localhost:8080/v1/user/select
     * 根据条件查询用户列表
     * GET http://localhost:8080/v1/user/select?username=mike
     * GET http://localhost:8080/v1/user/select?status=1
     * GET http://localhost:8080/v1/user/select?level=40
     * @param userQuery
     * @return
     */
    @GetMapping("select")
    public JsonResult selectUser(UserQuery userQuery){
        List<UserVO> list = userService.selectUser(userQuery);
        return JsonResult.ok(list);
    }

    /**
     * 修改指定用户的密码
     * POST http://localhost:8080/v1/user/update/password/20
     * @param userId
     * @return
     */
    @PostMapping("/update/password/{userId}")
    public JsonResult resetPassword(@PathVariable Long userId){
        log.debug("重置密码，userId={}",userId);
        userService.resetPassword(userId);
        return JsonResult.ok();
    }

    /**
     * 修改指定用户状态
     * POST http://localhost:8080/v1/user/save?id=20&status=1
     * @param userId
     * @param status
     * @return
     */
    @PostMapping("update/status/{userId}/{status}")
    public JsonResult updateStatus(@PathVariable Long userId,@PathVariable String status){
        log.debug("修改用户状态，userId={},status={}",userId,status);
        userService.updateStatus(userId,status);
        return JsonResult.ok();
    }

    /**
     * 删除指定用户
     * POST http://localhost:8080/v1/user/delete/20
     * @param userId
     * @return
     */
    @PostMapping("/delete/{userId}")
    public JsonResult deleteUser(@PathVariable Long userId){
        log.debug("删除用户，userId={}",userId);
        userService.deleteUser(userId);
        return JsonResult.ok();
    }

    /**
     * 查询上级审批人列表
     * @param parentId
     * @return
     */
    @GetMapping("select/audit/{parentId}")
    public JsonResult selectAuditList(@PathVariable Long parentId){
        List<UserVO> list = userService.selectAuditList(parentId);
        return JsonResult.ok(list);
    }
}
