package servlets;

import models.DBConnector;
import models.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean userBean = (UserBean) getServletContext().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");
        System.out.println(state);
        LinkedList<String[]> coursesData;
        if(state == "confirmed"){
            String lala = "lala";
            req.getSession().setAttribute("lala", lala);
            coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
            for(int i = 0; i < coursesData.size(); i++){
                System.out.println(Arrays.toString(coursesData.get(i)));
            }
            req.setAttribute("coursesData", coursesData);
            System.out.println("I am now here");
            req.getRequestDispatcher("myPage.jsp").forward(req,resp);
        } else if(state == "anonymous"){
            // Fetch course data using the selectQuery method
            coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
            System.out.println("I am first here");
            // Set the course data as a request attribute
            req.setAttribute("coursesData", coursesData);

            // request to the courses.jsp for rendering
            req.getRequestDispatcher("/courses.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
