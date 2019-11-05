package com.ylc.usercenter.service.user;

import com.ylc.usercenter.dao.user.UserMapper;
import com.ylc.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Date: 2019/9/15
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }
}

























