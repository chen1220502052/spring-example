package org.example.Server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.StartUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

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

    public JettyServer(){

    }

    public void run() throws Exception{
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
        logger.info("jetty server init: port="+httpPort);
        server.setHandler(servletContextHandler(webApplicationContext,
                contextPath, mappingUrl));
        server.start();
        logger.info("jetty server start: port="+httpPort);
        logger.info("");
        StartUp.testAddUser(webApplicationContext);
        server.join();
    }

    private ServletContextHandler servletContextHandler(WebApplicationContext webApplicationContext,
                                                        String contextPath, String mappingUrl){
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(contextPath);
        servletContextHandler.addServlet(new ServletHolder(
                new DispatcherServlet(webApplicationContext)), mappingUrl);
        servletContextHandler.addEventListener(new ContextLoaderListener(webApplicationContext));
        return servletContextHandler;
    }

    private WebApplicationContext defaultWebApplicationContext(){
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("org.example.**");
//        ctx.refresh();
        return ctx;
    }

}
