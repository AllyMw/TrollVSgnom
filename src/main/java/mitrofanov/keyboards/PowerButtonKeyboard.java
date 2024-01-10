package mitrofanov.keyboards;

import mitrofanov.handlers.TelegramRequestHandler;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class PowerButtonKeyboard {
    public static void powerButtonKeyboard(TrainingService trainingService, HashMap<String, Long> cost, Long chatID, SendMessage sendMessage) throws TelegramApiException {
        TelegramLongPollingBot bot = new TelegramRequestHandler();

        if (trainingService.enoughGoldForTraining((Long) cost.get("power"), chatID)) {
            trainingService.setNewPower(chatID);
            trainingService.decreaseGold(chatID, (Long) cost.get("power"));
            sendMessage.setChatId(chatID);
            sendMessage.setText("Ура прокачали силу");
            bot.execute(sendMessage);
        } else {
            sendMessage.setChatId(chatID);
            sendMessage.setText("Мало золота, иди работать");
            bot.execute(sendMessage);
        }
    }
}
