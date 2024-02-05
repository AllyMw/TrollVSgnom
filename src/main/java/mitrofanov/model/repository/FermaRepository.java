package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;
import mitrofanov.model.entity.BadalkaEvent;
import mitrofanov.model.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FermaRepository {

    public LocalDateTime getThisUserTime(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT datelastfarme FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getObject("datelastfarme", LocalDateTime.class);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public void updateUserTime(Long chatId, LocalDateTime hours) {
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

    public void addGoldForUser(Long chatId, Long gold) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "UPDATE player SET gold = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, gold);
            statement.setLong(2, chatId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public Long getGoldForUser(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT * FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            Long gold = resultSet.getLong("gold");

            statement.close();
            connection.close();

            return gold;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public static void setFarmHours(Long chatId, int farmHours) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "UPDATE player SET farmhours = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, farmHours);
            statement.setLong(2, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public static int getFarmHours(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT * FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            int farmHours = resultSet.getInt("farmhours");

            statement.close();
            connection.close();

            return farmHours;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}