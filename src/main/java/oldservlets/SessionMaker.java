package oldservlets;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sessionMaker")
public class SessionMaker extends HttpServlet {
    //@Override
    //public void init(ServletConfig config) {
    //config.getServletContext().setInitParameter("WelcomeFromInit", "Hello from servlet contextMaker INIT");
    //}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       req.getSession().setAttribute("Name", "Rosi");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //getServletContext().setAttribute("WelcomePost", "Hello from servlet contextMaker POST");
    }
}
