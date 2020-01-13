package org.example.dao;

import org.example.enntity.User;
import org.example.mapper.UserMapper;
import org.example.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class UserDaoTest {
    public static Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    public static void main(String[] args){
        logger.info("application start....");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("org.example.**");
        ctx.refresh();
        EchoService echoService = (EchoService) ctx.getBean("echoService");
        echoService.echo("hello word!");
        UserMapper userDao = (UserMapper) ctx.getBean("userMap");
        logger.info("create table success = " + userDao.createTable());
        logger.info("insert user id = " + userDao.insert(new User("Mark")));
        logger.info("insert user id = " + userDao.insert(new User("John")));

        logger.info("user=" + userDao.getById(01));
        List<User> users = userDao.getAll();
        if(users!= null){
            users.forEach(user -> logger.info("user=" + user));
        }
        logger.info("application end...");
    }
}
