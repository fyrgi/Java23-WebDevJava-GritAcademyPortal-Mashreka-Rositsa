package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contextMaker")
public class ContextMaker extends HttpServlet {
    //@Override
    //public void init(ServletConfig config) {
        //config.getServletContext().setInitParameter("WelcomeFromInit", "Hello from servlet contextMaker INIT");
    //}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getServletContext().setAttribute("WelcomeGet", "Hello from servlet contextMaker GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getServletContext().setAttribute("WelcomePost", "Hello from servlet contextMaker POST");
    }
}
