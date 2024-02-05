package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;
import mitrofanov.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistrationRepository {
    public boolean addUser(User user) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "INSERT INTO player (chatid, gold, power, agility, mastery, weight, fightingpower, datelastfarme, datelastattack, datelastguard) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, user.getChatId());
            statement.setLong(2, user.getGold());
            statement.setInt(3, user.getPower());
            statement.setInt(4, user.getAgility());
            statement.setInt(5, user.getMastery());
            statement.setInt(6, user.getWeight());
            statement.setLong(7, user.getFightingPower());
            statement.setObject(8, user.getDateLastFarme());
            statement.setObject(9, user.getDateLastAtack());
            statement.setObject(10, user.getDateLastGuard());

            int rowsInserted = statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SneakyThrows
    public void setNickNamebyChatId(String nickName, Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "UPDATE player SET nickname = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nickName);
            statement.setLong(2, chatId);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public boolean isNicknameExists(String nickname) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM player WHERE nickname = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nickname);

            ResultSet resultSet = statement.executeQuery();

            int count = 0;

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            statement.close();
            connection.close();

            return count > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }


    public void setRaceByChatId(String race, Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "UPDATE player SET race = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, race);
            statement.setLong(2, chatId);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }


    public boolean hasChatId(Long chatId) { // true если пользователь есть
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, chatId);

            ResultSet resultSet = statement.executeQuery();

            int count = 0;

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            return count > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public boolean nicknameIsNull(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT nickname FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            String nickname = resultSet.getString(1);

            if (nickname == null) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }


    public boolean raceIsNull(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT race FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, chatId);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            String nickname = resultSet.getString(1);

            if (nickname == null) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}
