package servlets;

import models.DBConnector;
import models.USER_TYPE;
import models.UserBean;
import models.PRIVILEGE_TYPE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve userBean and state from session
        HttpSession session = req.getSession();
        UserBean userBean = (UserBean) session.getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");

        // Check if the user is logged in, a teacher, and has admin privileges
        if (userBean != null && userBean.getUserType() == USER_TYPE.teacher && userBean.getPrivilegeType() == PRIVILEGE_TYPE.admin && state.equals("confirmed")) {
            // Forward to the registration page
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            // Redirect to login page
            resp.sendRedirect("/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String town = request.getParameter("town");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        String state = (String) getServletContext().getAttribute("userState");

        // Get session
        HttpSession session = request.getSession();

        // Check if the user has admin privileges
        UserBean userBean = (UserBean) session.getAttribute("userBean");
        if (userBean != null && userBean.getPrivilegeType() == PRIVILEGE_TYPE.admin && userBean.getUserType() == USER_TYPE.teacher && state.equals("confirmed")) {
            // Insert the new user into the appropriate table based on userType
            boolean registrationSuccessful = false;
            if (userType.equals("student")) {
                registrationSuccessful = DBConnector.getConnector().insertQuery("registerNewStudent", id, firstName, lastName, town, email, phone, username, password);
            } else if (userType.equals("teacher")) {
                registrationSuccessful = DBConnector.getConnector().insertQuery("registerNewTeacher", id, firstName, lastName, town, email, phone, username, password);
            }

            if (registrationSuccessful) {
                // Registration successful, update userBean with new id
                userBean.setId(id);
                session.setAttribute("userBean", userBean);
                session.setAttribute("userState", String.valueOf(userBean.getStateType()));
                response.sendRedirect("/register.jsp");
            } else {
                // Registration failed

                response.sendRedirect("/error.jsp");
            }
        } else {
            // User does not have admin privileges or is not a teacher

            response.sendRedirect("/error.jsp");
        }
    }
}
