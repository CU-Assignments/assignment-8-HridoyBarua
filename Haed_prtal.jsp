<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Attendance Portal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef2f3;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            width: 400px;
            padding: 30px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            text-align: left;
        }
        input, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 14px;
        }
        button {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            margin-right: 10px;
        }
        button:hover {
            background-color: #218838;
        }
        .button-group {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Enter Attendance Details</h2>
        <form action="../AttendanceServlet" method="post">
            <label for="studentName">Student Name:</label>
            <input type="text" id="studentName" name="studentName" placeholder="Enter student name" required>
            
            <label for="subject">Subject:</label>
            <input type="text" id="subject" name="subject" placeholder="Enter subject" required>
            
            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>
            
            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="">-- Select Status --</option>
                <option value="Present">Present</option>
                <option value="Absent">Absent</option>
            </select>
            
            <div class="button-group">
                <button type="submit">Submit</button>
                <button type="reset" style="background-color: #dc3545;">Reset</button>
            </div>
        </form>
    </div>
</body>
</html>
