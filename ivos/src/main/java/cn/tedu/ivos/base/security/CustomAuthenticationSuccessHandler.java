package cn.tedu.ivos.base.security;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 自定义的认证成功处理器
 * 当用户成功通过认证后，会调用这个处理器的onAuthenticationSuccess方法
 * */
@Service
@Slf4j
public class CustomAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    //添加响应data  uservo
    @Autowired
    UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        log.debug("认证成功");
        String name = authentication.getName();
        UserVO userVO = userMapper.selectByUsername(name);
        //设置响应的字符编码和内容类型，确保前端能正确解析JSON格式的数据
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        //将查询到的用户信息封装成JSON格式，写入响应中返回给前端
        response.getWriter().write(JSON.toJSONString(JsonResult.ok(userVO)));

    }
}
