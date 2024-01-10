package mitrofanov.keyboards;

import mitrofanov.handlers.TelegramRequestHandler;
import mitrofanov.model.repository.StatusRepository;
import mitrofanov.service.RegistrationService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartKeyboard {
    public static void startKeyboard(StatusRepository statusRepository, Message message, SendMessage sendMessage, RegistrationService registrationService) throws TelegramApiException {
        TelegramLongPollingBot bot = new TelegramRequestHandler();

        if (!statusRepository.hasChatId(Long.valueOf(message.getChatId()))) {  // если такой пользователь не зарегистрирован
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText("Здравствуйте, у вас еще нет персонажа! Давайте зарегистрируем его. Введите никнейм: ");
            bot.execute(sendMessage);
            statusRepository.addNewChatID(Long.valueOf(message.getChatId()));
            registrationService.addNewPlayer(Long.valueOf(message.getChatId()));
        } else if (statusRepository.getStatusByChatId(Long.valueOf(message.getChatId())) == 1) {
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText("Теперь введите(пожалуйста, введите точно как написано, без ковычек:" +
                    " 'troll' - игра за расу тролль, 'gnom' - игра за расу гном)");
            bot.execute(sendMessage);
            registrationService.changeNickName(message.getText(), Long.valueOf(message.getChatId()));
            statusRepository.insertStatusCode(Long.valueOf(message.getChatId()), 2);
        } else if (statusRepository.getStatusByChatId(Long.valueOf(message.getChatId())) == 2) {
            if ((message.getText().equals("troll")) | (message.getText().equals("gnom"))) {
                sendMessage.setChatId(message.getChatId().toString());
                sendMessage.setText("Отлично, можно начинать играть. Рекомендую пойти на тренировку");
                bot.execute(sendMessage);
                registrationService.changeRace(message.getText(), Long.valueOf(message.getChatId()));
                statusRepository.insertStatusCode(Long.valueOf(message.getChatId()), 3);
            } else {
                sendMessage.setChatId(message.getChatId().toString());
                sendMessage.setText("Введи четко, как написано!");
                bot.execute(sendMessage);
            }
        }
    }
}
