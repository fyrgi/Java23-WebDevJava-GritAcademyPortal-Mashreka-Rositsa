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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
        String state = (String) getServletContext().getAttribute("userState");

        if (state != null && state.equals("confirmed") && userBean != null) {
            String userType = String.valueOf(userBean.getUserType());
            LinkedList<String[]> databaseData;
            LinkedList<String> tableHeaders;
            System.out.println("User Type: " + userType);

            switch (userType) {
                case "student":
                    String userId = userBean.getId();
                    String student = "studentCourses";
                    // set the context attribute to find next action in myPage
                    request.getSession().setAttribute("caller", student);
                    databaseData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userId);
                    //System.out.println(Arrays.toString(Arrays.stream(databaseData.get(0)).toArray()));
                    tableHeaders = buildTableHeaders("ID", "Course Name", "Points", "Description", "Student name", "Teacher name");
                    getServletContext().setAttribute("coursesData", databaseData);
                    getServletContext().setAttribute("tableHeaders", tableHeaders);
                    request.getRequestDispatcher("myPage.jsp").forward(request, response);
                    break;
                case "teacher":
                    // Teacher-specific logic
                    // TODO: Implement teacher-specific logic
                    break;
                case "superadmin":
                    // Superadmin-specific logic
                    // TODO: Implement superadmin-specific logic
                    break;
                default:
                    // Handle unknown user types
                    // TODO: Implement logic for unknown user types
                    break;
            }
        } else {
            // User is not logged in, redirect to login page
            System.out.println("User not confirmed, redirecting to login page");
            response.sendRedirect("login.jsp");
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
