<div>
<form action=/register-student method=POST>
    <select name=idCourse>
    <c:choose>
        <c:when test="${fn:length(availableCourses)==1}">
            <option disabled value="">No data available.</option>
        </c:when>
        <c:otherwise>
            <c:forEach items="${availableCourses}" var="row" varStatus="loopRow">
                <c:if test="${loopRow.index != 0}">
                    <c:forEach items="4" var="column" varStatus="loop">
                        <option value="${row[0]}">${row[1]}</option>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </select>
    <input class="reset" type=submit value=Register>
</form>
</div>