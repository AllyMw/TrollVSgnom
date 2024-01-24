package mitrofanov.resolvers.button;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.TrainingService;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;

public class AgilityTraining implements CommandResolver {
    private final String COMMAND_NAME = "/agilityTraining";
    private final TrainingService trainingService;

    public AgilityTraining() {
        this.trainingService = new TrainingService();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {

        HashMap<String, Long> cost = trainingService.countCost(chatId);
        if (trainingService.enoughGoldForTraining((Long) cost.get("agility"), chatId)) {
            trainingService.setNewAgility(chatId);
            trainingService.decreaseGold(chatId, (Long) cost.get("agility"));
            TelegramBotUtils.sendMessage(tg_bot, "Ура вы прокачали ловкость", chatId);
        } else {
            TelegramBotUtils.sendMessage(tg_bot, "Мало золота, иди работай", chatId);
        }
    }
}

