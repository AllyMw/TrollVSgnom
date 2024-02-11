package mitrofanov.resolvers.impl;

import mitrofanov.keyboards.DeleteHistoryButton;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.EventService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;

public class EventResolver implements CommandResolver {
    private final EventService eventService;
    private final String COMMAND_NAME = "/event";

    public EventResolver() {
        this.eventService = new EventService();
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        if (text.startsWith("/deleteHistory")) {
            eventService.deleteHistoryByChatId(chatId);
            TelegramBotUtils.sendMessage(tg_bot, "Ваша история удалина", chatId);
        } else {
            String userEvent = eventService.generateEventUser(chatId);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(userEvent);
            sendMessage.setReplyMarkup(DeleteHistoryButton.deleteHistoryKeyboard());

            try {
                tg_bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            setSessionStateForThisUser(chatId, State.IDLE);
        }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}