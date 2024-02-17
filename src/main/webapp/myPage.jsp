<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="fragments/header.jsp" %>

<c:if test="${userBean.userType == 'student'}">
    <%@ include file="fragments/navStudent.jsp" %>
</c:if>


<%@ include file="fragments/footer.jsp" %>
