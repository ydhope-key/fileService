package com.heiyu707.dao;

import com.heiyu707.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    User login(User user);
}
