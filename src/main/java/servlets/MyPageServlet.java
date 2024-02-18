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

@WebServlet(("/mypage"))
public class MyPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean userBeanCon = (UserBean) getServletContext().getAttribute("userBean");
        UserBean userBeanSess = (UserBean) req.getSession().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");
        String userId = userBeanSess.getId();

        if (state != null && state.equals("confirmed") && userBeanSess != null) {
            String userType = String.valueOf(userBeanSess.getUserType());
            LinkedList<String[]> databaseData;
            LinkedList<String> tableHeaders;
            System.out.println("User Type: " + userType);
            if(state == "confirmed" && userBeanSess != null) {
                // userBean is stored in session after login. The one in
                // Now the correct data can be retrieved from the bean
                UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
                String privilegeType = String.valueOf(userBean.getPrivilegeType());
                if (!userType.equals("teacher")) {
                    String student = "studentCourses";
                    // set the context attribute to find next action in myPage
                    req.getSession().setAttribute("caller", student);
                    databaseData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userId);
                    //System.out.println(Arrays.toString(Arrays.stream(databaseData.get(0)).toArray()));
                    tableHeaders = buildTableHeaders("ID", "Course Name", "Points", "Description", "Student name", "Teacher name");
                    getServletContext().setAttribute("coursesData", databaseData);
                    getServletContext().setAttribute("tableHeaders", tableHeaders);
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                } else if (privilegeType.equals("superadmin")) {
                    System.out.println("No access");
                } else {
                    String teacher = "teacher";
                    req.getSession().setAttribute("caller", teacher);
                    databaseData = DBConnector.getConnector().selectQuery("showAllCourses");
                    getServletContext().setAttribute("coursesData", databaseData);
                    tableHeaders = new LinkedList<>();
                    tableHeaders.add("ID");
                    tableHeaders.add("Course");
                    tableHeaders.add("Points");
                    tableHeaders.add("Description");
                    getServletContext().setAttribute("tableHeaders", tableHeaders);
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
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
