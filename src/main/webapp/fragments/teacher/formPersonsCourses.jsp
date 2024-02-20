<form id="searchBy" class="form-container" action="/mypage" method="post">
    <div class="form-group">
        <label for="fname">First name:</label>
        <input type="text" id="fname" class="form-input" name="fname">
    </div>

    <div class="form-group">
        <label for="lname">Last name:</label>
        <input type="text" id="lname" class="form-input" name="lname">
    </div>

    <div class="form-group">
        <label for="id">ID:</label>
        <input type="text" id="id" class="form-input" name="id">
    </div>

    <div class="form-group">
        <label for="search_for">Student or teacher:</label>
        <select id="search_for" class="form-select" name="search_for">
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select>
    </div>

    <button type="submit" value="personCourseSubmit" name="personCourseSubmit" class="btn btn-primary">Login</button>
</form>
