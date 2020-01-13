package org.example;

import org.example.Server.DiscardNettyServer;
import org.example.Server.JettyServer;
import org.example.Server.NettyServer;
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
        testJetty();
    }

    public static void testJetty(){
        JettyServer.server();
    }
    public static void testNettyDiscardServer(){
        DiscardNettyServer.DiscardNettyServer();
    }



}
