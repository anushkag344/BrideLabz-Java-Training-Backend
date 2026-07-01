<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spring Greeting App</title>
</head>
<body>

<div class="container">
    <h1>Spring Greeting</h1>

    <form action="greet" method="POST">
        <label>Your Name</label>
        <input type="text" name="userName" required />
        <button type="submit">Get Greeting</button>
    </form>

</div>

</body>
</html>