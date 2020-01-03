package org.example.config;

import org.example.cache.MemoryCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

    @Bean
    public MemoryCache memoryCache(){
        System.out.println("init memory cache bean...");
        return MemoryCache.getInstance();
    }

    @Bean
    public  BeanTest beanTestOne(){
        System.out.println("run beanTestOne...");
        return new BeanTest(daoTest());
    }

    @Bean
    public BeanTest beanTestTwo(){
        System.out.println("run beanTestTwo...");
        return new BeanTest(daoTest());
    }

    @Bean
    public DaoTest daoTest(){
        System.out.println("run daoTest...");
        return new DaoTest();
    }

    class BeanTest{
        private DaoTest daoTest;
        BeanTest(DaoTest daoTest){
            this.daoTest = daoTest;
        }
    }
    class DaoTest{

    }




}
