package org.example;

import org.example.config.AppConfig;
import org.example.service.EchoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class StartUp {
    public static void main(String[] args){
        System.out.println("application start....");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("org.example.**");
        ctx.refresh();
        EchoService echoService = (EchoService) ctx.getBean("echoService");
        echoService.echo("hello word!");
        System.out.println("application end...");
    }
}
