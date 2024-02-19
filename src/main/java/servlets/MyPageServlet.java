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
        UserBean userBeanSess = null;
        String userId = "0";
        try {
             userBeanSess = (UserBean) req.getSession().getAttribute("userBean");
             userId = userBeanSess.getId();
        } catch (NullPointerException ex){

        }
        String state = (String) getServletContext().getAttribute("userState");
        System.out.println(req.getParameter("sub"));
        if (state != null && state.equals("confirmed") && userBeanSess != null) {
            String userType = String.valueOf(userBeanSess.getUserType());
            LinkedList<String[]> databaseData;
            LinkedList<String> tableHeaders;
            if(state.equals("confirmed") && userBeanSess != null) {
                // userBean is stored in session after login. The one in
                // Now the correct data can be retrieved from the bean
                UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
                String privilegeType = String.valueOf(userBean.getPrivilegeType());
                // Here we will find if the user is student, super admin, or teacher in that order
                if (!userType.equals("teacher")) {
                    String comingFromSubMenu = req.getParameter("sub");
                    if(comingFromSubMenu!=null && comingFromSubMenu.equals("my-courses")){
                        String student = "studentCourses";
                        // set the context attribute to find next action in myPage
                        req.getSession().setAttribute("caller", student);
                        databaseData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userId);
                        //System.out.println(Arrays.toString(Arrays.stream(databaseData.get(0)).toArray()));
                        tableHeaders = buildTableHeaders("ID", "Course Name", "Points", "Description", "Student name", "Teacher name");
                        req.getSession().setAttribute("coursesData", databaseData);
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null && comingFromSubMenu.equals("my-classmates")){
                        //TODO show all of the students classmates by course
                        String student = "studentClass";
                        // set the context attribute to find next action in myPage
                        req.getSession().setAttribute("caller", student);
                        System.out.println("Students classmates");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null){
                        String student = "error";
                        req.getSession().setAttribute("caller", student);
                        System.out.println("error");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }
                } else if (privilegeType.equals("superadmin")) {
                    String comingFromSubMenu = req.getParameter("sub");
                    if(comingFromSubMenu!=null && comingFromSubMenu.equals("manage-admins")){
                        String teacher = "makeAdmin";
                        // set the context attribute to find next action in myPage
                        req.getSession().setAttribute("caller", teacher);
                        // display form and table with teachers that are user or admins
                        System.out.println("Teacher superadmin make other admins view");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null && comingFromSubMenu.equals("reports")){
                        //TODO show all of the students classmates by course
                        String teacher = "reports";
                        // set the context attribute to find next action in myPage
                        req.getSession().setAttribute("caller", teacher);
                        System.out.println("Teacher superadmin statistics to be displayed");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }  else if(comingFromSubMenu!=null){
                        String student = "error";
                        req.getSession().setAttribute("caller", student);
                        System.out.println("error");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }
                } else {
                    /**
                     Available for both teacher user and teacher admin but not for teacher super admin
                     **/
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
                        /**
                         Available for both teacher user and teacher admin but not for teacher super admin
                         **/
                        String teacher = "teacherAllStudents";
                        req.getSession().setAttribute("caller", teacher);
                        databaseData = DBConnector.getConnector().selectQuery("showStudents");
                        req.getSession().setAttribute("coursesData", databaseData);
                        tableHeaders = buildTableHeaders("ID", "First name", "Last name", "city", "email", "phone");
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("all-courses-of-student")){
                        /**
                            Available for both teacher user and teacher admin but not for teacher super admin
                         **/
                        String teacher = "teacherCoursesForStudent";
                        req.getSession().setAttribute("caller", teacher);
                        // display all courses that a student take. Provide student id or names.
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("all-info-of-course")){
                        /**
                         Available for both teacher user and teacher admin but not for teacher super admin
                         **/
                        String teacher = "teacherInfoOfCourse";
                        req.getSession().setAttribute("caller", teacher);
                        ;
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("teacher-course")){
                        /**
                         Available only for teacher admin
                         **/
                        String teacher = "teacherRegisterTeacherForCourse";
                        req.getSession().setAttribute("caller", teacher);
                        //TODO implement association for teacher into course.
                        // One course can or cannot *DECIDE have more than one teachers.
                        // One course can have NULL teachers
                        System.out.println("Register student in course. Forms and table");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("student-course")){
                        /**
                         Available only for teacher admin
                         **/
                        String teacher = "teacherRegisterStudentInCourse";
                        req.getSession().setAttribute("caller", teacher);
                        //TODO implement association from student into course.
                        // One student cannot be in the same course twice.
                        System.out.println("Register student in course. Forms and table");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("remove")){
                        /**
                         Available only for teacher admin
                         **/
                        String teacher = "remove";
                        req.getSession().setAttribute("caller", teacher);
                        //TODO implemet the deletion of something form the databsae.
                        System.out.println("Admin remove");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null){
                        String student = "error";
                        req.getSession().setAttribute("caller", student);
                        System.out.println("error");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }
                }
            }
        } else {
            // User is not logged in, redirect to login page
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
