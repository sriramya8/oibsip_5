<%--
  Created by IntelliJ IDEA.
  User: SriRamya Nemani
  Date: 17-Jun-24
  Time: 11:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background: #f3f4f6;
        }
        .login-container {
            background: #fff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        .login-container h2 {
            margin-bottom: 1.5rem;
            color: #333;
        }
        .login-container input[type="email"],
        .login-container input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }
        .login-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 1rem;
            background: #5cb85c;
            border: none;
            border-radius: 4px;
            color: #fff;
            font-size: 18px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .login-container input[type="submit"]:hover {
            background: #4cae4c;
        }
        .login-container .links {
            margin-top: 1rem;
            text-align: center;
        }
        .login-container .links a {
            color: #5cb85c;
            text-decoration: none;
            transition: color 0.3s;
        }
        .login-container .links a:hover {
            color: #4cae4c;
        }
        .success-message {
            display: none;
            padding: 10px;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }

    </style>
    <script>
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
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form action="/authenticate" method="post">
        <c:if test="${Success != null}">
            <div id="success-message" class="success-message">
                <p> ${Success} </p>
            </div>
        </c:if>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="submit" value="Login">
    </form>
    <div class="links">
        <span>Don't have an account? <a href="/register/myuser">Signup</a></span>
    </div>
</div>
</body>
</html>

