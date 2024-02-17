package servlets;

import models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println();
        req.getSession().setAttribute("errorMessage","");
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        //retrieving data from loginForm
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("user_type");


        //comparing data with DB student or teacher
        if (userType.equals("student")) {
            LinkedList<String[]> data = DBConnector.getConnector().selectQuery("studentLogin", username, password);
            //data object always returns row with column names
            if (data.size() > 1) {
                req.getSession().setMaxInactiveInterval(0);
                UserBean userBean = new UserBean((data.get(1))[0],USER_TYPE.student, PRIVILAGE_TYPE.user,STATE_TYPE.confirmed);
                req.getSession().setAttribute("userBean", userBean);
                req.getRequestDispatcher("/myPage").forward(req,resp);
            }else{//if login not found goes back to login form and sows a message
                req.getSession().setAttribute("errorMessage","Student not found");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }else if (userType.equals("teacher")) {
            List data = DBConnector.getConnector().selectQuery("teacherLogin", username, password);
            //data object always returns row with column names
            if (data.size() > 1) {
                resp.getWriter().print("LOGGED IN - ");
                //TODO similar to the student code
            }else{
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }
    }
}