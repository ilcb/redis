package me.ilcb.controller;

import me.ilcb.domain.User;
import me.ilcb.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Jasper on 2017/4/18.
 */
@Controller
@RequestMapping(value = "/redis")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    private User user;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "id", required = true) String id,
                          @RequestParam(value = "name", required = true) String name,
                          @RequestParam(value = "password", required = true) String password) {
        user = new User(id, name, password);
        userService.addUser(user);
        return "addUserSuccess";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser() {
        return "addUser";
    }

    @RequestMapping(value = "/queryUser")
    public String queryUser() {
        return "getUser";
    }

    @RequestMapping(value = "/getUser")
    public String getUser(@RequestParam(value = "key", required = true) String key, Model model) {
        user = userService.getUser(key);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("password", user.getPassword());
        return "showUser";
    }

}
