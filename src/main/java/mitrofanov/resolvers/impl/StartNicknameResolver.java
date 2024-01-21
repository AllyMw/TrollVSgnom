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
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        SendMessage sendMessage = new SendMessage();
        registrationService.setNickName(text, chatId);
        setSessionStateForThisUser(chatId, State.START_RACE);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile("https://i.postimg.cc/Jz3FmNx8/tttttt.jpg"));
        try {
            tg_bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        sendMessage.setText("Выберите расу");
        sendMessage.setReplyMarkup(ChangeRaceButton.PersKeyboard());
        sendMessage.setChatId(chatId);
        tg_bot.execute(sendMessage);

        // добавить валидацию наличия никнейма

    }


    public static void setSessionStateForThisUser(Long chat_id, State state) {
        SessionManager.getInstance().getSession(chat_id).setState(state);
    }
}