package mitrofanov.model.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {

    private static ConnectionFactory connectionFactory = new DBConnectionFactory();

    public static Connection getConnection() {
        try {
            return connectionFactory.getConnection();
        } catch (SQLException e) {
            System.err.println("Ошибка в соединении");
            throw new RuntimeException(e);
        }
    }

    public static void setConnectionFactory(ConnectionFactory factory) {
        connectionFactory = factory;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error: Cannot close the connection!");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error: Cannot close the statement!");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}