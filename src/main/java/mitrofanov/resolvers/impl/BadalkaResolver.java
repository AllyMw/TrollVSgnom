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

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class BadalkaResolver implements CommandResolver {

    private final String COMMAND_NAME = "/badalka";
    private final BadalkaService badalkaService;

    public BadalkaResolver() {
        this.badalkaService = new BadalkaService();

    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        if (badalkaService.hasNotListForThisUser(chatId)) {
            badalkaService.setNewListUserForAttack(chatId);
        }
        String userProfileForAttack = badalkaService.generateUserProfileForAttack(badalkaService.getUserForAttack(chatId).getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(userProfileForAttack);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(BadalkaButtonKeyboard.badalkaKeyboard());
        tg_bot.execute(sendMessage);

    }
}