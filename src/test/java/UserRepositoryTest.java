import mitrofanov.model.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        DBConnection.setConnection(connection); // Предполагается, что есть способ установить мок-соединение
    }

    @Test
    public void testSetGoldByChatId() throws SQLException {
        userRepository.setGoldByChatId(1L, 100L);
        verify(preparedStatement).setLong(1, 100L);
        verify(preparedStatement).setLong(2, 1L);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetGoldByChatId() throws SQLException {
        when(resultSet.getLong("gold")).thenReturn(100L);
        Long gold = userRepository.getGoldByChatId(1L);
        assertEquals(Long.valueOf(100), gold);
        verify(preparedStatement).setLong(1, 1L);
    }

    @Test
    public void testGetNickNameByChatId() throws SQLException {
        when(resultSet.getString("nickname")).thenReturn("TestNick");
        String nickname = userRepository.getNickNameByChatId(1L);
        assertEquals("TestNick", nickname);
        verify(preparedStatement).setLong(1, 1L);
    }
}