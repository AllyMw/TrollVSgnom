package mitrofanov.keyboards;

import lombok.NonNull;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingKeyboard {
    public void trainingKeyboard(TelegramLongPollingBot bot, Long chatId) throws TelegramApiException {

        TrainingService trainingService = new TrainingService();
        HashMap<String, Long> cost = trainingService.countCost(Long.valueOf(chatId));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Какую характеристику хотите прокачать?\nПосле оканчания тренировки не забудь нажать на кнопку \"Выйти\"");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();

        var powerButton = new InlineKeyboardButton();
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
    public String updateTrainingKeyboard(TelegramLongPollingBot bot, Long chatId, Integer messageId, String callBack) throws TelegramApiException {
        if (callBack.equals("/agilityTraining")
                || callBack.equals("/powerTraining")
                || callBack.equals("/masteryTraining")
                || callBack.equals("/weightTraining")) {
            TrainingService trainingService = new TrainingService();
            HashMap<String, Long> cost = trainingService.countCost(chatId);

            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(messageId);
            editMessageText.setText("Какую характеристику хотите прокачать?\nПосле оканчания тренировки не забудь нажать на кнопку \"Выйти\"");

            InlineKeyboardButton updatePowerButton = new InlineKeyboardButton();
            updatePowerButton.setText("Cила - " + cost.get("power") + " золота");
            updatePowerButton.setCallbackData("/powerTraining");

            InlineKeyboardButton updateAgilityButton = new InlineKeyboardButton();
            updateAgilityButton.setText("Ловкость - " + cost.get("agility") + " золота");
            updateAgilityButton.setCallbackData("/agilityTraining");

            InlineKeyboardButton updateMasteryButton = new InlineKeyboardButton();
            updateMasteryButton.setText("Мастерство - " + cost.get("mastery") + " золота");
            updateMasteryButton.setCallbackData("/masteryTraining");

            InlineKeyboardButton updateWeightButton = new InlineKeyboardButton();
            updateWeightButton.setText("Масса - " + cost.get("weight") + " золота");
            updateWeightButton.setCallbackData("/weightTraining");

            InlineKeyboardButton updateCancelTraining = new InlineKeyboardButton();
            updateCancelTraining.setText("Выйти");
            updateCancelTraining.setCallbackData("/cancelTraining");

            List<InlineKeyboardButton> updateRowInLine = new ArrayList<>();
            updateRowInLine.add(updatePowerButton);
            updateRowInLine.add(updateAgilityButton);

            List<InlineKeyboardButton> updateRowInLine1 = new ArrayList<>();
            updateRowInLine1.add(updateMasteryButton);
            updateRowInLine1.add(updateWeightButton);

            List<InlineKeyboardButton> updateRowInLine2 = new ArrayList<>();
            updateRowInLine2.add(updateCancelTraining);

            List<List<InlineKeyboardButton>> updateRowsInLine = new ArrayList<>();
            updateRowsInLine.add(updateRowInLine);
            updateRowsInLine.add(updateRowInLine1);
            updateRowsInLine.add(updateRowInLine2);

            InlineKeyboardMarkup updatedMarkup = new InlineKeyboardMarkup();
            updatedMarkup.setKeyboard(updateRowsInLine);

            editMessageText.setReplyMarkup(updatedMarkup);

            bot.execute(editMessageText);
        }
        return null;
    }
}