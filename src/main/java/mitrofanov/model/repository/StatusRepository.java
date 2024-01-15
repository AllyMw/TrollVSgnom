package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;

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
