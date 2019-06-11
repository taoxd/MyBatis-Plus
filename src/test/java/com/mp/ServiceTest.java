package com.mp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.map.Application;
import com.map.entity.User;
import com.map.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:28
 * @Description: service测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getOne() {
        //查出来多个会报错
        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25));
        //不报错，会警告，但会返回第一个
        //User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25),false);
        System.out.println(one);
    }

    @Test
    public void batch() {
        User user1 = new User();
        user1.setName("徐丽3");
        user1.setAge(28);

        User user2 = new User();
        user2.setId(1138259534262312962L);
        user2.setName("徐力诚");
        user2.setAge(30);

        List<User> userList = Arrays.asList(user1, user2);
        //boolean saveBatch = userService.saveBatch(userList);
        boolean saveBatch = userService.saveOrUpdateBatch(userList);
        System.out.println(saveBatch);
    }

    @Test
    public void chain() {
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }

    @Test
    public void chain1() {
        boolean update = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println(update);
    }

    @Test
    public void chain2() {
        boolean update = userService.lambdaUpdate().eq(User::getAge, 24).remove();
        System.out.println(update);
    }
}
