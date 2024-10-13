
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySQL {

    // String URL = "jdbc:mysql://hostname:port/dbname";
    private static final String URL = "jdbc:mysql://localhost:3306/hrm";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Connection connect() {
        //Tạo đối tượng Connection
        Connection conn = null;

        // 1. Load Driver
        // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error: unable to load driver class!");
        }

        try {
            // 2. Open connection
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println("Connect successfully!");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        // 5. Close connection: will be auto closed by try-with-resource
        // conn.close();
        return conn;
    }
}
