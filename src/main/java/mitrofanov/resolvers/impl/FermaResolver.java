package mitrofanov.resolvers.impl;


import lombok.SneakyThrows;
import mitrofanov.keyboards.FermaKeyboard;
import mitrofanov.model.repository.FermaRepository;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.EventService;
import mitrofanov.service.FermaService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;


public class FermaResolver implements CommandResolver {
    private final String COMMAND_NAME = "/farm";
    private final FermaService fermaService;
    private final EventService eventService;

    public FermaResolver() {
        this.fermaService =  new FermaService();
        this.eventService = new EventService();
    }


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        if (text.startsWith("/farm")) {
            if (!fermaService.isRunOutTimeOfUser(chatId)) {

//TelegramBotUtils.sendMessage(tg_bot, "Вы уже находитесь на ферме. \nПовторное посещение будет доступно после окончания времени.\n" + fermaService.getRemainingTime(chatId), chatId);
                sendMessage.setText("Вы уже находитесь на ферме. \nПовторное посещение будет доступно после окончания времени.\n" + fermaService.getRemainingTime(chatId));
                sendMessage.setChatId(chatId);

                Message message = tg_bot.execute(sendMessage);
                Integer messageId = message.getMessageId();

                scheduler.scheduleAtFixedRate(() -> {
                    String newText = "Вы уже находитесь на ферме. \nПовторное посещение будет доступно после окончания времени.\n" + fermaService.getRemainingTime(chatId);
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
                setSessionStateForThisUser(chatId, State.IDLE);
            } else if (fermaService.isRunOutTimeOfUser(chatId)) {
                sendMessage.setText("Выберите на сколько часов отправитесь на ферму");
                sendMessage.setReplyMarkup(FermaKeyboard.hoursKeyboard());
                sendMessage.setChatId(chatId);
                tg_bot.execute(sendMessage);
            }

        } if (text.startsWith("/threeHours")) {
            if (!fermaService.isRunOutTimeOfUser(chatId)) {

                setSessionStateForThisUser(chatId, State.IDLE);
                return;
            }
            Long goldForFerm = fermaService.getCountGoldForFermByChatId(3, chatId);
            fermaService.addGoldForUserByFarm(chatId,  goldForFerm);
            sendMessage.setText("Вы ушли на ферму на 3 часа.\n Теперь у вас "+ fermaService.getGoldAfterByFerma(chatId) + " золота");
            sendMessage.setChatId(chatId);
            tg_bot.execute(sendMessage);

            LocalDateTime hours = LocalDateTime.now().plusHours(3);

            eventService.addNewFermaEvent(chatId, goldForFerm, hours);

            fermaService.updateUserDateLastFarm(chatId, hours);

            fermaService.updateFarmHours(chatId, 3);

            setSessionStateForThisUser(chatId, State.IDLE);
        }

        else if (text.startsWith("/sixHours")) {
            if (!fermaService.isRunOutTimeOfUser(chatId)) {

                setSessionStateForThisUser(chatId, State.IDLE);
                return;
            }
            Long goldForFerm = fermaService.getCountGoldForFermByChatId(6, chatId);
            fermaService.addGoldForUserByFarm(chatId,  goldForFerm);
            sendMessage.setText("Вы ушли на ферму на 6 часа.\n Теперь у вас "+ fermaService.getGoldAfterByFerma(chatId) + " золота");
            sendMessage.setChatId(chatId);
            tg_bot.execute(sendMessage);

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime hours = currentTime.plusHours(6);

            fermaService.updateUserDateLastFarm(chatId, hours);
            eventService.addNewFermaEvent(chatId, goldForFerm, hours);

            fermaService.updateFarmHours(chatId, 6);

            setSessionStateForThisUser(chatId, State.IDLE);

        } else if (text.startsWith("/twelveHours")) {
            if (!fermaService.isRunOutTimeOfUser(chatId)) {
                setSessionStateForThisUser(chatId, State.IDLE);
                return;
            }
            Long goldForFerm = fermaService.getCountGoldForFermByChatId(12, chatId);
            fermaService.addGoldForUserByFarm(chatId,  goldForFerm);
            sendMessage.setText("(\"Вы ушли на ферму на 12 часа.\n Теперь у вас "+ fermaService.getGoldAfterByFerma(chatId) + " золота");
            sendMessage.setChatId(chatId);
            tg_bot.execute(sendMessage);

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime hours = currentTime.plusHours(12);
            eventService.addNewFermaEvent(chatId, goldForFerm, hours);
            fermaService.updateUserDateLastFarm(chatId, hours);

            fermaService.updateFarmHours(chatId, 12);

            setSessionStateForThisUser(chatId, State.IDLE);


        } else if (text.startsWith("/leaveFarm")) {
            TelegramBotUtils.sendMessage(tg_bot,"Вы ушли с фермы", chatId);

            setSessionStateForThisUser(chatId, State.IDLE);
        }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}