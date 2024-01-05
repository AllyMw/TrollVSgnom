package mitrofanov;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";

    private static Connection conn = null;


    public static Connection getConnection() {

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("пизда в конекшоне");
            throw new RuntimeException(e);
        }

        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error: Cannot close the connection!");
                e.printStackTrace();
                System.exit(1);
            }
    }
    public static void closeStatement(Statement stmt) {
        if (stmt != null)
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error: Cannot close the statement!");
                e.printStackTrace();
                System.exit(1);
            }
    }

}