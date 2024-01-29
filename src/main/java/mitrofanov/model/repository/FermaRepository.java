package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;
import mitrofanov.model.entity.User;

import java.sql.*;
import java.time.LocalDateTime;

public class FermaRepository {
    @SneakyThrows
    public void saveThisUsersTime(User user, int hours) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "INSERT INTO player (chatid, datelastfarme) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, user.getChatId());
            statement.setObject(2, hours);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при записи даты");
        }
    }
    @SneakyThrows
    public static LocalDateTime getThisUserTime(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT datelastfarme FROM player WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getObject("datelastfarme", LocalDateTime.class);
        }
        return null;
    }
    public static void updateUserTime(Long chatId, LocalDateTime hours) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "UPDATE player SET datelastfarme = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(hours));
            statement.setLong(2, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
    public static void addGoldForUser(String chatId, int gold) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "UPDATE player SET gold = ? WHERE chat_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, gold);
            statement.setString(2, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}