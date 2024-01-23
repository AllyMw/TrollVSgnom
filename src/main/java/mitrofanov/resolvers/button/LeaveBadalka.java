package mitrofanov.resolvers.button;

import mitrofanov.keyboards.BadalkaButtonKeyboard;
import mitrofanov.model.entity.User;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class LeaveBadalka implements CommandResolver{
        private final String COMMAND_NAME = "/leaveBadalka";
        private final BadalkaService badalkaService;

        public LeaveBadalka() {
            this.badalkaService = BadalkaService.getInstance();;
        }

        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }

        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
            TelegramBotUtils.sendMessage(tg_bot, "Вы ушли с поля боя", chatId);
                badalkaService.deleteCurrIndexInUserForAttackAndList(chatId);
                setSessionStateForThisUser(chatId, State.IDLE);
            }
}

