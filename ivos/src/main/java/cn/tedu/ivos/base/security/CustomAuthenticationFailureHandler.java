package cn.tedu.ivos.base.security;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.base.response.StatusCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 自定义的认证失败处理器
 * 当用户认证失败后，会调用这个处理器的onAuthenticationFailure方法
 * */
@Service
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        log.debug("认证失败");
        //设置响应的字符编码和内容类型，确保前端能正确解析JSON格式的数据
        response.setCharacterEncoding("UTF-8");
        //设置响应的内容类型为application/json，指定客户端应以JSON格式解析响应数据
        //同时指定字符集为UTF-8，与前面的设置保持一致
        response.setContentType("application/json;charset=UTF-8");
        //将查询到的用户信息封装成JSON格式，写入响应中返回给前端
        //JsonResult封装了状态码和可能的错误信息，这里使用StatusCode.USERNAME_ERROR
        // 表示用户名错误的特定状态码
        response.getWriter()
                .write(JSON.toJSONString(new JsonResult(StatusCode.USERNAME_ERROR)));
    }
}
