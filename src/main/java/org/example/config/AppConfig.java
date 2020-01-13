package org.example.config;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
//import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.ibatis.transaction.TransactionFactory;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
//import org.example.mapper.UserMapper;
import org.example.cache.MemoryCache;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;

@EnableAspectJAutoProxy
@Configuration
@PropertySource(value = "classpath:config.properties")
@MapperScan("org.example.mapper")
@EnableTransactionManagement
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

    /**
     * 事务配置
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.addMappers("org.example.mapper");
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    /**
     * mybatis sqlsessionFactory的创建
     * @return
     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource){
////        org.apache.ibatis.logging.LogFactory.useStdOutLogging();
//        org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
////        configuration.addMapper(UserMapper.class);
//        configuration.addMappers("org.example.mapper");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//        return  sqlSessionFactory;
//    }

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
