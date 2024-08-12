package cn.tedu.ivos.user.service;

import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import cn.tedu.ivos.user.pojo.dto.UserQuery;
import cn.tedu.ivos.user.pojo.dto.UserSaveParam;
import cn.tedu.ivos.user.pojo.vo.UserVO;

import java.util.List;

/**
 * 定义对应的需要的功能方法
 */
public interface UserService {
    UserVO login(UserLoginParam userLoginParam);
    void saveUser(UserSaveParam userSaveParam);
    List<UserVO> selectUser(UserQuery userQuery);
    void resetPassword(Long userId);
    void updateStatus(Long userId, String status);
    void deleteUser(Long userId);
    List<UserVO> selectAuditList(Long parentId);
}
