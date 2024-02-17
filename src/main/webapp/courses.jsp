<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="fragments/header.jsp" %>
<div class="container">
        <h2>Course List</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Points</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${coursesData}" var="course">
                    <tr>
                        <td>${course[0]}</td> <!--  id -->
                        <td>${course[1]}</td> <!-- course name -->
                        <td>${course[2]}</td> <!-- points -->
                        <td>${course[3]}</td> <!-- description -->
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


<%@ include file="fragments/footer.jsp" %>