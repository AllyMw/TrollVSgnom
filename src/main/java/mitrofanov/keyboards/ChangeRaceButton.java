package mitrofanov.keyboards;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChangeRaceButton {

    @SneakyThrows
    public static InlineKeyboardMarkup PersKeyboard() {


        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton trollButton = new InlineKeyboardButton();
        trollButton.setText("Тролль");
        trollButton.setCallbackData("/choiceRiceTroll");

        InlineKeyboardButton gnomButton = new InlineKeyboardButton();
        gnomButton.setText("Гном");
        gnomButton.setCallbackData("/choiceRiceGnome");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(trollButton);
        firstRow.add(gnomButton);

        rows.add(firstRow);

        markup.setKeyboard(rows);

        return  markup;
    }
}
