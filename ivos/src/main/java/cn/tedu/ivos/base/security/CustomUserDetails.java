package cn.tedu.ivos.base.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义用户详情类，实现Spring Security的UserDetails接口
 * 该类用于存储和管理用户的认证信息
 */
@Data
public class CustomUserDetails implements UserDetails {
  private Long userId; // 用户ID
  private String username; // 用户名
  private String password; // 用户密码
  private Set<? extends GrantedAuthority> authorities; // 用户权限集合
    /**
     * 判断用户的账户是否未过期。
     * @return 总是返回true，表示账户永不过期。
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 判断用户的账户是否未被锁定。
     * @return 总是返回true，表示账户永不被锁定。
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    /**
     * 判断用户的凭证（密码）是否未过期。
     * @return 总是返回true，表示凭证永不过期。
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    /**
     * 判断用户是否被启用。
     * @return 总是返回true，表示用户总是启用状态。
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
