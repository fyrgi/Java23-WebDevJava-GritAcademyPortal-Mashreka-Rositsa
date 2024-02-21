<div class="form-container">
        <h2>Add Course Form</h2>
        <form action="/mypage?sub=add-course" method="post">

            <div class="form-group">
                <label for="courseName">Course Name:</label>
                <input type="text" id="courseName" name="courseName" class="form-input" required>
            </div>

            <div class="form-group">
                <label for="points">Points:</label>
                <input type="number" id="points" name="points" class="form-input" required>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" class="form-input" ></textarea>
            </div>

            <button type="submit" class="btn">Add Course</button>
        </form>
    </div>