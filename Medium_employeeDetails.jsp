<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Details</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; }
        h2 { text-align: center; color: #333; }
        table {
            border-collapse: collapse;
            width: 50%;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 20px;
        }
        .back {
            text-align: center;
            margin: 20px;
        }
        .back a {
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
        }
        .back a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Employee Details</h2>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p class="error"><%= error %></p>
<%
    }

    com.example.servlet.Employee employee = (com.example.servlet.Employee) request.getAttribute("employee");
    if (employee != null) {
%>
    <table>
        <tr>
            <th>ID</th>
            <td><%= employee.getId() %></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><%= employee.getName() %></td>
        </tr>
        <tr>
            <th>Department</th>
            <td><%= employee.getDepartment() %></td>
        </tr>
        <tr>
            <th>Salary</th>
            <td><%= employee.getSalary() %></td>
        </tr>
    </table>
<%
    } else if (error == null) {
%>
    <p class="error">No employee details available.</p>
<%
    }
%>

<div class="back">
    <a href="EmployeeServlet">Back to Employee List</a>
</div>

</body>
</html>
