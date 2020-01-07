package org.example;

import org.example.Server.JettyServer;
import org.example.dao.UserDao;
import org.example.enntity.User;
import org.example.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

public class StartUp {

    public static Logger logger = LoggerFactory.getLogger(StartUp.class);

    public static void main(String[] args){
        server();
    }

    public static void server(){
        JettyServer server = new JettyServer();
        try {
            server.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void test(){
        logger.info("application start....");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("org.example.**");
        ctx.refresh();
        EchoService echoService = (EchoService) ctx.getBean("echoService");
        echoService.echo("hello word!");
        UserDao userDao = (UserDao) ctx.getBean("userDao");
        logger.info("create table success = " + userDao.createTable());
        logger.info("insert user id = " + userDao.insert("Mark"));
        logger.info("insert user id = " + userDao.insert("John"));

        logger.info("user=" + userDao.get(01));
        List<User> users = userDao.getAll();
        if(users!= null){
            users.forEach(user -> logger.info("user=" + user));
        }
        logger.info("application end...");
    }

    public static void testAddUser(WebApplicationContext ctx){
        UserDao userDao = (UserDao) ctx.getBean("userDao");
        logger.info("create table success = " + userDao.createTable());
        logger.info("insert user id = " + userDao.insert("Mark"));
        logger.info("insert user id = " + userDao.insert("John"));
    }
}
