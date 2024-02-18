<div class="container">
  <h2>Course List</h2>
  <table class="table">
    <thead>
      <tr>
        <c:forEach items="${tableHeaders}" var="column">
            <th>${column}</th>
        </c:forEach>
      </tr>
    </thead>
    <tbody>
    <c:choose>
      <c:when test="${empty coursesData}">
        <tr>
          <td colspan="<c:out value="${fn:length(headers)}"/>"No data available.</td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach items="${coursesData}" var="row" >
          <tr>
            <c:forEach items="${tableHeaders}" var="column" varStatus="loop">
              <td>${row[loop.index]}</td>
            </c:forEach>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
    </tbody>
  </table>
</div>