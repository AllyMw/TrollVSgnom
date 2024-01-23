package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {
    @SneakyThrows
    public void setGoldByChatId(Long chatId, Long gold) {
            Connection connection = DBConnection.getConnection();
            String sql = "UPDATE player SET gold = ? WHERE chatid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, gold);
            statement.setLong(2, chatId);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        @SneakyThrows
        public Long getGoldByChatId(Long chatId) {
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
        }
    }

