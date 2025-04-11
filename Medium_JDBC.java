@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employe_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            if ("search".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    String sql = "SELECT * FROM employees WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        try (ResultSet rs = stmt.executeQuery()) {
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
                        }
                    }
                }
                request.getRequestDispatcher("employeeDetails.jsp").forward(request, response);
            } else {
                List<com.example.servlet.Employee> empList = new ArrayList<>();
                String sql = "SELECT * FROM employees";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        empList.add(new com.example.servlet.Employee(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("department"),
                                rs.getDouble("salary")
                        ));
                    }
                }
                request.setAttribute("empList", empList);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "JDBC Driver not found: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
