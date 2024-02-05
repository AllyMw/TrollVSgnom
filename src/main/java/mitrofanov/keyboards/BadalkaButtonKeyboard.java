package mitrofanov.keyboards;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class BadalkaButtonKeyboard {

    public static InlineKeyboardMarkup badalkaKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton attackButton = new InlineKeyboardButton();
        attackButton.setText("Напасть");
        attackButton.setCallbackData("/attack");

        InlineKeyboardButton skipThisPersButton = new InlineKeyboardButton();
        skipThisPersButton.setText("Пропустить");
        skipThisPersButton.setCallbackData("/skip");

        InlineKeyboardButton leaveBadalkaButton = new InlineKeyboardButton();
        leaveBadalkaButton.setText("Уйти из бадалки");
        leaveBadalkaButton.setCallbackData("/leaveBadalka");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(attackButton);
        firstRow.add(skipThisPersButton);
        firstRow.add(leaveBadalkaButton);

        rows.add(firstRow);
        markup.setKeyboard(rows);

        return markup;
    }
}

