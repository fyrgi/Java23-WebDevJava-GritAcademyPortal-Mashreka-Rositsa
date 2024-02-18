package servlets;

import models.DBConnector;
import models.PRIVILEGE_TYPE;
import models.USER_TYPE;
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

        UserBean userBeanCon = (UserBean) getServletContext().getAttribute("userBean");
        UserBean userBeanSess = (UserBean) req.getSession().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");
        LinkedList<String[]> coursesData;
        LinkedList<String> tableHeaders;
        if(state == "confirmed" && userBeanSess != null){
            // userBean is stored in session after login. The one in
            // Now the correct data can be retrieved from the bean
            UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
            String userType = String.valueOf(userBean.getUserType());
            String privilegeType = String.valueOf(userBean.getPrivilegeType());
            if(!userType.equals("teacher")) {
                String student = "student";
                // set the context attribute to find next action in myPage
                req.getSession().setAttribute("caller", student);
                //TODO get the specific data for the student with your query
                coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
                // send the data to the Context and use it later when needed (tableView.JSP)
                getServletContext().setAttribute("coursesData", coursesData);
                tableHeaders = new LinkedList<>();
                //TODO write tableHeaders like the row below
                tableHeaders.add("Course name");
                // send the headers to the Context and use it later when needed (tableView.JSP)
                getServletContext().setAttribute("tableHeaders", tableHeaders);
                // go back to myPage to call your result jsp
                req.getRequestDispatcher("myPage.jsp").forward(req, resp);
            } else if (privilegeType.equals("superadmin")){
                System.out.println("No access");
            } else {
                String teacher = "teacher";
                req.getSession().setAttribute("caller", teacher);
                coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
                getServletContext().setAttribute("coursesData", coursesData);
                tableHeaders = new LinkedList<>();
                tableHeaders.add("ID");
                tableHeaders.add("Course");
                tableHeaders.add("Points");
                tableHeaders.add("Description");
                getServletContext().setAttribute("tableHeaders", tableHeaders);
                req.getRequestDispatcher("myPage.jsp").forward(req, resp);
            }
        }else if (state == "anonymous"){
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
