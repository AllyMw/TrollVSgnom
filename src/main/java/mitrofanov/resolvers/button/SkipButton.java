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

import java.util.ArrayList;
import java.util.Map;

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class SkipButton implements CommandResolver{
        private final String COMMAND_NAME = "/skip";
        private final BadalkaService badalkaService;

        public SkipButton() {
            this.badalkaService = BadalkaService.getInstance();;
        }

        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }

        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
            badalkaService.setCurrIndexInUserForAttack(chatId);
            SendMessage sendMessage = new SendMessage();
            if (badalkaService.hasLenghtUserForAttackMoreCurrIndex(chatId)) {
                User userForAttacked = badalkaService.getUserForAttack(chatId, badalkaService.getCurrIndexInUserForAttack(chatId));
               String userProfileForAttack =  badalkaService.generateUserProfileForAttack(userForAttacked.getChatId());
                sendMessage.setText(userProfileForAttack);
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(BadalkaButtonKeyboard.badalkaKeyboard());
                try {
                    tg_bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                sendMessage.setText("Сори, больше никого нет, иди на ферму");
                sendMessage.setChatId(chatId);
                badalkaService.deleteCurrIndexInUserForAttackAndList(chatId);
                setSessionStateForThisUser(chatId, State.IDLE);
                try {
                    tg_bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }



        }
    }

