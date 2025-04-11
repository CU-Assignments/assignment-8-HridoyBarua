package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/employe_db"; // Changed from employee_db to employee-db
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = ""; // Ensure this matches your setup (empty if no password)

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection conn = null;
        try {
            // Load and register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");

            // Establish connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection established successfully.");

            if ("search".equals(action)) {
                String idStr = request.getParameter("id");
                int id = Integer.parseInt(idStr);
                String sql = "SELECT * FROM employees WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                com.example.servlet.Employee emp = null;
                if (rs.next()) {
                    emp = new com.example.servlet.Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("department"),
                            rs.getDouble("salary")
                    );
                    request.setAttribute("employee", emp);
                } else {
                    request.setAttribute("error", "No employee found with ID: " + id);
                }
                rs.close();
                stmt.close();
            } else {
                List<com.example.servlet.Employee> empList = new ArrayList<>();
                String sql = "SELECT * FROM employees";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    empList.add(new com.example.servlet.Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("department"),
                            rs.getDouble("salary")
                    ));
                }
                request.setAttribute("empList", empList);
                rs.close();
                stmt.close();
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "JDBC Driver not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }

        if ("search".equals(action)) {
            request.getRequestDispatcher("employeeDetails.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
