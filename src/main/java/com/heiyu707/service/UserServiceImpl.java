package com.heiyu707.service;

import com.heiyu707.dao.UserDAO;
import com.heiyu707.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User login(User user) {

        return userDAO.login(user);
    }
}
