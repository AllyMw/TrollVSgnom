package mitrofanov;

import lombok.SneakyThrows;
import mitrofanov.commands.StartCommands;
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
            if (update.hasMessage()) {
                var message = update.getMessage();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                sendMessage.setText("пиздец");
                execute(sendMessage);
                if (message.hasText()) {
                    sendMessage.setText("pohyi");
                    String text = message.getText();
                    String chatId = message.getChatId().toString();


                    if (text.startsWith("/random")) {

                        sendMessage.setChatId(update.getMessage().getChatId().toString());


                        sendMessage.setText("Выберите действие:");
                        sendMessage.setText("pohyi");

                        execute(sendMessage);
                    }

                }
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
