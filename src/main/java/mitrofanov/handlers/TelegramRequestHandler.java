package mitrofanov.handlers;

import lombok.SneakyThrows;
import mitrofanov.Configuration;
import mitrofanov.commands.StartCommands;
import mitrofanov.keyboards.*;
import mitrofanov.model.repository.StatusRepository;
import mitrofanov.service.RegistrationService;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class TelegramRequestHandler extends TelegramLongPollingBot {

    private final StatusRepository statusRepository;
    private final RegistrationService registrationService;
    private final TrainingService trainingService;


    public TelegramRequestHandler() {
        statusRepository = new StatusRepository();
        registrationService = new RegistrationService();
        trainingService = new TrainingService();

    }

    public void init() throws TelegramApiException {
            this.execute(new SetMyCommands(StartCommands.init(), new BotCommandScopeDefault(), null));
        }

        @SneakyThrows
        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage()) {
                var message = update.getMessage();
                SendMessage sendMessage = new SendMessage();
                StartKeyboard.startKeyboard(statusRepository, message, sendMessage, registrationService);

                if (message.getText().startsWith("/training") & statusRepository.getStatusByChatId(message.getChatId()) == 3) {
                    TrainingKeyboard.trainingKeyboard(sendMessage, message, trainingService);
                }
            } else if (update.hasCallbackQuery()) {
                var query = update.getCallbackQuery();
                String callData = query.getData();
                Long chatID = query.getMessage().getChatId();
                SendMessage sendMessage = new SendMessage();
                HashMap<String, Long> cost = trainingService.countCost(chatID);

                if (callData.equals("POWER_BUTTON")) {
                    PowerButtonKeyboard.powerButtonKeyboard(trainingService, cost, chatID, sendMessage);

                } else if (callData.equals("AGILITY_BUTTON")) {
                    AgilityButtonKeyboard.agilityButtonKeyboard(trainingService, cost, chatID, sendMessage);

                } else if (callData.equals("MASTERY_BUTTON")) {
                    MasteryButtonKeyboard.masterButtonKeyboard(trainingService, cost, chatID, sendMessage);

                } else if (callData.equals("WEIGHT_BUTTON")) {
                    WeightButtonKeyboard.weightButtonKeyboard(trainingService, cost, chatID, sendMessage);
                }
            //    sendMessage(callData, chatID.toString());
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
