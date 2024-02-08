package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.entity.User;
import mitrofanov.model.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BadalkaRepository {


    public User getUserByChatId(Long chatIdPlayer1) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, chatIdPlayer1);
            ResultSet resultSet = statement.executeQuery();

            User user = null;

            if (resultSet.next()) {
                Long chatId = resultSet.getLong("chatid");
                String nickname = resultSet.getString("nickname");
                String race = resultSet.getString("race");
                Long gold = resultSet.getLong("gold");
                int power = resultSet.getInt("power");
                int agility = resultSet.getInt("agility");
                int mastery = resultSet.getInt("mastery");
                int weight = resultSet.getInt("weight");
                user = User.builder().chatId(chatId).nickname(nickname).race(race).gold(gold).power(power)
                        .agility(agility).mastery(mastery).weight(weight).build();
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public List<User> getListUserForAttack(Long chatId) {
        try {
            List<User> users = new ArrayList<>();
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM player WHERE race = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            if (getUserByChatId(chatId).getRace().equals("Troll")) {
                statement.setString(1, "Gnom");
            } else {
                statement.setString(1, "Troll");
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = User.builder().build();
                user.setChatId(resultSet.getLong("chatId"));
                user.setNickname(resultSet.getString("nickname"));
                user.setRace(resultSet.getString("race"));
                user.setGold(resultSet.getLong("gold"));
                user.setPower(resultSet.getInt("power"));
                user.setAgility(resultSet.getInt("agility"));
                user.setMastery(resultSet.getInt("mastery"));
                user.setWeight(resultSet.getInt("weight"));
                users.add(user);
            }

            resultSet.close();
            statement.close();

            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public boolean isTimeLessThanCurrentAttack(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT datelastattack FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            LocalDateTime localDateTime = resultSet.getObject("datelastattack", LocalDateTime.class);

            if (localDateTime == null) {
                return true;
            } else {
                return LocalDateTime.now().isAfter(localDateTime);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTimeLastAttack(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "UPDATE player SET datelastattack = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
            statement.setLong(2, chatId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
    public LocalDateTime getTimeLastAttack(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT datelastattack FROM player WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getObject("datelastattack", LocalDateTime.class);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}

