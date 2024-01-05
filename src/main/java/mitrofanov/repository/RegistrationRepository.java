package mitrofanov.repository;

import lombok.SneakyThrows;
import mitrofanov.DBConnection;
import mitrofanov.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationRepository {
    DBConnection dbConnection;
    public boolean addUser(User user) {
        try {
            Connection connection = DBConnection.getConnection();

            // Готовим запрос для вставки нового пользователя
            String query = "INSERT INTO player (chatid, gold, power, agility, mastery, weight, fightingpower) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getChatId());
            statement.setLong(2, user.getGold());
            statement.setInt(3, user.getPower());
            statement.setInt(4, user.getAgility());
            statement.setInt(5, user.getMastery());
            statement.setInt(6, user.getWeight());
            statement.setLong(7, user.getFightingPower());

            // Выполняем запрос
            int rowsInserted = statement.executeUpdate();

            // Закрываем соединение с базой данных
            statement.close();
            connection.close();

            // Если запрос выполнен успешно, возвращаем true
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SneakyThrows
    public void setNickNamebyChatId(String nickName, Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET nickname = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nickName);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @SneakyThrows
    public void setRaceByChatId(String race, Long chatId) {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE player SET race = ? WHERE chatid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, race);
        statement.setLong(2, chatId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}