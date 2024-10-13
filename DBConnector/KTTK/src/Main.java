import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Management mng = new Management();

        mng.addDepartment(new Department("D002", "IT"));

        // mng.searchDepartmentById("D001");

        // mng.updateDepartment(new Department("D001", "Information Technology"));
    }

    /************** Employee ***************/

    // Fetch Employees from database
    public void fetchEmployees() {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "SELECT * FROM Employee e RIGHT JOIN Department d ON d.department_id = e.department_id";

        try {
            // Tạo đối tượng Statement
            Statement stm = conn.createStatement();

            // Thực thi truy vấn và trả về đối tượng ResultSet
            ResultSet rs = stm.executeQuery(query);

            // Duyệt kết quả trả về
            while (rs.next()) { // Di chuyển con trỏ xuống bản ghi kế tiếp
                System.out.println("Employee: " +
                        rs.getString("e.employee_id") + " " +
                        rs.getString("e.name") + " " +
                        (rs.getBoolean("e.sex") ? "Male" : "Female") + " " +
                        rs.getString("e.email") + " " +
                        rs.getString("e.phone") + " " +
                        rs.getString("e.address") + " " +
                        rs.getString("d.department_name"));
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Insert Employee to database
    public void insertEmployee(String employee_id, String name, boolean sex, String email, String phone, String address, String department_id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "INSERT INTO Employee (employee_id, name, sex, email, address, phone, department_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, employee_id);
            pstm.setString(2, name);
            pstm.setBoolean(3, sex);
            pstm.setString(4, email);
            pstm.setString(5, phone);
            pstm.setString(6, address);
            pstm.setString(7, department_id);

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int rows = pstm.executeUpdate(query);

            if (rows > 0) {
                System.out.println("A row has been inserted.");
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Update Employee in database
    public void updateEmployee(String employee_id, String name, boolean sex, String email, String phone, String address, String department_id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "UPDATE Employee SET name = ?, sex = ?, email = ?, phone = ?, address = ?, department_id = ? WHERE employee_id = ?";
        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setBoolean(2, sex);
            pstm.setString(3, email);
            pstm.setString(4, phone);
            pstm.setString(5, address);
            pstm.setString(6, department_id);
            pstm.setString(7, employee_id);

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int rows = pstm.executeUpdate(query);

            if (rows > 0) {
                System.out.println("A row has been inserted.");
            }

            //Đóng kết nối
            conn.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Delete Employee in database
    public void deleteEmployee(String employee_id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "DELETE FROM Employee WHERE employee_id = ?";
        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, employee_id);

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int rows = pstm.executeUpdate(query);

            if (rows > 0) {
                System.out.println("A row has been inserted.");
            }

            //Đóng kết nối
            conn.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Fetch Employee by department id
    public void fetchEmployeeByDepartmentId(String department_id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "SELECT * FROM Department d LEFT JOIN Employee e ON e.department_id = d.department_id WHERE d.department_id = ?";

        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, department_id);

            //Thực thi truy vấn và trả về đối tượng ResultSet
            ResultSet rs = pstm.executeQuery();

            //Duyệt kết quả trả về
            while (rs.next()) { // Di chuyển con trỏ xuống bản ghi kế tiếp
                System.out.println("Employee: " +
                        rs.getString("employee_id") + " " +
                        rs.getString("name") + " " +
                        (rs.getBoolean("sex") ? "Male" : "Female") + " " +
                        rs.getString("email") + " " +
                        rs.getString("phone") + " " +
                        rs.getString("address") + " " +
                        rs.getString("department_name"));
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Fetch Employee by department name
    public void fetchEmployeeByDepartmentName(String department_name) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "SELECT * FROM Department d LEFT JOIN Employee e ON e.department_id = d.department_id WHERE d.department_name like ?";

        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, "%" + department_name + "%");

            //Thực thi truy vấn và trả về đối tượng ResultSet
            ResultSet rs = pstm.executeQuery();

            //Duyệt kết quả trả về
            while (rs.next()) { // Di chuyển con trỏ xuống bản ghi kế tiếp
                System.out.println("Employee: " +
                        rs.getString("employee_id") + " " +
                        rs.getString("name") + " " +
                        (rs.getBoolean("sex") ? "Male" : "Female") + " " +
                        rs.getString("email") + " " +
                        rs.getString("phone") + " " +
                        rs.getString("address") + " " +
                        rs.getString("department_name"));
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    /************** Department ***************/

    // Fetch Departments from database
    public void fetchDepartments() {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "SELECT * FROM department";

        try {
            // Tạo đối tượng Statement
            Statement stm = conn.createStatement();

            // Thực thi truy vấn và trả về đối tượng ResultSet
            ResultSet rs = stm.executeQuery(query);

            // Duyệt kết quả trả về
            while (rs.next()) { //Di chuyển con trỏ xuống bản ghi kế tiếp
                System.out.println("Department: " +
                        rs.getString("department_id") + " " +
                        rs.getString("department_name"));
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Insert Department to database
    public void insertDepartment(String department_id, String department_name) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "INSERT INTO department (department_id, department_name) VALUES (?, ?)";

        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, department_id);
            pstm.setString(2, department_name);

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int rows = pstm.executeUpdate(query);

            if (rows > 0) {
                System.out.println("A row has been inserted.");
            }

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Update Department in database
    public void updateDepartment(String department_id, String department_name) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "UPDATE department SET department_name = ? WHERE department_id = ?";
        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, department_id);
            pstm.setString(2, department_name);

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int rows = pstm.executeUpdate(query);

            if (rows > 0) {
                System.out.println("A row has been inserted.");
            }

            //Đóng kết nối
            conn.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Delete Department in database
    public void deleteDepartment(String department_id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "DELETE FROM department WHERE department_id = ?";
        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, department_id);

            //Khi thực hiện các lệnh insert/update/delete sử dụng executeUpdate(), nó sẽ trả về số hàng bị tác động
            int rows = pstm.executeUpdate(query);

            if (rows > 0) {
                System.out.println("A row has been inserted.");
            }

            //Đóng kết nối
            conn.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}