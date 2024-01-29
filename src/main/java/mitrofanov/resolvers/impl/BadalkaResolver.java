package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.keyboards.BadalkaButtonKeyboard;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class BadalkaResolver implements CommandResolver {

    private final String COMMAND_NAME = "/badalka";
    private final BadalkaService badalkaService;

    public BadalkaResolver() {
        this.badalkaService = BadalkaService.getInstance();

    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        if (badalkaService.hasNotListForThisUser(chatId)) {
            badalkaService.setNewListUserForAttack(chatId);
            badalkaService.setCurrIndexInUserForAttack(chatId);
        }
        int curIndex = badalkaService.getCurrIndexInUserForAttack(chatId);
        String userProfileForAttack = badalkaService.generateUserProfileForAttack(badalkaService.getUserForAttack(chatId, curIndex).
                getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(userProfileForAttack);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(BadalkaButtonKeyboard.badalkaKeyboard());
        try {
            tg_bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}