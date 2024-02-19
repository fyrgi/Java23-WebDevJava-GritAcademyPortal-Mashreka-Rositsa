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
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet("/mypage")
public class MyPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean userBeanCon = (UserBean) getServletContext().getAttribute("userBean");
        UserBean userBeanSess = (UserBean) req.getSession().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");
        String userId = userBeanSess.getId();
        System.out.println(req.getParameter("sub"));
        if (state != null && state.equals("confirmed") && userBeanSess != null) {
            String userType = String.valueOf(userBeanSess.getUserType());
            LinkedList<String[]> databaseData;
            LinkedList<String> tableHeaders;
            if(state == "confirmed" && userBeanSess != null) {
                // userBean is stored in session after login. The one in
                // Now the correct data can be retrieved from the bean
                UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
                String privilegeType = String.valueOf(userBean.getPrivilegeType());
                // Here we will find if the user is student, super admin, or teacher in that order
                if (!userType.equals("teacher")) {
                    String student = "studentCourses";
                    // set the context attribute to find next action in myPage
                    req.getSession().setAttribute("caller", student);
                    databaseData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userId);
                    //System.out.println(Arrays.toString(Arrays.stream(databaseData.get(0)).toArray()));
                    tableHeaders = buildTableHeaders("ID", "Course Name", "Points", "Description", "Student name", "Teacher name");
                    req.getSession().setAttribute("coursesData", databaseData);
                    req.getSession().setAttribute("tableHeaders", tableHeaders);
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                } else if (privilegeType.equals("superadmin")) {
                    System.out.println("No access");
                } else {
                    // check from which submenu the teacher is coming. First take the parameter and assign it to a vriable
                    String comingFromSubMenu = req.getParameter("sub");
                    if(comingFromSubMenu!=null && comingFromSubMenu.equals("all-courses")){
                        // change the variable caller that will send info about who is asking for what to the JSP
                        // based on it the JSP will know what to do after and which HTML elemets to include or build
                        String teacher = "teacherAllCourses";
                        req.getSession().setAttribute("caller", teacher);
                        databaseData = DBConnector.getConnector().selectQuery("showAllCourses");
                        req.getSession().setAttribute("coursesData", databaseData);
                        // prepare the result table headers. We have to know in advance what we want to show in the table
                        // send them via context or session
                        tableHeaders = buildTableHeaders("ID", "Course Name", "Points", "Description");
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null && comingFromSubMenu.equals("all-students")){
                        String teacher = "teacherAllStudents";
                        req.getSession().setAttribute("caller", teacher);
                        databaseData = DBConnector.getConnector().selectQuery("showStudents");
                        req.getSession().setAttribute("coursesData", databaseData);
                        tableHeaders = buildTableHeaders("ID", "First name", "Last name", "city", "email", "phone");
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }
                }
            }
        } else {
            // User is not logged in, redirect to login page
            System.out.println("User not confirmed, redirecting to login page");
            resp.sendRedirect("login.jsp");
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


        req.getRequestDispatcher("/myPage.jsp").forward(req,resp);
    }

    protected LinkedList<String> buildTableHeaders(String...args){
        LinkedList<String> tableHeaders = new LinkedList<>();
        tableHeaders.addAll(Arrays.asList(args));
        return tableHeaders;
    }
}
