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
                        req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null && comingFromSubMenu.equals("my-classmates")){
                        //TODO show all of the students classmates by course
                        String student = "studentClass";
                        // set the context attribute to find next action in myPage
                        req.getSession().setAttribute("caller", student);
                        databaseData = DBConnector.getConnector().selectQuery("ClassMates", userId);
                        tableHeaders = buildTableHeaders("ID","Student name", "Course Name","ClassMates Name");
                        req.getSession().setAttribute("coursesData", databaseData);
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
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
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("all-courses-of-person")){
                        /**
                            Available for both teacher user and teacher admin but not for teacher super admin
                         **/
                        String teacher = "teacherCoursesOfPerson";
                        req.getSession().setAttribute("caller", teacher);
                        // display all courses that a student take. Provide student id or names.
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    }else if(comingFromSubMenu!=null && comingFromSubMenu.equals("add-course")){
                        /**
                         Available for both teacher user and teacher admin but not for teacher super admin
                         **/
                        String teacher = "teacherAddCourse";
                        req.getSession().setAttribute("caller", teacher);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null && comingFromSubMenu.equals("course-information")){
                        /**
                         Available only for teacher admin
                         **/
                        String teacher = "teacherCourseInformation";
                        req.getSession().setAttribute("caller", teacher);

                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else if(comingFromSubMenu!=null && comingFromSubMenu.equals("teacher-course")){
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
        LinkedList<String[]> databaseData = new LinkedList<>();
        LinkedList<String> tableHeaders = new LinkedList<>();
        //TODO put all this part into an if that checks that the user is coming from a part of the menu that uses this form.
        String sentFromPost = req.getParameter("personCourseSubmit");
        // student and teacher are using the same fields for search but are displaying different results.
        if (sentFromPost.equals("personCourseSubmit")) {
            String id = req.getParameter("id");
            String fname = req.getParameter("fname");
            String lname = req.getParameter("lname");
            String searchFor = req.getParameter("search_for");
            if (searchFor.equals("student")) {
                // the first ifs up to ELSE will handle the allowed cases and will display result based on which query was caled
                if (!id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Student", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showRegistrationsWithId", req.getParameter("fname"), req.getParameter("lname"), req.getParameter("id"));
                } else if (!id.isEmpty() && fname.isEmpty() && lname.isEmpty()) {
                    tableHeaders = buildTableHeaders("ID", "Student", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showRegistrationsIdOnly", req.getParameter("id"));
                } else if (id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Student", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showRegistrationsName", req.getParameter("fname"), req.getParameter("lname"));
                } else {
                    // in here we are only checking the possible errors (one name field was left empty or all fields were left empty)
                    if ((fname.isEmpty() && !lname.isEmpty()) || (!fname.isEmpty() && lname.isEmpty())) {
                        String errorMsg = "Both names should be filled";
                        if (!errorMsg.isEmpty()) {
                            //out.println("<p class=error>"+errorMsg+"</p>");
                            System.out.println(errorMsg);
                            errorMsg = "";
                        }
                    } else if (fname.isEmpty() && lname.isEmpty() && id.isEmpty()) {
                        System.out.println("all fields are empty");
                    }
                    // at the end we will set the result data into the lists
                    tableHeaders = buildTableHeaders("ID", "First name", "Last name", "City", "Email");
                    databaseData = DBConnector.getConnector().selectQuery("showStudents");
                }
            } else {
                if (!id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Teacher", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showCoursesForTeacherWithId", req.getParameter("fname"), req.getParameter("lname"), req.getParameter("id"));
                } else if (!id.isEmpty() && fname.isEmpty() && lname.isEmpty()) {
                    tableHeaders = buildTableHeaders("ID", "Teacher", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showCoursesForTeacherIdOnly", req.getParameter("id"));
                } else if (id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Teacher", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showCoursesForTeacherName", req.getParameter("fname"), req.getParameter("lname"));
                } else {
                    if ((fname.isEmpty() && !lname.isEmpty()) || (!fname.isEmpty() && lname.isEmpty())) {
                        String errorMsg = "Both names should be filled";
                        if (!errorMsg.isEmpty()) {
                            //out.println("<p class=error>"+errorMsg+"</p>");
                            System.out.println(errorMsg);
                            errorMsg = "";
                        }
                    } else if (fname.isEmpty() && lname.isEmpty() && id.isEmpty()) {
                        System.out.println("all fields are empty");
                    }
                    tableHeaders = buildTableHeaders("ID", "First name", "Last name", "City", "Email");
                    databaseData = DBConnector.getConnector().selectQuery("showTeachers");
                }

            }
            // after we have set the data either for teacher or for student we have to send it via session.
            //answerRequest
            String teacher = "answerRequest";
            req.getSession().setAttribute("caller", teacher);
            req.getSession().setAttribute("coursesData", databaseData);
            req.getSession().setAttribute("tableHeaders", tableHeaders);
            req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
        } else if (sentFromPost.equals("courseInfoSubmit")) {
            String id = req.getParameter("id");
            if (id.isEmpty()) {
                id = "0";
            }
            String name = req.getParameter("name").trim();
            if (!name.trim().isEmpty()) {
                name = "%" + name + "%";
            }
            String teacher = "answerRequest";
            req.getSession().setAttribute("caller", teacher);
            tableHeaders = buildTableHeaders("ID", "Course name", "Points", "Teachers", "Students");
            databaseData = DBConnector.getConnector().selectQuery("showCourseInformation", id, name);
            req.getSession().setAttribute("coursesData", databaseData);
            req.getSession().setAttribute("tableHeaders", tableHeaders);
            req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
        } else {
            System.out.println("Unknown psot");
        }
        // Adding a course by teacher admin
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userType") != null && session.getAttribute("privilegeType") != null) {
            String userType = (String) session.getAttribute("userType");
            String privilegeType = (String) session.getAttribute("privilegeType");

            if (userType.equals("teacher") && privilegeType.equals("admin")) {
                String courseName = req.getParameter("courseName");
                String pointsStr = req.getParameter("points");
                String description = req.getParameter("description");

                if (courseName.trim().isEmpty() || pointsStr.trim().isEmpty()) {
                    System.out.println("Course name and points cannot be empty!");
                    return;
                }

                try {
                    int points = Integer.parseInt(pointsStr);
                    DBConnector.getConnector().insertQuery("addNewCourse", courseName, String.valueOf(points), description, "S", "I", "S");
                    System.out.println("Course added successfully");
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse points as integer");
                    e.printStackTrace();
                }
            }
        }
    }


        protected LinkedList<String> buildTableHeaders(String...args){
        LinkedList<String> tableHeaders = new LinkedList<>();
        tableHeaders.addAll(Arrays.asList(args));
        return tableHeaders;
    }
}
