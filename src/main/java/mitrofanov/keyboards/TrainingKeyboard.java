package mitrofanov.keyboards;

import mitrofanov.handlers.TelegramRequestHandler;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingKeyboard {
    public  void trainingKeyboard(TelegramLongPollingBot bot, Long chatId) throws TelegramApiException {

        TrainingService trainingService = new TrainingService();
        HashMap<String, Long> cost = trainingService.countCost(Long.valueOf(chatId));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Какую характеристику хотите прокачать?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        var powerButton = new InlineKeyboardButton();;
        powerButton.setText("Cила - " + cost.get("power").toString() + " золота");
        powerButton.setCallbackData("/powerTraining");

        var agilityButton = new InlineKeyboardButton();
        agilityButton.setText("Ловкость - " + cost.get("agility").toString() + " золота");
        agilityButton.setCallbackData("/agilityTraining");

        var masteryButton = new InlineKeyboardButton();
        masteryButton.setText("Мастерство - " + cost.get("mastery").toString() + " золота");
        masteryButton.setCallbackData("/masteryTraining");

        var weightButton = new InlineKeyboardButton();
        weightButton.setText("Масса - " + cost.get("weight").toString() + " золота");
        weightButton.setCallbackData("/weightTraining");

        var cancelTraining = new InlineKeyboardButton();
        cancelTraining.setText("Выйти");
        cancelTraining.setCallbackData("/cancelTraining");



        rowInLine.add(powerButton);
        rowInLine.add(agilityButton);
        rowInLine1.add(masteryButton);
        rowInLine1.add(weightButton);
        rowInLine2.add(cancelTraining);

        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);


        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);

        bot.execute(sendMessage);
    }
}
