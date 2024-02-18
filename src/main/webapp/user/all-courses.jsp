
        <div class="container">
            <h2>Course List</h2>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Points</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${coursesData}" var="course" varStatus="status">
                    <c:if test="${status.index != 0}"> <!-- Skip header row -->
                        <tr>
                            <td>${course[0]}</td> <!--  id -->
                            <td>${course[1]}</td> <!-- course name -->
                            <td>${course[2]}</td> <!-- points -->
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>