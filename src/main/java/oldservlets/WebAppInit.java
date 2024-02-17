package oldservlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//@WebListener
public class WebAppInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event){
        event.getServletContext().setInitParameter("WelcomeMsg", "Hello from servlet INITIALIZER");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        ServletContextListener.super.contextDestroyed(sce);
    }
}
