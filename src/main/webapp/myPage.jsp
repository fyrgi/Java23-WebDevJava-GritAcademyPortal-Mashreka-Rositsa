<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="fragments/header.jsp" %>
<!-- here we have to ensure that state and userType hold expected values in order to show the user the expected navbar-->
<c:choose>
    <c:when test="${userBean.userType == 'student' && userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/student/navStudent.jsp" %>
        <c:choose>
            <c:when test="${caller == 'studentCourses'}">
                <c:set var="viewTitle" value="All Courses" scope="request" />
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller == 'studentClass'}">
                <c:set var="viewTitle" value="Class-mates" scope="request" />
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller == 'error'}">
                <%@ include file="fragments/404.jsp" %>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'user'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navUserTeacher.jsp" %>
        <p>Other include for teacher user</p>
        <c:choose>
            <c:when test="${caller == 'teacherAllCourses'}">
                <c:set var="viewTitle" value="All Courses" scope="request" />
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller == 'teacherAllStudents'}">
                <c:set var="viewTitle" value="All Students" scope="request" />
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller == 'teacherCoursesOfPerson'}">
                <%@ include file="fragments/teacher/formPersonsCourses.jsp" %>
            </c:when>
            <c:when test="${caller == 'error'}">
                <%@ include file="fragments/404.jsp" %>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'admin'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navAdminTeacher.jsp" %>
        <p>Other include for teacher admin</p>
        <c:choose>
            <c:when test="${caller == 'teacherAllCourses'}">
                <c:set var="viewTitle" value="All Courses" scope="request" />
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller == 'teacherAllStudents'}">
                <c:set var="viewTitle" value="All Students" scope="request" />
                <%@ include file="fragments/tableView.jsp" %>
            </c:when>
            <c:when test="${caller == 'teacherCoursesOfPerson'}">
                <%@ include file="fragments/teacher/formPersonsCourses.jsp" %>
            </c:when>
            <c:when test="${caller == 'teacherAddCourse'}">
                <p>To be implemented Dropdowns and Table</p>
            </c:when>
            <c:when test="${caller == 'teacherRegisterTeacherForCourse'}">
                <p>To be implemented Dropdowns and Table</p>
            </c:when>
            <c:when test="${caller == 'teacherRegisterStudentInCourse'}">
                <p>To be implemented Dropdowns and Table</p>
            </c:when>
            <c:when test="${caller == 'remove'}">
                <p>To be implemented Depends on what we will remove</p>
            </c:when>
            <c:when test="${caller == 'error'}">
                <%@ include file="fragments/404.jsp" %>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'superadmin' && userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navSuperadmin.jsp" %>
        <p>Other include for teacher super admin</p>
        <c:choose>
            <c:when test="${caller == 'makeAdmin'}">
                <p>To be implemented! Could show a form and a table.<br>The form can be to add id and to specify if we want to make admin or user wit ha radio</p>
            </c:when>
            <c:when test="${caller == 'reports'}">
                <p>To be implemented! Could show buttons which upon click will display a table with statistics</p>
            </c:when>
            <c:when test="${caller == 'error'}">
                <%@ include file="fragments/404.jsp" %>
            </c:when>
        </c:choose>
    </c:when>
<c:otherwise>
    <%@ include file="fragments/404.jsp" %>
</c:otherwise>
</c:choose>

<c:if test="${caller == 'answerRequest'}">
    <c:set var="viewTitle" value="All courses per person" scope="request" />
    <%@ include file="fragments/tableView.jsp" %>
</c:if>

<%@ include file="fragments/footer.jsp" %>
