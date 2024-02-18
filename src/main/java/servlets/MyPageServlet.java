package servlets;

import models.DBConnector;
import models.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(("/mypage"))
public class MyPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");
        System.out.println(state);
        LinkedList<String[]> studentCoursesData ;


        if (userBean != null) {
            String userType = String.valueOf(userBean.getUserType());
            switch (userType) {
                case "student":
                    studentCoursesData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userBean.getId());
                    request.setAttribute("studentCourses", studentCoursesData);
                    request.getRequestDispatcher("/myPage.jsp").forward(request, response);
                    break;
                case "teacher":
                    // Teacher-specific logic
                    break;
                case "superadmin":
                    // Superadmin-specific logic
                    response.sendRedirect("superadmin_dashboard.jsp");
                    break;
                default:
                    // Handle unknown user types
                    response.sendRedirect("login.jsp");
                    break;
            }
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.jsp");
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
    }
}
