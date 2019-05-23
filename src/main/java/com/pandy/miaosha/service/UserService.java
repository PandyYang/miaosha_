package com.pandy.miaosha.service;

import com.pandy.miaosha.dao.UserDao;
import com.pandy.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Pandy
 * @Date: 2019/5/16 10:38
 * @Version 1.0
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean getTx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("fry");
        userDao.insert(u1);

        //1出现问题 会导致2的回滚
        User u2 = new User();
        u2.setId(1);
        u2.setName("roll back");
        userDao.insert(u2);

        return true;
    }
}
