package servlets;

import models.DBConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Fetch course data using the selectQuery method
        LinkedList<String[]> coursesData = DBConnector.getConnector().selectQuery("showAllCourses", "gritacademy", "localhost", "3308", "User3", "user");

        // Set the course data as a request attribute
        req.setAttribute("coursesData", coursesData);

        // request to the courses.jsp for rendering
        req.getRequestDispatcher("/courses.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
