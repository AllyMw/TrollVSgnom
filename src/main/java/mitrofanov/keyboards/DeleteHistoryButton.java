package mitrofanov.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class DeleteHistoryButton {
    public static InlineKeyboardMarkup deleteHistoryKeyboard() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton deleteHistory = new InlineKeyboardButton();
        deleteHistory.setText("Удалить историю");
        deleteHistory.setCallbackData("/deleteHistory");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        firstRow.add(deleteHistory);

        rows.add(firstRow);

        markup.setKeyboard(rows);

        return  markup;
    }
}
