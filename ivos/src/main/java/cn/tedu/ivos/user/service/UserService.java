package cn.tedu.ivos.user.service;

import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import cn.tedu.ivos.user.pojo.vo.UserVO;

/**
 * 定义对应的需要的功能方法
 */
public interface UserService {
    UserVO login(UserLoginParam userLoginParam);
}
