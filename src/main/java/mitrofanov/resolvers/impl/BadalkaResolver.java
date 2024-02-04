package mitrofanov.resolvers.impl;

import mitrofanov.keyboards.BadalkaButtonKeyboard;
import mitrofanov.model.entity.User;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.service.EventService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Map;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;


public class BadalkaResolver implements CommandResolver {

    private final String COMMAND_NAME = "/badalka";
    private final BadalkaService badalkaService;
    private final EventService eventService;

    public BadalkaResolver() {
        this.badalkaService = BadalkaService.getInstance();
        this.eventService = new EventService();

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        if (badalkaService.isBodalkaAvailable(chatId)) {
            if (text.startsWith("/badalka")) {
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
            if (text.startsWith("/attack")) {
                setSessionStateForThisUser(chatId, State.IDLE);
                int indexUserForDeferent = badalkaService.getCurrIndexInUserForAttack(chatId);
                Long chatIdUserForDeferent = badalkaService.getUserForAttack(chatId, indexUserForDeferent).getChatId();
                ArrayList<Long> winer = badalkaService.fight(chatId, chatIdUserForDeferent);
                Map<Long, Long> table = badalkaService.changeGoldAfterFight(winer.get(0), winer.get(1));
                TelegramBotUtils.sendMessage(tg_bot, "За победу вы получили " + table.get(winer.get(0)).toString() + " золота", winer.get(0));
                TelegramBotUtils.sendMessage(tg_bot, "Вас победили и вы потеряли " + table.get(winer.get(1)).toString() + " золота", winer.get(1));
                eventService.addNewBadalkaEvent(winer, table);
                badalkaService.setTimeLastAttack(chatId);

            }
            if (text.startsWith("/skip")) {
                badalkaService.setCurrIndexInUserForAttack(chatId);
                SendMessage sendMessage = new SendMessage();
                if (badalkaService.hasLenghtUserForAttackMoreCurrIndex(chatId)) {
                    User userForAttacked = badalkaService.getUserForAttack(chatId, badalkaService.getCurrIndexInUserForAttack(chatId));
                    String userProfileForAttack = badalkaService.generateUserProfileForAttack(userForAttacked.getChatId());
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
            if (text.startsWith("/leaveBadalka")) {
                TelegramBotUtils.sendMessage(tg_bot, "Вы ушли с поля боя", chatId);
                badalkaService.deleteCurrIndexInUserForAttackAndList(chatId);
                setSessionStateForThisUser(chatId, State.IDLE);
            }
        } else {
            setSessionStateForThisUser(chatId, State.IDLE);
            TelegramBotUtils.sendMessage(tg_bot, "Вы сейчас не можете сражаться", chatId);
        }
    }
}