package mitrofanov.model.repository;

import lombok.SneakyThrows;
import mitrofanov.model.db.DBConnection;
import mitrofanov.model.entity.BadalkaEvent;
import mitrofanov.model.entity.FermaEvent;
import mitrofanov.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    @SneakyThrows
    public ArrayList<FermaEvent> getFermaEventsByChatId(Long chatId) {
        ArrayList<FermaEvent> fermaEvents = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM fermaevent WHERE chatid = ? order by dateevent LIMIT 10";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            FermaEvent fermaEvent = FermaEvent.builder().build();
            fermaEvent.setChatid(resultSet.getLong("chatid"));
            fermaEvent.setGold(resultSet.getLong("gold"));
            fermaEvent.setDateEvent(resultSet.getDate("dateevent").toLocalDate());
            fermaEvents.add(fermaEvent);
        }

        // Закрытие ресурсов
        resultSet.close();
        statement.close();

        return fermaEvents;
    }

    @SneakyThrows
    public ArrayList<BadalkaEvent> getBadalkaEventsByChatId(Long chatId) {
        ArrayList<BadalkaEvent> badalkaEvents = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM badalkaevent " +
                "WHERE chatidwinner = ? or chatidloser = ? " +
                "order by datebadalkaevent " +
                "LIMIT 10";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, chatId);
        statement.setLong(2, chatId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            BadalkaEvent badalkaEvent = BadalkaEvent.builder().build();
            badalkaEvent.setChatIdWinner(resultSet.getLong("chatidwinner"));
            badalkaEvent.setChatIdLoser(resultSet.getLong("chatidloser"));
            badalkaEvent.setNickNameWinner(resultSet.getString("nicknamewinner"));
            badalkaEvent.setNickNameLoser(resultSet.getString("nicknameloser"));
            badalkaEvent.setChangeGold(resultSet.getLong("changegold"));
            badalkaEvent.setDateBadalkaEvent(resultSet.getDate("datebadalkaevent").toLocalDate());
            badalkaEvents.add(badalkaEvent);
        }

        // Закрытие ресурсов
        resultSet.close();
        statement.close();

        return badalkaEvents;
    }

   public void addNewFermEvent(FermaEvent fermaEvent) {
       try {
           Connection connection = DBConnection.getConnection();

           String query = "INSERT INTO fermaevent (chatid, gold, dateevent) " +
                   "VALUES (?, ?, ?)";
           PreparedStatement statement = connection.prepareStatement(query);
           statement.setLong(1, fermaEvent.getChatid());
           statement.setLong(2, fermaEvent.getGold());
           statement.setTimestamp(3,  Timestamp.valueOf(fermaEvent.getDateEvent().toString()));

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
            statement.setTimestamp(6,  Timestamp.valueOf(badalkaEvent.getDateBadalkaEvent().toString()));

            statement.executeUpdate();

            // Закрываем соединение с базой данных
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
