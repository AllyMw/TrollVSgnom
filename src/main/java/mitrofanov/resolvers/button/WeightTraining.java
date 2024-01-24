package mitrofanov.resolvers.button;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.TrainingService;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;

public class WeightTraining implements CommandResolver{

        private final String COMMAND_NAME = "/weightTraining";
        private final TrainingService trainingService;

        public WeightTraining() {
            this.trainingService = new TrainingService();
        }

        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }

        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

            HashMap<String, Long> cost = trainingService.countCost(chatId);
            if (trainingService.enoughGoldForTraining((Long) cost.get("weight"), chatId)) {
                trainingService.setNewWeight(chatId);
                trainingService.decreaseGold(chatId, (Long) cost.get("weight"));
                TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали массу", chatId);
            } else {
                TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
            }
        }
}
