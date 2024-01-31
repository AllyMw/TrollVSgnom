package mitrofanov.model.db;



import mitrofanov.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static mitrofanov.Configuration.*;

public class DBConnection {

    private static final String URL =
            DB_URL;
    private static final String USER = DB_USER;
    private static final String PASSWORD = DB_PASSWORD;

    private static Connection conn = null;


    public static Connection getConnection() {

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
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