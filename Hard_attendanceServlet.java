package com.portal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Add your DB password if set

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        String studentName = request.getParameter("studentName");
        String subject = request.getParameter("subject");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        // Basic validation
        if (studentName == null || subject == null || date == null || status == null ||
            studentName.isEmpty() || subject.isEmpty() || date.isEmpty() || status.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            return;
        }

        // Database interaction
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO attendance (student_name, subject, date, status) VALUES (?, ?, ?, ?)")) {

            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Set parameters
            stmt.setString(1, studentName);
            stmt.setString(2, subject);
            stmt.setString(3, date);
            stmt.setString(4, status);

            // Execute insert
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("jsp/success.jsp");
            } else {
                request.setAttribute("error", "Failed to record attendance.");
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "JDBC Driver not found.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }
}
