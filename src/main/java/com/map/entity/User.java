package com.map.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:21
 * @Description: 与数据库表对应的实体类
 * 默认数据库下划线对应实体驼峰或下划线，如manager_id对应managerId
 */
@Data
@TableName("user")//设置数据库表名
public class User {
    //主键
    @TableId("id")//配置数据库主键
    private Long id;
    //姓名
    @TableField("name")//配置数据库字段
    private String name;
    //年龄
    private Integer age;
    //邮箱
    private String email;
    //直属上级
    private Long managerId;
    //创建时间
    private LocalDateTime createTime;
    //备注(数据库中没有)
    @TableField(exist = false)
    private String remark;
    /**
     * 1.加关键字transient，不参与序列化
     * private transient String remark;
     *
     * 2.加关键字static
     * private static String remark;
     *
     * 3.添加注解@TableField(exist = false)
     */

}
