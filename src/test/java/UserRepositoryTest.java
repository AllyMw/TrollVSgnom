import mitrofanov.model.db.DBConnection;
import mitrofanov.model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {

        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

 //       when(DBConnection.getConnection()).thenReturn(connection);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("gold")).thenReturn(100L);
    }

    @Test
    public void testSetGoldByChatId() {
        userRepository.setGoldByChatId(12345L, 100L);
    }

    @Test
    public void testGetGoldByChatId() {
        Long gold = userRepository.getGoldByChatId(12345L);
        assertEquals(100L, gold);
    }
}
