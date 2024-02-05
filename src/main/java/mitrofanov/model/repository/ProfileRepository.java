package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;
import mitrofanov.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepository {

    public User getUserProfile(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "SELECT * FROM player WHERE chatid = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, chatId);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            chatId = resultSet.getLong("chatId");
            String nickname = resultSet.getString("nickname");
            String race = resultSet.getString("race");
            Long gold = resultSet.getLong("gold");
            int power = resultSet.getInt("power");
            int agility = resultSet.getInt("agility");
            int mastery = resultSet.getInt("mastery");
            int weight = resultSet.getInt("weight");
            Long fightingPower = resultSet.getLong("fightingPower");

            User user = User.builder().chatId(chatId).nickname(nickname).agility(agility).race(race)
                    .gold(gold).mastery(mastery).power(power).weight(weight).fightingPower(fightingPower).build();

            return user;
        } catch (
                SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}
