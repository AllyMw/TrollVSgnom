package mitrofanov.resolvers.impl;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.SessionManager;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class StartRaceResolver implements CommandResolver {
    private final String COMMAND_NAME = "/start_race";
    private final RegistrationService registrationService;

    public StartRaceResolver() {
        this.registrationService = new RegistrationService();

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

        registrationService.setNickName(text, chatId);
        setSessionStateForThisUser(chatId, State.IDLE);
        TelegramBotUtils.sendMessage(tg_bot, "Вы успешно зарегистрировались! Вам дано 100 золота на тренировку", chatId);


    }


    private static void setSessionStateForThisUser(Long chat_id, State state) {
        SessionManager.getInstance().getSession(chat_id).setState(state);
    }
}
}
