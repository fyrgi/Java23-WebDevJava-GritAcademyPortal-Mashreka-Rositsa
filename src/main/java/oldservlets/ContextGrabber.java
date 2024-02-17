package oldservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/contextGrabber")
public class ContextGrabber extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/HTML");
        String form = "<form action=/contextMaker metod=POST><input type=submit value=\"Go to contextMaker\"></form>";
        resp.getWriter().println(getServletContext().getInitParameter("WelcomeMsg") + form);
        //resp.getWriter().println(getServletContext().getInitParameter("WelcomeFromInit"));
        String welcomeGet = (String) getServletContext().getAttribute("WelcomeGet");
        String welcomePost = (String) getServletContext().getAttribute("WelcomePost");
        resp.getWriter().println(welcomeGet + "<br>" + welcomePost);
    }
}
