package org.example.config;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.example.mapper.UserMapper;
import org.example.cache.MemoryCache;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@EnableAspectJAutoProxy
@Configuration
@PropertySource(value = "classpath:config.properties")
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public MemoryCache memoryCache(){
        logger.debug("init memory cache bean...");
        return MemoryCache.getInstance();
    }

    // 创建数据库h2
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws Exception{
        logger.debug("h2 database start...");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8043");
    }


    @Bean
    public DriverManagerDataSource driverManagerDataSource(@Value("${h2.url}") String url,
                                              @Value("${h2.driver}") String driver){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    @Qualifier("dataSource")
    public DataSource dataSource(@Value("${h2.url}") String url,
                                      @Value("${h2.driver}") String driver){
        logger.debug("url=" + url + ", driver=" + driver);
        PooledDataSourceFactory factory = new PooledDataSourceFactory();
        Properties ps = new Properties();
        ps.setProperty("driver", driver);
        ps.setProperty("url", url);
        ps.setProperty("username", "");
        ps.setProperty("password", "");
        factory.setProperties(ps);
        return factory.getDataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource){
//        org.apache.ibatis.logging.LogFactory.useStdOutLogging();
        org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return  sqlSessionFactory;
    }

    @Bean
    public  BeanTest beanTestOne(){
        logger.debug("run beanTestOne...");
        return new BeanTest(daoTest());
    }

    @Bean
    public BeanTest beanTestTwo(){
        logger.debug("run beanTestTwo...");
        return new BeanTest(daoTest());
    }

    @Bean
    public DaoTest daoTest(){
        logger.debug("run daoTest...");
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
