package me.ilcb.service.impl;

import me.ilcb.dao.impl.UserDaoImpl;
import me.ilcb.domain.User;
import me.ilcb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jasper on 2017/4/18.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl userDao;

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUser(String key) {
        User user = userDao.getUser(key);
        return user;
    }
}
