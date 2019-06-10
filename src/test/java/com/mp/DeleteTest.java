package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.map.Application;
import com.map.dao.UserMapper;
import com.map.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:28
 * @Description: 删除测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1137993841495302145L);
        System.out.println("删除条数: " + rows);
    }

    /**
     * DELETE FROM user WHERE name = ? AND age = ?
     */
    @Test
    public void deleteByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "向后");
        columnMap.put("age", 28);

        int rows = userMapper.deleteByMap(columnMap);
        System.out.println("删除条数: " + rows);
    }

    /**
     * DELETE FROM user WHERE id IN ( ? , ? )
     */
    @Test
    public void deleteBatchIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1137993791062953985L, 1137993746813091841L));
        System.out.println("删除条数: " + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getAge, 26).eq(User::getName, "周雨");
        int rows = userMapper.delete(lambdaQuery);
        System.out.println("删除条数: " + rows);
    }
}
