package com.mp;

import com.map.Application;
import com.map.dao.UserMapper;
import com.map.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:28
 * @Description: 插入测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        /**
         * 如果自己没有配置主键策略，MP的默认主键策略是基于雪花算法的自增主键
         */
        User user = new User();
        user.setName("向后");
        user.setAge(28);
        //user.setEmail("25656@qq.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setRemark("我是一个备注");
        int rows = userMapper.insert(user);
        System.out.println("影响记录数:" + rows);
    }
}
