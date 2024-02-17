<form id="loginForm" class="form-container" action="/login" method="post">
    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" class="form-input" name="username" required value="bob">
    </div>

    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" class="form-input" name="password" required value="zob">
    </div>

    <div class="form-group">
        <label for="user_type">Choose a user type:</label>
        <select id="user_type" class="form-select" name="user_type">
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select>
    </div>

    <button type="submit" class="btn btn-primary">Login</button>
</form>
