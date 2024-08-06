package cn.tedu.ivos.base.security;


import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义用户详情业务类，实现Spring Security的UserDetailsService接口
 * 根据用户名加载用户详情
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名加载用户详情。
     * @param username 用户名
     * @return UserDetails对象，包含用户信息和权限
     * @throws UsernameNotFoundException 如果用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.debug("根据用户名查询用户详情{}",username);
        //检查用户名是否为null
        if (username==null){
            throw new UsernameNotFoundException("用户名不能为null");
        }
        //根据用户名查询用户信息
        UserVO userVO = userMapper.selectByUsername(username);
        // 创建自定义的UserDetails对象，并设置用户信息
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(userVO.getId());
        userDetails.setUsername(userVO.getUsername());
        userDetails.setPassword(userVO.getPassword());
        //初始化用户权限集合
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        // 创建一个示例权限对象，
        // 这里假设用户具有"example"权限，这里只是需要写一个名称，
        // 并没有具体的权限组，为空方法会报错
        GrantedAuthority authority = new SimpleGrantedAuthority("example");
        authoritySet.add(authority);
        // 设置用户的权限集合
        userDetails.setAuthorities(authoritySet);
        //记得一定要 返回数据
        return userDetails;
    }
}
