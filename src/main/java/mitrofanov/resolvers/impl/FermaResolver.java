package mitrofanov.resolvers.impl;


import lombok.SneakyThrows;
import mitrofanov.keyboards.FermaKeyboard;
import mitrofanov.resolvers.CommandResolver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class FermaResolver implements CommandResolver {
    private final String COMMAND_NAME = "/farm";

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId)  {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите на сколько часов отправитесь на ферму");
        sendMessage.setReplyMarkup(FermaKeyboard.hoursKeyboard(tg_bot, chatId));
        sendMessage.setChatId(chatId);
        tg_bot.execute(sendMessage);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}