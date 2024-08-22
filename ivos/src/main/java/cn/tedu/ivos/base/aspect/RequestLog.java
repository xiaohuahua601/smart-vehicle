package cn.tedu.ivos.base.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 切面日志
 * @Aspect 声明当前的类是一个切面类
 * @Component 类初始化--》放到spring容器来面
 * 注释--》jvm-->做成一个注解--》
 */
@Aspect
@Component
public class RequestLog {
    //自定义logger日志对象
    private final static Logger logger = LoggerFactory.getLogger(RequestLog.class);

    //公共方法===》通知方法
     /*
     bean 对象
     within-->指定包
     execution--》按语法匹配
     @annotaion-->通过注解
      */
    //execution==>匹配 多个类 多个方法
    //execution()-->表达式---》修饰符   返回值结构  包路径.类名称.方法名称（入参）
    //execution(pulic JsonRuselt cn.tedu.ivos.user.controller.UserController.login(UserLoginParam))  连接点
    //execution(pulic * cn.tedu.ivos.*.*.UserController.*(..))  连接点
    //execution(pulic * cn.tedu.ivos.*.controller.*.*(..))  连接点
    @Pointcut("execution(public * cn.tedu.ivos.*.controller.*.*(..))")
//    @Pointcut("within(cn.tedu.ivos.user.controller)")
//    @Pointcut("bean(userController)")
//    @Pointcut("@annotation(Log)")
    public void anyController(){}

    //通知方法 执行的类型 前置通知
    //JoinPoint连接点--》存放 前端来的请求相关的信息
    @Before("anyController()")
    public void before(JoinPoint joinPoint){
        requestUrl();//输出我们请求路径
        //输出其他的参数
        Object[] args = joinPoint.getArgs();
        //过滤数据 args 空数据
       Stream<?> stream = ArrayUtils.isEmpty(args)? Stream.empty() : Arrays.stream(args);
        //过滤数据 args 获取请求信息
        // 判断当前的对象 属于什么类型 instanceof
        List<Object> collect = stream.filter(arg -> (
                !(arg instanceof HttpServletRequest)
                        && !(arg instanceof HttpServletResponse)
                        && !(arg instanceof MultipartFile))).collect(Collectors.toList());
        //请求方法
        //getSignature() 方法签名-
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        // 类路径 cn.tedu.ivos.user.controller
        String  methodPackage = methodSignature.getDeclaringTypeName();
        //方法名称 /login
        String methodName = methodSignature.getMethod().getName();
        String builder = methodPackage + "."+methodName +",请求参数："+ JSON.toJSONString(collect);
        logger.info("请求日志详情{}",builder);

    }

    private void requestUrl() {
        ServletRequestAttributes requestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes!=null){
            HttpServletRequest request = requestAttributes.getRequest();
            String requestURI = request.getRequestURI();
            logger.info("请求URL是什么:{}",requestURI);
        }
    }


}
