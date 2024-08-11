package cn.tedu.ivos.base.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    @Autowired
    CustomAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //针对于认证的情况进行配置 重写代码逻辑
        http.csrf().disable() // 禁用CSRF（跨站请求伪造）保护
                .httpBasic() // 配置HTTP基本认证
                .and().authorizeRequests() // 配置请求授权，默认禁用所有请求
                .antMatchers(
                        "/doc.html",
                        "/**/*.js",
                        "/**/*.css",
                        "/swagger-resources",
                        "/v2/api-docs",
//                        方便我们测试接口
                        "/**"
                ).permitAll() // 对指定的资源不进行权限检查，允许所有用户访问
                .anyRequest().authenticated() //对其他所有请求要求用户必须认证通过
                .and().formLogin()  // 配置表单登录，默认拦截的登录请求地址为/login
                .successHandler(authenticationSuccessHandler) //设置认证成功处理器---封装到类---注入对象
                .failureHandler(authenticationFailureHandler) //设置认证失败处理器---封装到类---注入对象
                .permitAll() //允许所有用户进行登录尝试
                .and().exceptionHandling() //当用户尝试访问没有权限的资源时，调用自定义的权限拒绝处理器
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().cors() // 配置跨域请求
                .configurationSource(corsConfigurationSource()) // 设置跨域配置源
                ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
    /**
     * 配置CORS（跨源资源共享）策略。
     * 它允许所有的请求源（Origin）、请求方法（Method）、请求头（Header）和响应头（Exposed Header）且支持请求凭证（Credentials）
     * @return UrlBasedCorsConfigurationSource 一个配置了CORS策略的实例。
     */
    private CorsConfigurationSource corsConfigurationSource(){
        //创建一个新的CORS配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许请求携带凭证，如cookies
        corsConfiguration.setAllowCredentials(true);
        //允许所有的请求头
        corsConfiguration.addAllowedHeader("*");
        //允许所有的请求方法，如GET、POST、PUT、DELETE等
        corsConfiguration.addAllowedMethod("*");
        //允许响应头被客户端访问
        corsConfiguration.addExposedHeader("*");
        //允许来自任何请求源的请求
        corsConfiguration.addAllowedOriginPattern("*");
        //创建一个基于URL的CORS配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //将之前配置的CORS应用于所有URL
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
