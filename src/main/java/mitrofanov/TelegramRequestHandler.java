package mitrofanov;

import lombok.SneakyThrows;
import mitrofanov.commands.StartCommands;
import mitrofanov.repository.StatusRepository;
import mitrofanov.service.RegistrationService;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                if (!statusRepository.hasChatId(message.getChatId())) {  // если такой пользователь не зарегистрирован
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Здравствуйте, у вас еще нет персонажа! Давайте зарегистрируем его. Введите никнейм: ");
                    execute(sendMessage);
                    statusRepository.addNewChatID(message.getChatId());
                    registrationService.addNewPlayer(message.getChatId());
                } else if (statusRepository.getStatusByChatId(message.getChatId()) == 1) {
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Теперь введите(пожалуйста, введите точно как написано, без ковычек:" +
                            " 'troll' - игра за расу тролль, 'gnom' - игра за расу гном)");
                    execute(sendMessage);
                    registrationService.changeNickName(message.getText(), message.getChatId());
                    statusRepository.insertStatusCode(message.getChatId(), 2);
                } else if (statusRepository.getStatusByChatId(message.getChatId()) == 2) {
                    if ((message.getText().equals("troll")) | (message.getText().equals("gnom"))) {
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
            if (message.getText().startsWith("/training") & statusRepository.getStatusByChatId(message.getChatId()) == 3) {
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Какую характеристику хотите прокачать?");

                InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
                var powerButton = new InlineKeyboardButton();
                HashMap<String, Long> cost = trainingService.countCost(message.getChatId());
                powerButton.setText("Cила - " + cost.get("power").toString() + " золота");
                powerButton.setCallbackData("POWER_BUTTON");

                var agilityButton = new InlineKeyboardButton();
                agilityButton.setText("Ловкость - " + cost.get("agility").toString() + " золота");
                agilityButton.setCallbackData("AGILITY_BUTTON");

                var masteryButton = new InlineKeyboardButton();
                masteryButton.setText("Мастерство - " + cost.get("mastery").toString() + " золота");
                masteryButton.setCallbackData("MASTERY_BUTTON");

                var weightButton = new InlineKeyboardButton();
                weightButton.setText("Масса - " + cost.get("weight").toString() + " золота");
                weightButton.setCallbackData("WEIGHT_BUTTON");

                rowInLine.add(powerButton);
                rowInLine.add(agilityButton);
                rowInLine1.add(masteryButton);
                rowInLine1.add(weightButton);

                rowsInLine.add(rowInLine);
                rowsInLine.add(rowInLine1);

                markupInLine.setKeyboard(rowsInLine);
                sendMessage.setReplyMarkup(markupInLine);

                execute(sendMessage);
            }


            } else if (update.hasCallbackQuery()) {
                var query = update.getCallbackQuery();
                String callData = query.getData();
                Long chatID = query.getMessage().getChatId();
                SendMessage sendMessage = new SendMessage();
                HashMap<String, Long> cost = trainingService.countCost(chatID);
                if (callData.equals("POWER_BUTTON")) {
                    if (trainingService.enoughGoldForTraining(cost.get("power"), chatID)) {
                        trainingService.setNewPower(chatID);
                        trainingService.decreaseGold(chatID, cost.get("power"));
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Ура прокачали силу");
                        execute(sendMessage);
                    } else {
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Мало золота, иди работать");
                        execute(sendMessage);
                    }

                } else if (callData.equals("AGILITY_BUTTON")) {
                    if (trainingService.enoughGoldForTraining(cost.get("agility"), chatID)) {
                        trainingService.setNewAgility(chatID);
                        trainingService.decreaseGold(chatID, cost.get("agility"));
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Ура прокачали ловкость");
                        execute(sendMessage);
                    } else {
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Мало золота, иди работать");
                        execute(sendMessage);
                    }

                } else if (callData.equals("MASTERY_BUTTON")) {
                    if (trainingService.enoughGoldForTraining(cost.get("mastery"), chatID)) {
                        trainingService.setNewMastery(chatID);
                        trainingService.decreaseGold(chatID, cost.get("mastery"));
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Ура прокачали мастерство");
                        execute(sendMessage);
                    } else {
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Мало золота, иди работать");
                        execute(sendMessage);
                    }

                } else if (callData.equals("WEIGHT_BUTTON")) {
                    if (trainingService.enoughGoldForTraining(cost.get("weight"), chatID)) {
                        trainingService.setNewWeight(chatID);
                        trainingService.decreaseGold(chatID, cost.get("weight"));
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Ура прокачали массу");
                        execute(sendMessage);
                    } else {
                        sendMessage.setChatId(chatID);
                        sendMessage.setText("Мало золота, иди работать");
                        execute(sendMessage);
                    }
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
