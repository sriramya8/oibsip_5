<%--
  Created by IntelliJ IDEA.
  User: SriRamya Nemani
  Date: 17-Jun-24
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Book</title>
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
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
        }

        .form-group input[type="text"] {
            width: 100%;
            padding: 8px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        .button {
            display: inline-block;
            padding: 8px 20px;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            cursor: pointer;
        }

        .button:hover {
            background-color: #45a049;
        }

        .success-message {
            padding: 10px;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }

        .error-message {
            padding: 10px;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const successMessage = document.getElementById("success-message");
            const errorMessage = document.getElementById("error-message");
            if (successMessage) {
                setTimeout(function () {
                    successMessage.style.display = 'none';
                }, 7000);
            }
            if (errorMessage) {
                setTimeout(function () {
                    errorMessage.style.display = 'none';
                }, 7000);
            }
        });
    </script>
</head>
<body>

<div class="navbar">
    <a href="/allbooks">All Books</a>
    <a href="/details">My Details</a>
    <a href="/addbooks">Add Books</a>
    <a href="/issuerequests">Pending Issue Requests</a>
    <a href="/returnrequests">Pending Return Requests</a>
    <a href="/logout" class="right">Logout</a>
</div>

<div class="container">
    <h2>Add Book</h2>
    <form id="add-book-form" method="post" action="/addbook">
        <c:if test="${success != null}">
            <div id="success-message" class="success-message">
                Book with ISBN ${success} saved successfully.
            </div>
        </c:if>
        <c:if test="${error != null}">
            <div id="error-message" class="error-message">
                    ${error}
            </div>
        </c:if>
        <div class="form-group">
            <label for="isbn">ISBN:</label>
            <input type="text" id="isbn" name="isbn" required>
        </div>
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="author">Author:</label>
            <input type="text" id="author" name="author" required>
        </div>

        <button type="submit" class="button">Add Book</button>
    </form>
</div>

</body>
</html>
