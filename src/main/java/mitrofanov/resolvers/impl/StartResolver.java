package mitrofanov.resolvers.impl;
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
import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;




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
            } else if(registrationService.nickNameNotNull(chatId)) {
                SendMessage sendMessage = new SendMessage();
                registrationService.setNickName(text, chatId);
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
                try {
                    tg_bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (registrationService.raceNotNull(chatId)) {
                if (text.equals("/choiceRiceTroll")) {
                    registrationService.setRace("Troll", chatId);
                    TelegramBotUtils.sendMessage(tg_bot, "Вы успешно зарегистрировались! Вам дано 100 золота на тренировку", chatId);
                    setSessionStateForThisUser(chatId, State.IDLE);
                } if (text.equals("/choiceRiceGnome")) {
                    registrationService.setRace("Gnom", chatId);
                    TelegramBotUtils.sendMessage(tg_bot, "Вы успешно зарегистрировались! Вам дано 100 золота на тренировку", chatId);
                    setSessionStateForThisUser(chatId, State.IDLE);
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Вы должны выбрать расу через кнопки");
                    sendMessage.setChatId(chatId);
                    sendMessage.setReplyMarkup(ChangeRaceButton.PersKeyboard());
                    try {
                        tg_bot.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
    }


    private void setState(Long chatId, State state) {
        SessionManager.getInstance().getSession(chatId).setState(state);
    }

}


