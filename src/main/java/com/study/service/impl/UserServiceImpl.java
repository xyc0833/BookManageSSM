package com.study.service.impl;

import com.study.entity.Account;
import com.study.mapper.UserMapper;
import com.study.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    //用户登录之前的自定义验证 根据用户名加载用户详情（Spring Security 核心方法）
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 从数据库中根据用户名查询用户信息
        Account account = userMapper.findUserByName(username);
        // 2. 校验用户是否存在：若查询结果为空，抛出用户名不存在异常
        if(account == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 3. 封装 UserDetails 对象返回给 Spring Security
        //    Spring Security 会自动使用配置的密码编码器比对该密码与前端传入的密码
        return User
                .withUsername(account.getUsername())// 设置认证的用户名（与查询条件一致）
                .password(account.getPassword())// 注意：数据库中必须存储密文密码，且项目需配置对应的密码编码器）
                .roles(account.getRole())// 构建 UserDetails 对象（如需添加权限/角色，可在此处链式调用.roles()/.authorities()）
                .build();
    }
}
