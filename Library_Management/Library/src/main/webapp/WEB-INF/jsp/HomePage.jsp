<%--
  Created by IntelliJ IDEA.
  User: SriRamya Nemani
  Date: 16-Jun-24
  Time: 9:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            overflow: hidden;
        }
        .container {
            text-align: center;
        }
        .login-btn {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #5cb85c;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
            border-radius: 5px; /* Added round corners */
            margin: 5px; /* Added some margin */
        }
        .login-btn:hover {
            background-color: #4cae4c;
        }
        .animation {
            font-size: 24px;
            margin-bottom: 20px;
            position: relative;
            animation: float 3s infinite;
        }
        @keyframes float {
            0% { transform: translatey(0px); }
            50% { transform: translatey(-20px); }
            100% { transform: translatey(0px); }
        }
        /* Added styling for "Enter Library as" */
        .enter-library {
            font-size: 18px; /* Increased font size */
            margin-bottom: 10px; /* Added some bottom margin */
        }
    </style>
</head>
<body>
<div class="container">
    <div class="animation">
        📚 Welcome to the Library - A place where you find all books 📚
    </div>
    <!-- "Enter as" as a text line with added styling -->
    <div class="enter-library">See</div>
    <!-- Added two new buttons -->
    <button class="login-btn" onclick="location.href='/login'">Books</button>

</div>
</body>
</html>
