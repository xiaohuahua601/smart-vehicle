package cn.tedu.ivos.base.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 自定义认证类，用于处理特定的认证逻辑。
 */
@Service
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailsService userDetailsService;
    /**
     * 实现AuthenticationProvider接口的authenticate方法，用于认证用户。
     * @param authentication 用户的认证信息，包含用户名和密码等。
     * @return 经过认证的Authentication对象，如果认证失败则抛出AuthenticationException异常。
     * @throws AuthenticationException 如果认证失败，抛出该异常。
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // 从authentication对象中提取用户名和密码  前端传过来的
        String userName = authentication.getPrincipal().toString();
        String passWord = authentication.getCredentials().toString();
        //从用户详情服务加载用户信息
        //所以此处需要自定义用户详情类，该类需要实现UserDetails接口
        //是SpringSecurity规定的，用来保存用户信息的类
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        //检查密码是否匹配，如果不匹配就抛出异常
        if (!userDetails.getPassword().equals(passWord)){
            throw new BadCredentialsException("用户名密码不正确，请重新登录！");
        }
        // 如果认证成功，返回新的认证令牌
        return new UsernamePasswordAuthenticationToken(userDetails,passWord,userDetails.getAuthorities());
    }
    /**
     * 实现AuthenticationProvider接口的supports方法，指定本提供者支持的认证类型
     * @param authentication 需要判断是否支持的认证类型
     * @return 支持该认证类型
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
