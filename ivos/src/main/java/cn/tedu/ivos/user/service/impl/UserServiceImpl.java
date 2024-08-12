package cn.tedu.ivos.user.service.impl;

import cn.tedu.ivos.base.exception.ServiceException;
import cn.tedu.ivos.base.response.StatusCode;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import cn.tedu.ivos.user.pojo.dto.UserQuery;
import cn.tedu.ivos.user.pojo.dto.UserSaveParam;
import cn.tedu.ivos.user.pojo.entity.User;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import cn.tedu.ivos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类实现接口时候 需要重写接口中的全部方法
 * 快捷键  alter+enter --》回车
 * @Controller  修饰控制器类
 * @Service      修饰service实现类
 * @Repository   修饰mapper接口
 * 被修饰的类 在项目启动的时候 进行加载
 * 把对应的类生成bean对象放在spring容器中 方便后面注入
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserVO login(UserLoginParam userLoginParam) {
        //根据用户名查询用户信息
        //判断查询结果 对象存不存在  密码对不对
        //userMapper.selectByUsername(userLoginParam.getUsername()).var; 然后右有提示 直接回车
        UserVO userVO = userMapper.selectByUsername(userLoginParam.getUsername());
        if (userVO==null){
            //用户对象不存在，抛出异常
            throw new ServiceException(StatusCode.USERNAME_ERROR);
        }
        if (!userVO.getPassword().equals(userLoginParam.getPassword())){
            //密码不一样，抛出异常
            throw new ServiceException(StatusCode.PASSWORD_ERROR);
        }
        return userVO;
    }
    @Override
    public void saveUser(UserSaveParam userSaveParam) {
        User user = new User();
        BeanUtils.copyProperties(userSaveParam,user);
        if(user.getId()==null){
            //新增用户时要判断该用户名是否存在
            if(userMapper.selectByUsername(user.getUsername())!=null){
                throw new ServiceException(StatusCode.USERNAME_ALREADY_EXISTS);
            }
            //注意:员工数据不是员工自己注册的，所以需要预设一个初始密码
            user.setPassword("123456");
            user.setCreateTime(new Date());
            userMapper.insert(user);
        }else{
            user.setUpdateTime(new Date());
            userMapper.update(user);
        }
    }
    @Override
    public List<UserVO> selectUser(UserQuery userQuery) {
        List<UserVO> list = userMapper.selectUser(userQuery);
        return list;
    }
    @Override
    public void resetPassword(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setPassword("admin");
        userMapper.update(user);
    }
    @Override
    public void updateStatus(Long userId, String status) {
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        userMapper.update(user);
    }
    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteById(userId);
    }
    @Override
    public List<UserVO> selectAuditList(Long parentId) {
        ArrayList<UserVO> userVOList = new ArrayList<>();
        //根据上级用户Id查询上级用户信息,添加到list中
        UserVO auditUser1 = userMapper.selectById(parentId);
        userVOList.add(auditUser1);
        //如果有查到上级审批人,且上级审批人还有上级
        if(auditUser1 != null&& auditUser1.getParentId() !=null ){
            //查询上上级,添加到list中
            UserVO auditUser2 = userMapper.selectById(auditUser1.getParentId());
            userVOList.add(auditUser2);
        }
        return userVOList;
    }
}
