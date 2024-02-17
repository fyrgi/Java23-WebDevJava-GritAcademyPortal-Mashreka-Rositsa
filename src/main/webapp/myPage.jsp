<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="fragments/header.jsp" %>
<!-- here we have to ensure that state and userType hold expected values in order to show the user the expected navbar-->
<c:choose>
    <c:when test="${userBean.userType == 'student' && userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/student/navStudent.jsp" %>
        <p>Other include for student</p>
    </c:when>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'user'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navUserTeacher.jsp" %>
        <p>Other include for teacher user</p>
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
