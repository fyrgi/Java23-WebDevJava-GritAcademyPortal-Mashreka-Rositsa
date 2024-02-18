<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="fragments/header.jsp" %>
<!-- here we have to ensure that state and userType hold expected values in order to show the user the expected navbar-->
<c:choose>
    <c:when test="${userBean.userType == 'student' && userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/student/navStudent.jsp" %>
        <p>Other include for student</p>
        <!-- Table for enrolled courses -->
            <table>
                <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Course Name</th>
                        <th>Points</th>
                        <th>Description</th>
                        <th>Student Name</th>
                        <th>Teacher Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${studentCoursesData}">
                        <tr>
                            <td>${course.id}</td>
                            <td>${course.name}</td>
                            <td>${course.points}</td>
                            <td>${course.description}</td>
                            <td>${course.student_name}</td>
                            <td>${course.teacher_name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'user'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navUserTeacher.jsp" %>
        <p>Other include for teacher user</p>
        <c:choose>
            <c:when test="${caller == 'teacher'}">
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller != 'teacher'}">
                <p>Other include for teacher user</p>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'admin'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navAdminTeacher.jsp" %>
        <p>Other include for teacher admin</p>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'superadmin'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navSuperadmin.jsp" %>
        <p>Other include for teacher super admin</p>
    </c:when>
</c:choose>

<%@ include file="fragments/footer.jsp" %>
