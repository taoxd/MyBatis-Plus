package com.mp;

import com.map.Application;
import com.map.dao.UserMapper;
import com.map.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:28
 * @Description: 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(5, list.size());
        list.forEach(System.out::println);

    }
}
