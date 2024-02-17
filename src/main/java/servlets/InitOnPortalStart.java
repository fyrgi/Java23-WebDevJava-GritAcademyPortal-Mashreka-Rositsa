package servlets;

import models.UserBean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitOnPortalStart  implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent event){
        UserBean userBean = new UserBean();
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){
            ServletContextListener.super.contextDestroyed(sce);
        }
}

