package mitrofanov.resolvers.button;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.TrainingService;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;

public class PowerTraining implements CommandResolver {
        private final String COMMAND_NAME = "/powerTraining";
        private final TrainingService trainingService;

        public PowerTraining() {
            this.trainingService = new TrainingService();
        }

        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }

        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

            HashMap<String, Long> cost = trainingService.countCost(chatId);
            if (trainingService.enoughGoldForTraining((Long) cost.get("power"), chatId)) {
                trainingService.setNewPower(chatId);
                trainingService.decreaseGold(chatId, (Long) cost.get("power"));
                TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали силу", chatId);
            } else {
                TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
            }
        }
}
