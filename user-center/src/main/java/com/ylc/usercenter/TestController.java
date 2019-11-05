package com.ylc.usercenter;

import com.ylc.usercenter.dao.user.UserMapper;
import com.ylc.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description: TODO
 * @Date: 2019/9/15
 */
@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/insert")
    public User testInsert(){
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);

        return user;
    }
}
