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
    public static void trainingKeyboard(SendMessage sendMessage, Message message, TrainingService trainingService) throws TelegramApiException {
        TelegramLongPollingBot bot = new TelegramRequestHandler();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Какую характеристику хотите прокачать?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var powerButton = new InlineKeyboardButton();
        HashMap<String, Long> cost = trainingService.countCost(Long.valueOf(message.getChatId()));
        powerButton.setText("Cила - " + cost.get("power").toString() + " золота");
        powerButton.setCallbackData("POWER_BUTTON");

        var agilityButton = new InlineKeyboardButton();
        agilityButton.setText("Ловкость - " + cost.get("agility").toString() + " золота");
        agilityButton.setCallbackData("AGILITY_BUTTON");

        var masteryButton = new InlineKeyboardButton();
        masteryButton.setText("Мастерство - " + cost.get("mastery").toString() + " золота");
        masteryButton.setCallbackData("MASTERY_BUTTON");

        var weightButton = new InlineKeyboardButton();
        weightButton.setText("Масса - " + cost.get("weight").toString() + " золота");
        weightButton.setCallbackData("WEIGHT_BUTTON");

        rowInLine.add(powerButton);
        rowInLine.add(agilityButton);
        rowInLine1.add(masteryButton);
        rowInLine1.add(weightButton);

        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);

        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);

        bot.execute(sendMessage);
    }
}
