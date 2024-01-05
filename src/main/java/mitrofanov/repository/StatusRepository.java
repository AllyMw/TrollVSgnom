package mitrofanov.repository;

import lombok.SneakyThrows;
import mitrofanov.DBConnection;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusRepository {
    DBConnection dbConnection;
    public int getStatusByChatId(Long chatId) {
        Integer status = null;
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT statuscode FROM status WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getInt("statuscode");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    public void addNewChatID(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "INSERT INTO status (chatid, statuscode) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatId);
            statement.setInt(2, 1);
            statement.executeQuery();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public boolean hasChatId(Long chatId) { // true если пользователь есть

            Connection connection = DBConnection.getConnection();
            String query = "SELECT COUNT(*) FROM status WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            if(resultSet.next()){
                 count = resultSet.getInt(1);
            }
            return count > 0;
    }
    @SneakyThrows
    public void insertStatusCode(Long chatId, int stCode) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE status SET statuscode = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, stCode);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
