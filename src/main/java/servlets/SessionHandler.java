package servlets;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionHandler extends HttpServlet implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // Set session timeout to  10 seconds  (30 min minutes 1800 seconds)
        event.getSession().setMaxInactiveInterval(10);
        System.out.println("session started");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // Perform any cleanup or notification actions here
        System.out.println("Session ended");
        event.notifyAll();
    }
}