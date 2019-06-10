package com.mp;

import com.map.Application;
import com.map.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:28
 * @Description: AR测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ARTest {

    @Test
    public void insert() {
        /**
         * 如果自己没有配置主键策略，MP的默认主键策略是基于雪花算法的自增主键
         */
        User user = new User();
        user.setName("张飞");
        user.setAge(24);
        user.setEmail("zf@qq.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insert();
        System.out.println(insert);
    }

    @Test
    public void selectById() {
        User user = new User();
        User userSelect = user.selectById(1138007682149101570L);
        System.out.println(userSelect);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1138007682149101570L);
        User userSelect = user.selectById();
        System.out.println(userSelect);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1138007682149101570L);
        user.setName("张飞飞");
        boolean updateById = user.updateById();
        System.out.println(updateById);
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setId(1138007682149101570L);
        boolean deleteById = user.deleteById();
        System.out.println(deleteById);
    }

    /**
     * 无主键id就插入
     * 有主键id就更新
     */
    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setName("张强");
        user.setAge(24);
        user.setEmail("zq@qq.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insertOrUpdate = user.insertOrUpdate();
        System.out.println(insertOrUpdate);
    }
}
