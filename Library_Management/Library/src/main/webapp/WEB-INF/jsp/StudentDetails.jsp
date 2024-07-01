<%--
  Created by IntelliJ IDEA.
  User: SriRamya Nemani
  Date: 18-Jun-24
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar .right {
            float: right;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[readonly] {
            background-color: #e9e9e9;
            cursor: not-allowed;
        }
        .button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            cursor: pointer;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #45a049;
        }
        .error-message {
            display: none; /* Initially hide error message */
            color: #D8000C;
            background-color: #FFBABA;
            border: 1px solid #D8000C;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
        }
        .success-message {
            display: none; /* Initially hide success message */
            color: #4F8A10;
            background-color: #DFF2BF;
            border: 1px solid #4F8A10;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="navbar">
    <a href="/allbooks">All Books</a>
    <a href="/details">My Details</a>
    <a href="/issued">Issued Books</a>
    <a href="/logout" class="right">Logout</a>
</div>
<div class="container">
    <h2>My Details</h2>
    <form action="/updatePassword" method="post" onsubmit="return validatePassword()">
        <div id="success-message" class="success-message">
            <c:if test="${Success != null}">
                <p>${Success}</p>
            </c:if>
        </div>
        <div id="error-message" class="error-message">
            <c:if test="${error != null}">
                <p>${error}</p>
            </c:if>
        </div>
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${user.name}" readonly>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" readonly>
        </div>
        <div class="form-group">
            <label for="password">Present Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div class="form-group">
            <label for="reenterPassword">Re-enter New Password:</label>
            <input type="password" id="reenterPassword" name="reenterPassword" required>
        </div>
        <div id="error" class="error"></div>

        <button type="submit" class="button">Submit</button>
    </form>
</div>
<script>
    function validatePassword() {
        var newPassword = document.getElementById("newPassword").value;
        var reenterPassword = document.getElementById("reenterPassword").value;
        var errorElement = document.getElementById("error-message");

        if (newPassword !== reenterPassword) {
            errorElement.textContent = "New password and re-entered password do not match.";
            errorElement.style.display = 'block'; // Show the error message
            return false;
        } else {
            errorElement.textContent = ""; // Clear the error message
            errorElement.style.display = 'none'; // Hide the error message
            return true;
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        var reenterPasswordField = document.getElementById("reenterPassword");
        reenterPasswordField.addEventListener("change", validatePassword);

        const successMessage = document.getElementById("success-message");
        const errorMessage = document.getElementById("error-message");

        // Automatically hide success and error messages after 7 seconds if they are shown
        if (successMessage.textContent.trim().length > 0) {
            successMessage.style.display = 'block';
            setTimeout(function () {
                successMessage.style.display = 'none';
            }, 7000);
        }

        if (errorMessage.textContent.trim().length > 0) {
            errorMessage.style.display = 'block';
            setTimeout(function () {
                errorMessage.style.display = 'none';
            }, 7000);
        }
    });

</script>
</body>
</html>
