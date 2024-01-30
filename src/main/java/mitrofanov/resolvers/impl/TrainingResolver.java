package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.keyboards.TrainingKeyboard;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.TrainingService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;

public class TrainingResolver implements CommandResolver {

    private final String COMMAND_NAME = "/training";
        private final TrainingService trainingService;

        public  TrainingResolver() {
            this.trainingService = new TrainingService();

        }
        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }


        @SneakyThrows
        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
            if (text.startsWith("/training")) {
                TrainingKeyboard trainingKeyboard = new TrainingKeyboard();
                trainingKeyboard.trainingKeyboard(tg_bot, chatId);
            }
            if (text.startsWith("/agilityTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining((Long) cost.get("agility"), chatId)) {
                    trainingService.setNewAgility(chatId);
                    trainingService.decreaseGold(chatId, (Long) cost.get("agility"));
                    TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали ловкость", chatId);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                }
            }
            if (text.startsWith("/powerTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining((Long) cost.get("power"), chatId)) {
                    trainingService.setNewPower(chatId);
                    trainingService.decreaseGold(chatId, (Long) cost.get("power"));
                    TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали силу", chatId);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                }
            }
            if (text.startsWith("/masteryTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining((Long) cost.get("mastery"), chatId)) {
                    trainingService.setNewMastery(chatId);
                    trainingService.decreaseGold(chatId, (Long) cost.get("mastery"));
                    TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали  мастерство", chatId);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                }
            }
            if (text.startsWith("/weightTraining")) {
                HashMap<String, Long> cost = trainingService.countCost(chatId);
                if (trainingService.enoughGoldForTraining((Long) cost.get("weight"), chatId)) {
                    trainingService.setNewWeight(chatId);
                    trainingService.decreaseGold(chatId, (Long) cost.get("weight"));
                    TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали массу", chatId);
                } else {
                    TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
                }
            }
            if (text.startsWith("/cancelTraining")) {
                TelegramBotUtils.sendMessage(tg_bot, "Вы больше не хотите тренироваться", chatId);
                setSessionStateForThisUser(chatId, State.IDLE);
            }

        }
    }

