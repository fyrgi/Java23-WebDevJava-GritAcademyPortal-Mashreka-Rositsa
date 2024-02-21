package servlets;

import models.*;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/**
 * Servlet that is responsible for the way we create a Bean or navigate user in case of a login attempt.
 * */

@WebServlet("/login")
public class LoginServlet extends HttpServlet implements HttpSessionListener  {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // when the page is called we want to make sure of the global state in context.
        // in the state we will save value anonymous if the user is not logged in and
        // confirmed if they have successfully logged in
        String currState = (String) getServletContext().getAttribute("userState");
        // we don't sue the Error message now,
        // but later it may be used to store information about unsuccessful attempt of login
        req.getSession().setAttribute("errorMessage","");
        System.out.println(currState);
        // if the user is not logged in they will be able to see the login page which calls the login form
        if(currState == null || currState.equals("anonymous")){
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        } else if (currState.equals("confirmed")) {
            // otherwise they will be forwarded to their view of myPage
            req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        //retrieving data from loginForm
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("user_type");
        // success will only become true if a user logs in with right username and password
        boolean success = false;
        // user will be used in teacher to determine what is it stored in the database
        String[] user;
        if (userType.equals("student")) {
            LinkedList<String[]> data = DBConnector.getConnector().selectQuery("studentLogin", username, password);
            // the first returned row is always the table columns
            if (data.size() > 1) {
                System.out.println(data.size());
                req.getSession().setMaxInactiveInterval(10);
                // create a bean for the student. Later we will have to eaven save info about ID of the connected user
                UserBean userBean = new UserBean((data.get(1))[0],USER_TYPE.student, "user",STATE_TYPE.confirmed);
                // updates the session with the new data in the userBean. I will try to initialize it as well
                req.getSession().setAttribute("userBean", userBean);
                System.out.println("FROM LOGIN Context" + req.getServletContext().getAttribute("userBean"));

                System.out.println("FROM Login Session " + req.getSession().getAttribute("userBean"));
                // execute the steps after successful login
                success = true;
            }
        } else if (userType.equals("teacher")) {
            LinkedList<String[]> data = DBConnector.getConnector().selectQuery("teacherLogin", username, password);
            if (data.size() > 1) {
                req.getSession().setMaxInactiveInterval(10);
                // get the array with data about the teacher and find the LAST COLUMN IN THE DATABASE. If it is changed
                // userbean will get unexpected data for privilege type but it has a default on user so the system won't crash
                user = data.get(1);
                UserBean userBean = new UserBean((data.get(1))[0],USER_TYPE.teacher, user[user.length-1],STATE_TYPE.confirmed);
                req.getSession().setAttribute("userBean", userBean);
                success = true;
            }
        } else {
            // if the user did not exist or something else went wrong like wrong password
            // we keep their state as anonymous
            getServletContext().setAttribute("userState", "anonymous");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
        // here are the actions performed if success is true or false.
        // It is true if the user provided right pair username and password.
        if(success){
            // the state is no longer anonymous
            getServletContext().setAttribute("userState", "confirmed");
            // the user gets redirected to their version of my page later detirmined in the JSP
            req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
        } else {
            // we prepare an error message which currently does not work and it is not optimal
            req.getSession().setAttribute("errorMessage","User not found");
            // ensure that the state stays anonymous
            getServletContext().setAttribute("userState", "anonymous");
            // send them back to login. Might display error based on errorMessage later.
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

}