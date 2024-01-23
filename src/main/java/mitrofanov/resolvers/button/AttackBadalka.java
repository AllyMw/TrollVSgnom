package mitrofanov.resolvers.button;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.Map;

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class AttackBadalka implements CommandResolver {
    private final String COMMAND_NAME = "/attack";
    private final BadalkaService badalkaService;

    public AttackBadalka() {
        this.badalkaService = BadalkaService.getInstance();;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        int indexUserForDeferent = badalkaService.getCurrIndexInUserForAttack(chatId);
        Long chatIdUserForDeferent = badalkaService.getUserForAttack(chatId, indexUserForDeferent).getChatId();
        ArrayList<Long> winer = badalkaService.fight(chatId, chatIdUserForDeferent);
        Map<Long, Long> table = badalkaService.changeGoldAfterFight(winer.get(0), winer.get(1));
        TelegramBotUtils.sendMessage(tg_bot, "За победу вы получили " + table.get(winer.get(0)).toString() + " золота", winer.get(0));
        TelegramBotUtils.sendMessage(tg_bot, "Вас победили и вы потеряли " + table.get(winer.get(1)).toString() + " золота", winer.get(1));

    }
}
