<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="fragments/header.jsp" %>

<c:choose>
    <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'admin'&& userBean.stateType == 'confirmed'}">
        <%@ include file="fragments/teacher/navAdminTeacher.jsp" %>
        <%@ include file="fragments/registerForm.jsp" %>
    </c:when>

    <c:when test="${userBean.privilegeType != 'admin'&& userBean.stateType == 'confirmed'}">
        <h2>Access denied!</h2>
        <p>
            You need the right privilege to access the page.<br><br>
            If this is incorrect contact admin: admin@gritacademy.se<br><br>
            <a href="/mypage">Go to My Page</a>
        </p>
    </c:when>

    <c:otherwise>
        <h2>Access denied!</h2>
        <p>
            You need the right privilege to access the page.<br><br>
            <a href="login.jsp">Go to log in</a>
        </p>
    </c:otherwise>

</c:choose>




<%@ include file="fragments/footer.jsp" %>