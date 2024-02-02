package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.keyboards.TrainingKeyboard;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.TrainingService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;


public class TrainingResolver implements CommandResolver {

    public static TrainingKeyboard trainingResolver;
    private final String COMMAND_NAME = "/training";
    private final TrainingService trainingService;
    private long messageId;

    public  TrainingResolver() {
        this.trainingService = new TrainingService();

    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


    private List<Integer> messageIds = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId)  {

        if (text.startsWith("/training")) {
            TrainingKeyboard trainingKeyboard = new TrainingKeyboard();
            trainingKeyboard.trainingKeyboard(tg_bot, chatId);
        }
        int newGold = trainingService.setNewGold(chatId);
        if (text.startsWith("/agilityTraining")) {
            HashMap<String, Long> cost = trainingService.countCost(chatId);
            if (trainingService.enoughGoldForTraining(cost.get("agility"), chatId)) {
                trainingService.setNewAgility(chatId);
                trainingService.decreaseGold(chatId, cost.get("agility"));
                int newAgility = trainingService.setNewAgility(chatId);
                //TelegramBotUtils.sendMessage(tg_bot, "Ура! Вы прокачали ловкость, теперь она равна: " + newAgility + "\nУ Вас осталось " + newGold + " золота", chatId);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Ура! Вы прокачали ловкость, теперь она равна: " + newAgility + "\nУ Вас осталось " + newGold + " золота");
                sendMessage.setChatId(chatId);
                Message sentMessage = tg_bot.execute(sendMessage);
                messageIds.add(sentMessage.getMessageId());

                scheduler.schedule(() -> {
                    DeleteMessage deleteMessage = new DeleteMessage();
                    deleteMessage.setChatId(chatId);
                    deleteMessage.setMessageId(sentMessage.getMessageId());
                    try {
                        tg_bot.execute(deleteMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }, 15000, TimeUnit.MILLISECONDS);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                    setSessionStateForThisUser(chatId, State.IDLE);
                }
            }
            if (text.startsWith("/powerTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining(cost.get("power"), chatId)) {
                    trainingService.setNewPower(chatId);
                    trainingService.decreaseGold(chatId, cost.get("power"));
                    int newPower = trainingService.setNewPower(chatId);
                    //TelegramBotUtils.sendMessage(tg_bot, "Ура! Вы прокачали силу, теперь она равна: " + newPower + "\nУ Вас осталось " + newGold + " золота", chatId);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Ура! Вы прокачали силу, теперь она равна: " + newPower + "\nУ Вас осталось " + newGold + " золота");
                    sendMessage.setChatId(chatId);
                    Message sentMessage = tg_bot.execute(sendMessage);
                    messageIds.add(sentMessage.getMessageId());

                    scheduler.schedule(() -> {
                        DeleteMessage deleteMessage = new DeleteMessage();
                        deleteMessage.setChatId(chatId);
                        deleteMessage.setMessageId(sentMessage.getMessageId());
                        try {
                            tg_bot.execute(deleteMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }, 15000, TimeUnit.MILLISECONDS);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                    setSessionStateForThisUser(chatId, State.IDLE);
                }
            }
            if (text.startsWith("/masteryTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining(cost.get("mastery"), chatId)) {
                    trainingService.setNewMastery(chatId);
                    trainingService.decreaseGold(chatId, cost.get("mastery"));
                    int newMastery = trainingService.setNewMastery(chatId);

                    //TelegramBotUtils.sendMessage(tg_bot, "Ура! Вы прокачали  мастерство, теперь оно равно: " + newMastery + "\nУ Вас осталось " + newGold + " золота", chatId);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Ура! Вы прокачали  мастерство, теперь оно равно: " + newMastery + "\nУ Вас осталось " + newGold + " золота");
                    sendMessage.setChatId(chatId);
                    Message sentMessage = tg_bot.execute(sendMessage);
                    messageIds.add(sentMessage.getMessageId());

                    scheduler.schedule(() -> {
                        DeleteMessage deleteMessage = new DeleteMessage();
                        deleteMessage.setChatId(chatId);
                        deleteMessage.setMessageId(sentMessage.getMessageId());
                        try {
                            tg_bot.execute(deleteMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }, 15000, TimeUnit.MILLISECONDS);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                    setSessionStateForThisUser(chatId, State.IDLE);
                }
            }
            if (text.startsWith("/weightTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining(cost.get("weight"), chatId)) {
                    trainingService.setNewWeight(chatId);
                    trainingService.decreaseGold(chatId, cost.get("weight"));
                    int newWeight = trainingService.setNewWeight(chatId);
                    //TelegramBotUtils.sendMessage(tg_bot, "Ура! Вы прокачали массу, теперь она равна: " + newWeight + "\nУ Вас осталось " + newGold + " золота", chatId);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Ура! Вы прокачали массу, теперь она равна: " + newWeight + "\nУ Вас осталось " + newGold + " золота");
                    sendMessage.setChatId(chatId);
                    Message sentMessage = tg_bot.execute(sendMessage);
                    messageIds.add(sentMessage.getMessageId());

                    scheduler.schedule(() -> {
                        DeleteMessage deleteMessage = new DeleteMessage();
                        deleteMessage.setChatId(chatId);
                        deleteMessage.setMessageId(sentMessage.getMessageId());
                        try {
                            tg_bot.execute(deleteMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }, 15000, TimeUnit.MILLISECONDS);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                    setSessionStateForThisUser(chatId, State.IDLE);
                }
            }
            if (text.startsWith("/cancelTraining")) {
                TelegramBotUtils.sendMessage(tg_bot, "Вы больше не хотите тренироваться", chatId);
                setSessionStateForThisUser(chatId, State.IDLE);
            }

    }
}

