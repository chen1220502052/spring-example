package org.example.controller;

import org.example.dao.UserDao;
import org.example.enntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        if(id == null){
            return null;
        }
        return  userDao.get(id);
    }

}
