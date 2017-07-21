package me.ilcb.service;


import me.ilcb.domain.User;

/**
 * Created by Jasper on 2017/4/18.
 */
public interface UserService {
    public void addUser(User user);
    public User getUser(String key);
}
