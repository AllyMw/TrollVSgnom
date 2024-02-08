package mitrofanov.resolvers.impl;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.EventService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;

public class EventResolver implements CommandResolver {
    private final EventService eventService;
    private final String COMMAND_NAME = "/event";

    public EventResolver() {
        this.eventService = new EventService();
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
            String userEvent = eventService.generateEventUser(chatId);

            TelegramBotUtils.sendMessage(tg_bot, userEvent, chatId);

            setSessionStateForThisUser(chatId, State.IDLE);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}