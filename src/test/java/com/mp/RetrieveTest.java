package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.map.Application;
import com.map.dao.UserMapper;
import com.map.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:28
 * @Description: 查询测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RetrieveTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        //根据id查询
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
    }

    @Test
    public void insert() {
        //根据id批量查询
        List<Long> idsList = Arrays.asList(1087982257332887553L, 1088248166370832385L);
        List<User> userList = userMapper.selectBatchIds(idsList);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        //根据map条件查询,map中的key是数据库中的列名
        //map.put("name","闪闪")
        //map.put("age",2)
        //where name = "闪闪" and age = 2
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "刘德华1");
        columnMap.put("age", "40");
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    /**
     * 1.名字中包含雨并且年龄小于40
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.<User>query();
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 2.名字中包含雨并且年龄大于等于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.like("name", "向").between("age", 20, 40).isNotNull("email");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 3.名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age >= 25 order by age desc,id asc
     */
    @Test
    public void selectByWrapper3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.likeRight("name", "刘").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 4.创建日期为2019年2月14日并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d') = '2019-06-07' and manager_id in (select id from user where name like '王%')
     * <p>
     * SELECT id,name,age,email,manager_id,create_time FROM user
     * WHERE date_format(create_time,'%Y-%m-%d')=? AND manager_id IN (select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-06-07")
                //会有sql注入风险
                //SELECT id,name,age,email,manager_id,create_time FROM user
                // WHERE date_format(create_time,'%Y-%m-%d')='2019-06-07' AND manager_id IN (select id from user where name like '王%')
                //queryWrapper.apply("date_format(create_time,'%Y-%m-%d')='2019-06-07'")
                .inSql("manager_id", "select id from user where name like '王%'");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 5.名字为王姓并且(年龄小于40或邮箱不为空)
     * name like '王%' and (age < 40 or email is not null)
     */
    @Test
    public void selectByWrapper5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 6.名字为王姓或者(年龄小于40并且年龄大于20并且邮箱不为空)
     * name like '王%' or (age < 40 and age > 20 and email is not null)
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.likeRight("name", "王")
                .or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 7.(年龄小于40或邮箱不为空)并且名字为王姓
     * or的优先级小于and，不加括号，会先执行email is not null and name like '王%'
     * (age < 40 or email is not null) and name like '王%'
     */
    @Test
    public void selectByWrapper7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email")).likeRight("name", "王%");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 8.年龄为30、31、34、35
     * age in (30、31、34、35)
     */
    @Test
    public void selectByWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.in("age", Arrays.asList(31, 40));
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 9.只返回满足条件的其中一条语句即可
     * last()无视优化规则，直接拼到sql最后，只能调用一次,多次调用，以最后一次为准
     * limit 1
     */
    @Test
    public void selectByWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //直接调用，默认and连接
        queryWrapper.in("age", Arrays.asList(31, 40)).last("limit 1");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 10.名字中包含雨并且年龄小于40
     * 只查询id和name
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapperSuper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.<User>query();
        queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 11.名字中包含雨并且年龄小于40
     * 不查询create_time和manager_id
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWrapperSuper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.<User>query();
        queryWrapper.like("name", "雨").lt("age", 40)
                .select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * condition的作用
     * 控制条件是否加入到where语句中
     * 默认true添加
     */
    @Test
    public void testCondition() {
        String name = "王";
        String email = "";
        condition(name, email);
    }

    /**
     * 假设传入两个参数
     *
     * @param name
     * @param email
     */
    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //传统写法
/*        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(email)){
            queryWrapper.like("email",email);
        }*/
        queryWrapper.like(!StringUtils.isEmpty(name), "name", name)
                .like(!StringUtils.isEmpty(email), "email", email);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }
}
