package mitrofanov.keyboards;

import mitrofanov.handlers.TelegramRequestHandler;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class WeightButtonKeyboard {
    public static void weightButtonKeyboard(TrainingService trainingService, HashMap<String, Long> cost, Long chatID, SendMessage sendMessage) throws TelegramApiException {
        TelegramLongPollingBot bot = new TelegramRequestHandler();

        if (trainingService.enoughGoldForTraining((Long) cost.get("weight"), chatID)) {
            trainingService.setNewWeight(chatID);
            trainingService.decreaseGold(chatID, (Long) cost.get("weight"));
            sendMessage.setChatId(chatID);
            sendMessage.setText("Ура прокачали массу");
            bot.execute(sendMessage);
        } else {
            sendMessage.setChatId(chatID);
            sendMessage.setText("Мало золота, иди работать");
            bot.execute(sendMessage);
        }
    }
}
