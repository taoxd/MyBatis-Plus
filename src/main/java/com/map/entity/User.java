package com.map.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Auther: taoxd
 * @Date: 2019/6/4 23:21
 * @Description: 与数据库表对应的实体类
 * 默认数据库下划线对应实体驼峰或下划线，如manager_id对应managerId
 */
@Data
@TableName("user")//设置数据库表名
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     *
     * 局部策略>全局策略
     *
     * IdType.AUTO数据库ID自增策略
     * IdType.NONE默认策略，跟随全局，无id默认雪花算法，有id就直接用
     * IdType.INPUT用户输入ID
     *
     * 以下3种类型，只有当插入对象ID为空，才自动填充
     * IdType.ID_WORKER，雪花算法
     * IdType.ID_WORKER_STR，雪花算法字符串
     * IdType.UUID，全局唯一ID
     */
    @TableId(value = "id",type = IdType.NONE)//配置数据库主键
    private Long id;
    //姓名
    @TableField("name")//配置数据库字段
    private String name;
    //年龄,忽略null值和空字符串
    @TableField(strategy = FieldStrategy.NOT_EMPTY)
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
