package servlets;

import models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currState = (String) getServletContext().getAttribute("userState");
        req.getSession().setAttribute("errorMessage","");
        if(currState == null || currState.equals("anonymous")){
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        } else {
            req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        //retrieving data from loginForm
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("user_type");
        boolean success = false;
        //comparing data with DB student or teacher
        if (userType.equals("student")) {
            LinkedList<String[]> data = DBConnector.getConnector().selectQuery("studentLogin", username, password);
            if (data.size() > 1) {
                req.getSession().setMaxInactiveInterval(600);
                UserBean userBean = new UserBean((data.get(1))[0],USER_TYPE.student, PRIVILEGE_TYPE.user,STATE_TYPE.confirmed);
                req.getSession().setAttribute("userBean", userBean);
                success = true;

            }
        } else if (userType.equals("teacher")) {
            LinkedList<String[]> data = DBConnector.getConnector().selectQuery("teacherLogin", username, password);
            if (data.size() > 1) {
                req.getSession().setMaxInactiveInterval(600);
                System.out.println(Arrays.toString(data.get(1)));
                UserBean userBean = new UserBean((data.get(1))[0],USER_TYPE.teacher, PRIVILEGE_TYPE.user,STATE_TYPE.confirmed);
                req.getSession().setAttribute("userBean", userBean);
                success = true;
            }
        } else {
            getServletContext().setAttribute("userState", "anonymous");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }

        if(success){

            getServletContext().setAttribute("userState", "confirmed");
            getServletContext().setInitParameter("initState","confirmed");
            req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
        } else {
            req.getSession().setAttribute("errorMessage","User not found");
            getServletContext().setAttribute("userState", "anonymous");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}