package mitrofanov.resolvers.impl;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.SessionManager;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;


public class StartResolver implements CommandResolver {

    private final String COMMAND_NAME = "/start";
    private final RegistrationService registrationService;
    public StartResolver() {
        this.registrationService = new RegistrationService();

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

            if (!registrationService.hasChatId(chatId)) {
                TelegramBotUtils.sendMessage(tg_bot, "Здравствуйте, у вас еще нет персонажа! Давайте зарегистрируем его. Введите никнейм:", chatId);
                registrationService.addNewPlayer(chatId);
                setState(chatId, State.START_NICKNAME);
            }
    }


    private void setState(Long chatId, State state) {
        SessionManager.getInstance().getSession(chatId).setState(state);
    }

}


