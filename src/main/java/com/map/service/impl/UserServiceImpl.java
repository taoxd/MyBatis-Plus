package com.map.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.map.dao.UserMapper;
import com.map.entity.User;
import com.map.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
