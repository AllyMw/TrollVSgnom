package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainingRepository {
    DBConnection dbConnection;
    @SneakyThrows
    public int getPower(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT power FROM player WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        int status = 5;
        if (resultSet.next()) {
            status = resultSet.getInt("power");
        }
        statement.close();
        connection.close();
        return status;
    }

    @SneakyThrows
    public int getAgility(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT agility FROM player WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        int status = 5;
        if (resultSet.next()) {
            status = resultSet.getInt("agility");
        }
        statement.close();
        connection.close();
        return status;
    }

    @SneakyThrows
    public int getMastery(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT mastery FROM player WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        int status = 5;
        if (resultSet.next()) {
            status = resultSet.getInt("mastery");
        }
        statement.close();
        connection.close();
        return status;
    }

    @SneakyThrows
    public int getWeight(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT weight FROM player WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        int status = 5;
        if (resultSet.next()) {
            status = resultSet.getInt("weight");
        }
        statement.close();
        connection.close();
        return status;
    }

    @SneakyThrows
    public void setNewPower(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET power = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, getPower(chatId) + 1);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    @SneakyThrows
    public void setNewAgility(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET agility = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, getAgility(chatId) + 1);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    @SneakyThrows
    public void setNewMastery(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET mastery = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, getMastery(chatId) + 1);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    @SneakyThrows
    public void setNewWeight(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET weight = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, getWeight(chatId) + 1);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    @SneakyThrows
    public Long getGoldByChatId(Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT gold FROM player WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();
        Long gold = 0L;
        if (resultSet.next()) {
            gold = resultSet.getLong("gold");
        }
        statement.close();
        connection.close();
        return gold;
}
    @SneakyThrows
    public void decreaseGoldByChatId(Long chatId, Long gold) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET gold = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, getGoldByChatId(chatId) - gold);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }


}
