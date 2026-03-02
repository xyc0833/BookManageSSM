package com.study.mapper;

import com.study.entity.Account;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface UserMapper {

    @Select("select * from user where username=#{username}")
    Account findUserByName(String username);
}
