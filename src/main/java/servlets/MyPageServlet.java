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
        System.out.println("Inside doGet method of MyPageServlet");
        UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");

        System.out.println("UserBean ID: " + userBean.getId());
        System.out.println("User State: " + state);

        if (state != null && state.equals("confirmed") && userBean != null) {
            String userType = userBean.getUserType().toString();

            System.out.println("User Type: " + userType);

            switch (userType) {
                case "student":
                    String userId = userBean.getId();
                    System.out.println("Student ID: " + userId);
                    LinkedList<String[]> studentCoursesData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userId);
                    System.out.println("Student Courses Data: " + studentCoursesData);
                    request.setAttribute("studentCoursesData", studentCoursesData);
                    request.getRequestDispatcher("myPage.jsp").forward(request, response);
                    break;
                case "teacher":
                    // Teacher-specific logic
                    // TODO: Implement teacher-specific logic
                    break;
                case "superadmin":
                    // Superadmin-specific logic
                    // TODO: Implement superadmin-specific logic
                    break;
                default:
                    // Handle unknown user types
                    // TODO: Implement logic for unknown user types
                    break;
            }
        } else {
            // User is not logged in, redirect to login page
            System.out.println("User not confirmed, redirecting to login page");
            response.sendRedirect("login.jsp");
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


        req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
    }
}
