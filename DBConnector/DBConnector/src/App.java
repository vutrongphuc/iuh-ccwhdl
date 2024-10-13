
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setFirstName("Mark");
        Teacher t1 = new Teacher();
        t1.setFirstName("Tom");
        Teacher t2 = new Teacher();
        t2.setFirstName("Peter");

        Management m = new Management();
        m.addPerson(t1);
        m.addPerson(t2);
        m.addPerson(s1);
        m.printList();

    }

    // Fetch users from database
    public void fetchUsers() {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "SELECT * FROM Persons";

        try {
            //Tạo đối tượng Statement
            Statement stm = conn.createStatement();

            //Thực thi truy vấn và trả về đối tượng ResultSet
            ResultSet rs = stm.executeQuery(query);

            Management m = new Management();

            //Duyệt kết quả trả về
            while (rs.next()) { //Di chuyển con trỏ xuống bản ghi kế tiếp
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setFirstName(rs.getString("fist_name"));
                s.setLastName(rs.getString("last_name"));
                s.setGender(rs.getBoolean("gender"));

                m.addPerson(s);
            }

            m.printList();

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    // Insert user to database
    public void insertUser(String firstName, String lastName, boolean gender) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "INSERT INTO Persons (first_name, last_name, gender) VALUES (? ,? ,?)";

        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, firstName);
            pstm.setString(2, lastName);
            pstm.setBoolean(3, gender);

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

    // Update user in database
    public void updateUser(int id, String firstName, String lastName, boolean gender) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "UPDATE Persons SET first_name = ?, last_name = ?, gender = ? WHERE id = ?";
        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, firstName);
            pstm.setString(2, lastName);
            pstm.setBoolean(3, gender);
            pstm.setInt(4, id);

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

    // Delete user in database
    public void deleteUser(int id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "DELETE FROM Persons WHERE id = ?";
        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, id);

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

    // Fetch user by id
    public void fetchUserById(int id) {
        ConnectMySQL connectMySQL = new ConnectMySQL();
        Connection conn = connectMySQL.connect();

        String query = "SELECT * FROM Persons WHERE id = ?";

        try {
            //Tạo đối tượng Statement
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, id);

            //Thực thi truy vấn và trả về đối tượng ResultSet
            ResultSet rs = pstm.executeQuery();

            Management m = new Management();

            //Duyệt kết quả trả về
            while (rs.next()) { //Di chuyển con trỏ xuống bản ghi kế tiếp
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setFirstName(rs.getString("fist_name"));
                s.setLastName(rs.getString("last_name"));
                s.setGender(rs.getBoolean("gender"));

                m.addPerson(s);
            }

            m.printList();

            //Đóng kết nối
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}
