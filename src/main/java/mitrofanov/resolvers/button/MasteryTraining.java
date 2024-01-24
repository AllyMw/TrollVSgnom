package mitrofanov.resolvers.button;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.TrainingService;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;

public class MasteryTraining implements CommandResolver{
        private final String COMMAND_NAME = "/masteryTraining";
        private final TrainingService trainingService;

        public MasteryTraining() {
            this.trainingService = new TrainingService();
        }

        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }

        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

            HashMap<String, Long> cost = trainingService.countCost(chatId);
            if (trainingService.enoughGoldForTraining((Long) cost.get("mastery"), chatId)) {
                trainingService.setNewMastery(chatId);
                trainingService.decreaseGold(chatId, (Long) cost.get("mastery"));
                TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали  мастерство", chatId);
            } else {
                TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
            }
        }
    }


