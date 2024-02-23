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

        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String town = request.getParameter("town");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        String state = (String) getServletContext().getAttribute("userState");
        if(firstName.trim().isEmpty() || lastName.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()){
            System.out.println("Names, username and password cannot be empty!");
        } else {
            boolean registrationSuccessful = false;
            if(state.equals("confirmed")){
                if (userType.equals("student")) {
                    DBConnector.getConnector().insertQuery("registerNewStudent", firstName, lastName, town, email, phone, username, password, "S","S","S","S","S","S","S");
                } else if (userType.equals("teacher")) {
                    DBConnector.getConnector().insertQuery("registerNewTeacher", firstName, lastName, town, email, phone, username, password, "S","S","S","S","S","S","S");
                } else {
                    System.out.println("No type chosen");
                }
                request.getRequestDispatcher("myPage.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("loggedout.jsp").forward(request, response);
            }
        }
    }
}
