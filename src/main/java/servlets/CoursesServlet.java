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
        LinkedList<String[]> coursesData;
        LinkedList<String> tableHeaders;
        if(state == "confirmed"){
            String lala = "lala";
            req.getSession().setAttribute("lala", lala);
            coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
            getServletContext().setAttribute("coursesData", coursesData);
            tableHeaders = new LinkedList<>();
            tableHeaders.add("ID");
            tableHeaders.add("Course");
            tableHeaders.add("Points");
            tableHeaders.add("Description");
            System.out.println(tableHeaders.get(3));
            getServletContext().setAttribute("tableHeaders", tableHeaders);
            req.getRequestDispatcher("myPage.jsp").forward(req,resp);
        } else if(state == "anonymous"){
            // Fetch course data using the selectQuery method
            coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
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
