package cn.tedu.ivos.base.security;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.base.response.StatusCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义未授权访问处理类
 * 实现AuthenticationEntryPoint接口，用于在未授权访问时进行处理
 */
@Service
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当访问受保护的资源时，如果用户未被授权，此方法将被调用
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param authException 引起未授权访问的AuthenticationException异常
     * @throws IOException 如果发生I/O错误
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.debug("未授权访问");
        //设置响应状态码为  401 未授权访问
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //设置响应类型 application/json;charset=UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置响应内容
        JsonResult jsonResult = new JsonResult(StatusCode.NOT_LOGIN);
        String jsonString = JSON.toJSONString(jsonResult);
        //获取输出流
        PrintWriter writer = response.getWriter();
        //通过输出流 写响应
        writer.write(jsonString);
        //刷新输出流
        writer.flush();
        //关闭输出流
        writer.close();
    }
}
