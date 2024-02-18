<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="fragments/header.jsp" %>

<c:set var="viewTitle" value="All Courses" scope="request" />
<%@ include file="fragments/tableView.jsp" %>


<%@ include file="fragments/footer.jsp" %>