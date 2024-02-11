import mitrofanov.model.db.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {

    @Test
    void getConnection() {
        Connection conn = DBConnection.getConnection();

        assertNotNull(conn);

        try {
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            fail("SQLException thrown");
        }

        DBConnection.closeConnection(conn);
    }
}

