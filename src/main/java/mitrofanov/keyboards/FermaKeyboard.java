package mitrofanov.keyboards;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class FermaKeyboard {
    public static InlineKeyboardMarkup hoursKeyboard() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton threeButton = new InlineKeyboardButton();
        threeButton.setText("3 часа");
        threeButton.setCallbackData("/threeHours");

        InlineKeyboardButton sixButton = new InlineKeyboardButton();
        sixButton.setText("6 часов");
        sixButton.setCallbackData("/sixHours");

        InlineKeyboardButton twelveButton = new InlineKeyboardButton();
        twelveButton.setText("12 часов");
        twelveButton.setCallbackData("/twelveHours");

        InlineKeyboardButton leaveFarmButton = new InlineKeyboardButton();
        leaveFarmButton.setText("Покинуть ферму");
        leaveFarmButton.setCallbackData("/leaveFarm");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(threeButton);
        firstRow.add(sixButton);
        firstRow.add(twelveButton);

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(leaveFarmButton);

        rows.add(firstRow);
        rows.add(secondRow);

        markup.setKeyboard(rows);

        return  markup;
    }
}