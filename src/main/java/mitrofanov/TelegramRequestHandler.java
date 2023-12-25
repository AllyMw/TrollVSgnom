package mitrofanov;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramRequestHandler extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "TrollVSgnom_bot";
    }

    @Override
    public String getBotToken() {
        return "6978497435:AAHJjcrb03lsitkS-MYuq6cPElDI5dPfOI8";
    }
}
