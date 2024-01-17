package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.keyboards.ChangeRaceButton;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.SessionManager;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static mitrofanov.keyboards.ChangeRaceButton.PersKeyboard;

public class StartNicknameResolver implements CommandResolver {

    private final String COMMAND_NAME = "/start_nickname";
    private final RegistrationService registrationService;

    public StartNicknameResolver() {
        this.registrationService = new RegistrationService();

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

        registrationService.setNickName(text, chatId);
        setSessionStateForThisUser(chatId, State.START_RACE);
        TelegramBotUtils.sendMessage(tg_bot, "Выберите расу:", chatId);
        ChangeRaceButton.PersKeyboard(tg_bot, chatId);


        // добавить валидацию наличия никнейма
        // добавить вывод кнопок для расы
    }


    public static void setSessionStateForThisUser(Long chat_id, State state) {
        SessionManager.getInstance().getSession(chat_id).setState(state);
    }
}