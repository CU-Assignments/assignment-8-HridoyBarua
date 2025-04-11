<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee List</title>
<style>
    table { border-collapse: collapse; width: 80%; margin: 20px auto; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
    .form-container { text-align: center; margin: 20px; }
    .error { color: red; text-align: center; }
</style>
</head>
<body>
<div class="form-container">
<h2>Search Employee by ID</h2>
<form action="EmployeeServlet" method="get">
    <input type="hidden" name="action" value="search">
    <label>Employee ID: </label>
    <input type="number" name="id" required>
    <input type="submit" value="Search">
</form>
</div>

<h2 style="text-align: center;">All Employees</h2>
<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>
<p class="error"><%= error %></p>
<%
}
java.util.List<com.example.servlet.Employee> empList = (java.util.List<com.example.servlet.Employee>) request.getAttribute("empList");
if (empList != null && !empList.isEmpty()) {
%>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Department</th>
        <th>Salary</th>
    </tr>
    <% for (com.example.servlet.Employee emp : empList) { %>
    <tr>
        <td><%= emp.getId() %></td>
        <td><%= emp.getName() %></td>
        <td><%= emp.getDepartment() %></td>
        <td><%= emp.getSalary() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p class="error">No employees found.</p>
<% } %>
</body>
</html>
