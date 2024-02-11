package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;
import mitrofanov.model.entity.BadalkaEvent;
import mitrofanov.model.entity.FermaEvent;
import mitrofanov.model.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    public ArrayList<FermaEvent> getFermaEventsByChatId(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM fermaevent WHERE chatid = ? order by dateevent LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<FermaEvent> fermaEvents = new ArrayList<>();

            while (resultSet.next()) {
                FermaEvent fermaEvent = FermaEvent.builder().build();
                fermaEvent.setChatid(resultSet.getLong("chatid"));
                fermaEvent.setGold(resultSet.getLong("gold"));
                fermaEvent.setDateEvent(resultSet.getObject("dateevent", LocalDateTime.class));
                fermaEvents.add(fermaEvent);
            }

            resultSet.close();
            statement.close();

            return fermaEvents;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }

    }

    public ArrayList<BadalkaEvent> getBadalkaEventsByChatId(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM badalkaevent " +
                    "WHERE chatidwinner = ? or chatidloser = ? " +
                    "order by datebadalkaevent " +
                    "LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, chatId);
            statement.setLong(2, chatId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<BadalkaEvent> badalkaEvents = new ArrayList<>();

            while (resultSet.next()) {
                BadalkaEvent badalkaEvent = BadalkaEvent.builder().build();
                badalkaEvent.setChatIdWinner(resultSet.getLong("chatidwinner"));
                badalkaEvent.setChatIdLoser(resultSet.getLong("chatidloser"));
                badalkaEvent.setNickNameWinner(resultSet.getString("nicknamewinner"));
                badalkaEvent.setNickNameLoser(resultSet.getString("nicknameloser"));
                badalkaEvent.setChangeGold(resultSet.getLong("changegold"));
                badalkaEvent.setDateBadalkaEvent(resultSet.getObject("datebadalkaevent", LocalDateTime.class));
                badalkaEvents.add(badalkaEvent);
            }

            resultSet.close();
            statement.close();

            return badalkaEvents;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public void addNewFermEvent(FermaEvent fermaEvent) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "INSERT INTO fermaevent (chatid, gold, dateevent) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, fermaEvent.getChatid());
            statement.setLong(2, fermaEvent.getGold());
            statement.setTimestamp(3, Timestamp.valueOf(fermaEvent.getDateEvent()));

            statement.executeUpdate();

            // Закрываем соединение с базой данных
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewBadalkaEvent(BadalkaEvent badalkaEvent) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "INSERT INTO badalkaevent (chatidwinner, chatidloser, " +
                    "nicknamewinner, nicknameloser, " +
                    "changegold, datebadalkaevent) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, badalkaEvent.getChatIdWinner());
            statement.setLong(2, badalkaEvent.getChatIdLoser());
            statement.setString(3, badalkaEvent.getNickNameWinner());
            statement.setString(4, badalkaEvent.getNickNameLoser());
            statement.setLong(5, badalkaEvent.getChangeGold());
            statement.setTimestamp(6, Timestamp.valueOf(badalkaEvent.getDateBadalkaEvent()));

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void deleteHistoryByChatId(Long chatId) {
        try {
            Connection connection = DBConnection.getConnection();

            String deleteFermaEventQuery = "DELETE FROM fermaevent WHERE chatid = ?";
            String deleteBadalkaEventQuery = "DELETE FROM badalkaevent WHERE chatidwinner = ? or chatidloser = ?";

            PreparedStatement psFermaEvent = connection.prepareStatement(deleteFermaEventQuery);
            psFermaEvent.setLong(1, chatId);
            psFermaEvent.executeUpdate();

            PreparedStatement psBadalkaEvent = connection.prepareStatement(deleteBadalkaEventQuery);
            psBadalkaEvent.setLong(1, chatId);
            psBadalkaEvent.setLong(2, chatId);
            psBadalkaEvent.executeUpdate();

            psBadalkaEvent.close();
            psFermaEvent.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
