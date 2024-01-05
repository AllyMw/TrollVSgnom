package mitrofanov;

import lombok.SneakyThrows;
import mitrofanov.commands.StartCommands;
import mitrofanov.repository.StatusRepository;
import mitrofanov.service.RegistrationService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramRequestHandler extends TelegramLongPollingBot {

        public void init() throws TelegramApiException {
            this.execute(new SetMyCommands(StartCommands.init(), new BotCommandScopeDefault(), null));
        }

        @SneakyThrows
        @Override
        public void onUpdateReceived(Update update) {
            StatusRepository statusRepository = new StatusRepository();
            RegistrationService registrationService = new RegistrationService();
            if (update.hasMessage()) {
                var message = update.getMessage();
                SendMessage sendMessage = new SendMessage();
                boolean r = !statusRepository.hasChatId(message.getChatId());
                if (r) {  // если такой пользователь не зарегистрирован
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Здравствуйте, у вас еще нет персонажа! Давайте зарегистрируем его. Введите никнейм: ");
                    execute(sendMessage);
                    statusRepository.addNewChatID(message.getChatId());
                    registrationService.addNewPlayer(message.getChatId());
                } else if (statusRepository.getStatusByChatId(message.getChatId()) == 1) {
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Теперь введите(пожалуйста, введите точно как написано, без ковычек:" +
                            " 'troll' - игра за расу тролль, 'gnom' - игра за расу гном, 'princess' - игра за принцесс");
                    execute(sendMessage);
                    registrationService.changeNickName(message.getText(), message.getChatId());
                    statusRepository.insertStatusCode(message.getChatId(), 2);
                } else if (statusRepository.getStatusByChatId(message.getChatId()) == 2) {
                    if ((message.getText().equals("troll")) | (message.getText().equals("gnom")) | (message.getText().equals("princess"))) {
                        sendMessage.setChatId(message.getChatId().toString());
                        sendMessage.setText("Отлично, можно начинать играть. Рекомендую пойти на тренировку");
                        execute(sendMessage);
                        registrationService.changeRace(message.getText(), message.getChatId());
                        statusRepository.insertStatusCode(message.getChatId(), 3);
                    } else {
                        sendMessage.setChatId(message.getChatId().toString());
                        sendMessage.setText("Введи четко, как написано!");
                        execute(sendMessage);
                    }
                }
//                    } else (//вывести профиль и кнопки)
//                switch (statusRepository.getStatusByChatId(message.getChatId())) {
//                    case 1: // 1 - статус ожидания ввода никнейма
//                    sendMessage.setChatId(message.getChatId().toString());
//                    sendMessage.setText("Теперь введите(пожалуйста, введите точно как написано, без ковычек:" +
//                            " 'troll' - игра за расу тролль, 'gnom' - игра за расу гном");
//                    execute(sendMessage);
//                    registrationService.changeNickName(message.getText(), message.getChatId());
//                    statusRepository.insertStatusCode(message.getChatId(), 2);
//                    break;
//                    case 2: // 2 - статус ожидания ввода расы
//                    if ((message.getText().equals("troll")) | (message.getText().equals("gnom"))) {
//                        sendMessage.setChatId(message.getChatId().toString());
//                        sendMessage.setText("Отлично, можно начинать играть. Рекомендую пойти на тренировку");
//                        execute(sendMessage);
//                        registrationService.changeRace(message.getText(), message.getChatId());
//                        statusRepository.insertStatusCode(message.getChatId(), 3);
//                    } else {
//                        sendMessage.setChatId(message.getChatId().toString());
//                        sendMessage.setText("Введи четко, как написано!");
//                        execute(sendMessage);
//                    }
//                    break;




            } else if (update.hasCallbackQuery()) {
                var query = update.getCallbackQuery();

                String callData = query.getData();
                Long chatID = query.getMessage().getChatId();

                sendMessage(callData, chatID.toString());
            }

        }

        private void sendMessage(String text, String chatId) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(text);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                System.out.println("чет пошло не так при отправке сообщения");
            }
        }


    @Override
    public String getBotUsername() {
        return "TrollVSgnom_bot";
    }

    @Override
    public String getBotToken() {
        return "6978497435:AAHJjcrb03lsitkS-MYuq6cPElDI5dPfOI8";
    }
}
