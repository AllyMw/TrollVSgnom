package mitrofanov.resolvers.impl;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.RegistrationService;
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

        registrationService.changeRace(text, chatId);
        StartNicknameResolver.setSessionStateForThisUser(chatId, State.IDLE);
        TelegramBotUtils.sendMessage(tg_bot, "Вы успешно зарегистрировались! Вам дано 100 золота на тренировку", chatId);


    }



}

