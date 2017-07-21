package me.ilcb.dao.impl;

import me.ilcb.dao.UserDao;
import me.ilcb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by Jasper on 2017/4/18.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private RedisTemplate redisTemplate;

    public void addUser(User user) {
        ValueOperations<String, User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(user.getId(), user);
    }

    public User getUser(String key) {
        ValueOperations<String, User> valueOperations = redisTemplate.opsForValue();
        User user = valueOperations.get(key);
        return user;
    }
}

