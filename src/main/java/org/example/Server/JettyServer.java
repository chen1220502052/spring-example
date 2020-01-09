package org.example.Server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.StartUp;
import org.example.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class JettyServer {
    private static int HTTP_PORT = 8090;
    private static String CONTEXT_PATH = "/";
    private static String MAPPING_URL = "/*";

    private static Logger logger = LoggerFactory.getLogger(JettyServer.class);

    @Value("${http_port}")
    private int httpPort;

    @Value("$context_path")
    private String contextPath;

    @Value("${mapping_url}")
    private String mappingUrl;

    private Server server = null;

    private WebApplicationContext webApplicationContext;
    private DispatcherServlet dispatcherServlet;

    public JettyServer(){

    }
    public void init(){
        if(httpPort <= 0){
            httpPort = HTTP_PORT;
        }
        server = new Server(httpPort);
        if(StringUtils.isEmpty(contextPath)){
            contextPath = CONTEXT_PATH;
        }
        if(StringUtils.isEmpty(mappingUrl)){
            mappingUrl = MAPPING_URL;
        }
        if(webApplicationContext == null){
            webApplicationContext = defaultWebApplicationContext();
        }
        server.setHandler(servletContextHandler(webApplicationContext,
                contextPath, mappingUrl));
        logger.info("jetty server init: port="+httpPort + ";init handler...");

    }
    public void run() throws Exception{
        server.start();
        logger.info("jetty server start: port="+httpPort);
        logger.info("servlet context: " + ((ServletContextHandler)server.getHandler()).getServletHandler().getServletContext());
//        Runtime.getRuntime().addShutdownHook(new Thread(()->{
//            server.setStopAtShutdown(true);
//        }));
//        server.join();
    }

    private ServletContextHandler servletContextHandler(WebApplicationContext webApplicationContext,
                                                        String contextPath, String mappingUrl){
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(contextPath);
        dispatcherServlet = new DispatcherServlet(webApplicationContext);
        CharacterEncodingFilter setEncoding = new CharacterEncodingFilter("UTF-8");
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        ServletHolder servletHolder = new ServletHolder("dispatcherServlet", dispatcherServlet);
        servletContextHandler.addServlet(servletHolder, mappingUrl);
        FilterHolder filterHolder = new FilterHolder(setEncoding);
        filterHolder.setName("setEncoding");
        servletContextHandler.addFilter(filterHolder, "/*", dispatcherTypes);
        servletContextHandler.addEventListener(new ContextLoaderListener(webApplicationContext));

        return servletContextHandler;
    }

    private WebApplicationContext defaultWebApplicationContext(){
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("org.example.**");
//        ctx.refresh();
        return ctx;
    }

    public static void server(){
        JettyServer server = new JettyServer();
        try {
            server.init();
            server.run();
            initUserTable(server.webApplicationContext);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public static void initUserTable(WebApplicationContext ctx){
        UserDao userDao = (UserDao) ctx.getBean("userDao");
        logger.info("create table success = " + userDao.createTable());
        logger.info("insert user id = " + userDao.insert("Mark"));
        logger.info("insert user id = " + userDao.insert("John"));
        logger.info("insert user id = " + userDao.insert("李三"));
    }

}
