package cn.tedu.ivos.user.service.impl;

import cn.tedu.ivos.base.exception.ServiceException;
import cn.tedu.ivos.base.response.StatusCode;
import cn.tedu.ivos.user.mapper.UserMapper;
import cn.tedu.ivos.user.pojo.dto.UserLoginParam;
import cn.tedu.ivos.user.pojo.vo.UserVO;
import cn.tedu.ivos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
}
