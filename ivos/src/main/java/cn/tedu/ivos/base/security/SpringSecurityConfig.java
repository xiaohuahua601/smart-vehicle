package cn.tedu.ivos.base.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全框架的配置类
 * WebSecurityConfigurerAdapter安全框架自带的配置类 重写里面的代码
 */
@Configuration
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //这个就是配置类 HttpSecurity
    @Autowired
    CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //针对于认证的情况进行配置 重写代码逻辑
        http.csrf().disable() // 禁用CSRF（跨站请求伪造）保护
                .httpBasic() // 配置HTTP基本认证
                .and().authorizeRequests() // 配置请求授权，默认禁用所有请求
                .antMatchers().permitAll() // 对指定的资源不进行权限检查，允许所有用户访问
                .anyRequest().authenticated() //对其他所有请求要求用户必须认证通过
                .and().formLogin()  // 配置表单登录，默认拦截的登录请求地址为/login
                .successHandler(authenticationSuccessHandler) //设置认证成功处理器---封装到类---注入对象
                .failureHandler(authenticationFailureHandler) //设置认证失败处理器---封装到类---注入对象
                .permitAll() //允许所有用户进行登录尝试
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
