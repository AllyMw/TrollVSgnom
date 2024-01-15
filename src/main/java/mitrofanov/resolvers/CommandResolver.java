package mitrofanov.resolvers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandResolver {

    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id);

    public String getCommandName();

}