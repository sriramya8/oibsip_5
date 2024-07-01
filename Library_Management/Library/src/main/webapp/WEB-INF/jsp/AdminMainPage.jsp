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
    <title>Library</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
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
        }

        .button:hover {
            background-color: #45a049;
        }

        .button.red {
            background-color: #f44336;
        }

        .button.red:hover {
            background-color: #da190b;
        }
    </style>
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
    <h2>Welcome ${user.name}</h2>

    <table>
        <tr>
            <th>Statistics</th>
            <th>Count</th>
        </tr>
        <tr>
            <td>Total number of books</td>
            <td>${num}</td>
        </tr>
        <tr>
            <td>Total number of students</td>
            <td>${nums}</td>
        </tr>
    </table>
</div>

</body>
</html>
