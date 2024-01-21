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

// Создаем кнопку для выбора тролля
        InlineKeyboardButton trollButton = new InlineKeyboardButton();
        trollButton.setText("Тролль");
        trollButton.setCallbackData("/choiceRiceTroll");

// Создаем кнопку для выбора гнома
        InlineKeyboardButton gnomButton = new InlineKeyboardButton();
        gnomButton.setText("Гном");
        gnomButton.setCallbackData("/choiceRiceGnome");


// Добавляем кнопки в первую строку
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(trollButton);
        firstRow.add(gnomButton);

// Добавляем строку в список строк
        rows.add(firstRow);

// Устанавливаем список строк в разметку
        markup.setKeyboard(rows);

        return  markup;
    }
}
