package mitrofanov.resolvers.impl;

import mitrofanov.keyboards.BadalkaButtonKeyboard;
import mitrofanov.model.entity.User;
import mitrofanov.model.repository.FermaRepository;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.service.EventService;
import mitrofanov.service.FermaService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;


public class BadalkaResolver implements CommandResolver {

    private final String COMMAND_NAME = "/badalka";
    private final BadalkaService badalkaService;
    private final EventService eventService;
    private final FermaService fermaService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    public BadalkaResolver() {
        this.badalkaService = BadalkaService.getInstance();
        this.eventService = new EventService();
        this.fermaService = new FermaService();

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        if (badalkaService.isBodalkaAvailable(chatId)) {
            if (text.startsWith("/badalka")) {
                if (badalkaService.hasNotListForThisUser(chatId)) {
                    badalkaService.setNewListUserForAttack(chatId);
                    badalkaService.setCurrIndexInUserForAttack(chatId);
                }

                int curIndex = badalkaService.getCurrIndexInUserForAttack(chatId);
                String userProfileForAttack = badalkaService.generateUserProfileForAttack(badalkaService.getUserForAttack(chatId, curIndex).
                        getChatId());

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
                    badalkaService.deleteCurrIndexInUserForAttackAndList(chatId);

                    setSessionStateForThisUser(chatId, State.IDLE);

                    sendMessage.setText("Сори, больше никого нет, иди на ферму");
                    sendMessage.setChatId(chatId);

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
            if (!badalkaService.isTimeLessThanCurrentAttack(chatId)) {
                setSessionStateForThisUser(chatId, State.IDLE);

                sendMessage.setText("Сражаться можно не чаще, чем раз в 5 минут! Следующая атака будет возможна через " + badalkaService.getTimeLastAttack(chatId));
                sendMessage.setChatId(chatId);

                Message message;
                try {
                    message = tg_bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                Integer messageId = message.getMessageId();
                scheduler.scheduleAtFixedRate(() -> {
                    String newText = "Сражаться можно не чаще, чем раз в 5 минут! Следующая атака будет возможна через " + badalkaService.getTimeLastAttack(chatId);
                    EditMessageText editMessage = new EditMessageText();
                    editMessage.setChatId(chatId);
                    editMessage.setMessageId(messageId);
                    editMessage.setText(newText);

                    try {
                        tg_bot.execute(editMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }, 0, 1, TimeUnit.SECONDS);

            }
            if (!fermaService.isRunOutTimeOfUser(chatId)) {
                setSessionStateForThisUser(chatId, State.IDLE);

                sendMessage.setText("Вы сейчас крутите хвосты коров на ферме и отвони противники разбежались. Вы будете вонять еще " + fermaService.getRemainingTime(chatId));
                sendMessage.setChatId(chatId);

                Message message;
                try {
                    message = tg_bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                Integer messageId = message.getMessageId();
                scheduler.scheduleAtFixedRate(() -> {
                    String newText = "Вы сейчас крутите хвосты коров на ферме и отвони противники разбежались. Вы будете вонять еще " + fermaService.getRemainingTime(chatId);
                    EditMessageText editMessage = new EditMessageText();
                    editMessage.setChatId(chatId);
                    editMessage.setMessageId(messageId);
                    editMessage.setText(newText);

                    try {
                        tg_bot.execute(editMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }, 0, 1, TimeUnit.SECONDS);

            }
        }
    }
}