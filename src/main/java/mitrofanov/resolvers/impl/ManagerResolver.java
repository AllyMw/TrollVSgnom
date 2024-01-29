package mitrofanov.resolvers.impl;

import mitrofanov.keyboards.BadalkaButtonKeyboard;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static mitrofanov.handlers.TelegramRequestHandler.resolvers;
import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class ManagerResolver implements CommandResolver {
    private final String COMMAND_NAME = "/idle";
    private final BadalkaService badalkaService;

    public ManagerResolver() {
        this.badalkaService = BadalkaService.getInstance();

    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        if (text.startsWith("/profile")) {
            CommandResolver commandResolver = resolvers.get("/profile");
            commandResolver.resolveCommand(tg_bot, text, chatId);
        } else if (text.startsWith("/badalka")) {
            setSessionStateForThisUser(chatId, State.BADALKA);
            CommandResolver commandResolver = resolvers.get("/badalka");
            commandResolver.resolveCommand(tg_bot, text, chatId);
        } else if (text.startsWith("/training")) {
            setSessionStateForThisUser(chatId, State.TRAINING);
            CommandResolver commandResolver = resolvers.get("/training");
            commandResolver.resolveCommand(tg_bot, text, chatId);
        } else if (text.startsWith("/farm")) {
            setSessionStateForThisUser(chatId, State.FARM);
            CommandResolver commandResolver = resolvers.get("/farm");
            commandResolver.resolveCommand(tg_bot, text, chatId);
        } else {
            TelegramBotUtils.sendMessage(tg_bot, "Вы что-то сделали не так", chatId);

        }

    }
}
