package mitrofanov.keyboards;

import mitrofanov.handlers.TelegramRequestHandler;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class AgilityButtonKeyboard {
    public static void agilityButtonKeyboard(TrainingService trainingService, HashMap<String, Long> cost, Long chatID, SendMessage sendMessage) throws TelegramApiException {
        TelegramLongPollingBot bot = new TelegramRequestHandler();

        if (trainingService.enoughGoldForTraining((Long) cost.get("agility"), chatID)) {
            trainingService.setNewAgility(chatID);
            trainingService.decreaseGold(chatID, (Long) cost.get("agility"));
            sendMessage.setChatId(chatID);
            sendMessage.setText("Ура прокачали ловкость");
            bot.execute(sendMessage);
        } else {
            sendMessage.setChatId(chatID);
            sendMessage.setText("Мало золота, иди работать");
            bot.execute(sendMessage);
        }
    }
}
