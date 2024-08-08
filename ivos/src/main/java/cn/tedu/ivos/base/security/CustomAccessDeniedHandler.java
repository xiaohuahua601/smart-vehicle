package cn.tedu.ivos.base.security;

import cn.tedu.ivos.base.response.JsonResult;
import cn.tedu.ivos.base.response.StatusCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义权限不足处理类，实现了AccessDeniedHandler接口
 * 当用户没有足够的权限访问资源时，该类将被调用以处理权限不足的异常
 */
@Service
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 处理权限不足的异常
     * @param request  当前的HTTP请求对象
     * @param response 当前的HTTP响应对象
     * @param accessDeniedException 权限不足异常对象
     * @throws IOException 如果在处理过程中发生I/O错误
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.debug("权限不足");
//        403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
