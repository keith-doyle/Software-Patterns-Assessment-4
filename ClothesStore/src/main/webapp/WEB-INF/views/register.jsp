<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - ClothesStore</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        form { max-width: 500px; }
        input, textarea {
            width: 100%;
            padding: 10px;
            margin: 8px 0 16px;
            box-sizing: border-box;
        }
        button {
            padding: 10px 16px;
            cursor: pointer;
        }
        .error {
            color: red;
            margin-bottom: 16px;
        }
    </style>
</head>
<body>
    <h1>Create Account</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <div class="error"><%= errorMessage %></div>
    <%
        }
    %>

    <form method="post" action="<%= request.getContextPath() %>/register">
        <label>Full Name</label>
        <input type="text" name="fullName" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required minlength="6">

        <label>Address</label>
        <textarea name="address" rows="4"></textarea>

        <label>Payment Method</label>
        <input type="text" name="paymentMethod">

        <button type="submit">Register</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/login">Already have an account? Login</a></p>
</body>
</html>