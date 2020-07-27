package org.example.controller;

import org.example.contant.ResponseCode;
import org.example.dao.UserDao;
import org.example.enntity.FriendShip;
import org.example.enntity.Response;
import org.example.enntity.User;
import org.example.mapper.UserMapper;
import org.example.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/{id}")
    @ResponseBody
    public Response<User> getUser(@PathVariable Long id){
        if(id == null){
            return new Response<>(ResponseCode.PARAM_INVALID, "params are invalid, please check", null);
        }
        return  new Response<>(userDao.get(id));
    }

    @GetMapping("/all")
    @ResponseBody
    public List<User> getUser(){
        return userDao.getAll();
    }

    @GetMapping("/exception")
    @ResponseBody
    public void exception(){
            int i = 1/0;
    }


    @Autowired
    private FriendService friendService;
    @GetMapping("/friendship/add")
    @ResponseBody
    public Response<FriendShip> addFriend(@RequestParam("frid1")Long frid1, @RequestParam("frid2")Long frid2){
        if(frid1 == null || frid2 == null){
            return new Response<>(ResponseCode.PARAM_INVALID, "params are invalid, please check", null);
        }
        if(friendService.addFriend(frid1, frid2)){
            return new Response<>();
        }
        return new Response<>(ResponseCode.PARAM_INVALID, "params are invalid, please check", null);
    }
    @GetMapping("/friendship/{id}")
    @ResponseBody
    public Response<FriendShip> addFriend(@PathVariable("id")Long id){
        if(id == null){
            return new Response<>(ResponseCode.PARAM_INVALID, "params are invalid, please check", null);
        }
        return new Response<>(friendService.get(id));
    }


}
