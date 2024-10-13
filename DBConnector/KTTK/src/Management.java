import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Management implements Interface {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Management() {
        conn = new ConnectMySQL().connect();
    }

    // Add department
    @Override
    public void addDepartment(Department dep) {
        try {
            String sql = "INSERT INTO department VALUES(?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dep.getDepartmentId());
            ps.setString(2, dep.getDepartmentName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Update department
    @Override
    public void updateDepartment(Department dep) {
        try {
            String sql = "UPDATE department SET department_name = ? WHERE department_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dep.getDepartmentName());
            ps.setString(2, dep.getDepartmentId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Delete department from database
    @Override
    public void deleteDepartment(Department dep) {
        try {
            String sql = "DELETE FROM department WHERE department_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dep.getDepartmentId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }


    // Search department by id
    @Override
    public void searchDepartmentById(String department_id) {
        try {
            String sql = "SELECT * FROM department WHERE department_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department_id);
            rs = ps.executeQuery();

            // Store the result in a list
            List<Department> dep = new ArrayList<>();
            while (rs.next()) {
                dep.add(new Department(rs.getString("department_id"), rs.getString("department_name")));
            }

            System.out.println(dep.toString());

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Search department by name
    @Override
    public void searchDepartmentByName(String department_name) {
        try {
            String sql = "SELECT * FROM department WHERE department_name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department_name);
            rs = ps.executeQuery();

            // Store the result in a list
            List<Department> dep = new ArrayList<>();
            while (rs.next()) {
                dep.add(new Department(rs.getString("department_id"), rs.getString("department_name")));
            }

            System.out.println(dep.toString());

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Add employee
    @Override
    public void addEmployee(Employee emp) {
        try {
            String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getEmployeeId());
            ps.setString(2, emp.getName());
            ps.setBoolean(3, emp.getSex());
            ps.setString(4, emp.getEmail());
            ps.setString(5, emp.getPhone());
            ps.setString(6, emp.getAddress());
            ps.setString(7, emp.getDepartmentId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Delete employee
    @Override
    public void deleteEmployee(String employee_id) {
        try {
            String sql = "DELETE FROM employee WHERE employee_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee_id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Update department
    @Override
    public void updateEmployee(Employee emp) {
        try {
            String sql = "UPDATE Employee SET name = ?, sex = ?, email = ?, phone = ?, address = ?, department_id = ? WHERE employee_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getName());
            ps.setBoolean(2, emp.getSex());
            ps.setString(3, emp.getEmail());
            ps.setString(4, emp.getPhone());
            ps.setString(5, emp.getAddress());
            ps.setString(6, emp.getDepartmentId());
            ps.setString(7, emp.getEmployeeId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }


    // Search employee by id
    @Override
    public void searchEmployeeById(String employee_id) {
        try {
            String sql = "SELECT * FROM Department d LEFT JOIN Employee e ON e.department_id = d.department_id WHERE d.department_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee_id);
            rs = ps.executeQuery();

            // Store the result in a list
            List<Employee> emp = new ArrayList<>();
            while (rs.next()) {
                emp.add(new Employee(rs.getString("employee_id"), rs.getString("name"), rs.getBoolean("sex"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getString("department_id"), rs.getString("department_name")));
            }

            System.out.println(emp.toString());

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Search employee by name
    @Override
    public void searchEmployeeByName(String name) {
        try {
            String sql = "SELECT * FROM Department d LEFT JOIN Employee e ON e.department_id = d.department_id WHERE d.department_name like ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");

            rs = ps.executeQuery();

            // Store the result in a list
            List<Employee> emp = new ArrayList<>();
            while (rs.next()) {
                emp.add(new Employee(rs.getString("employee_id"), rs.getString("name"), rs.getBoolean("sex"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getString("department_id"), rs.getString("department_name")));
            }

            System.out.println(emp.toString());
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}
