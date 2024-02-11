package mitrofanov.handlers;

import lombok.SneakyThrows;
import mitrofanov.commands.StartCommands;
import mitrofanov.configuration.Configuration;
import mitrofanov.keyboards.TrainingKeyboard;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.resolvers.impl.StartResolver;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.Session;
import mitrofanov.session.SessionManager;
import mitrofanov.session.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;




public class TelegramRequestHandler extends TelegramLongPollingBot {
    private final RegistrationService registrationService;
    private final TrainingKeyboard trainingKeyboard;
    public static Map<String, CommandResolver> resolvers = Configuration.resolvers;
    private final SessionManager sessionManager = SessionManager.getInstance();

    static {
        StartResolver startResolver = new StartResolver();
        resolvers.put(startResolver.getCommandName(), startResolver);

    }
    public TelegramRequestHandler() {
        registrationService = new RegistrationService();
        trainingKeyboard = new TrainingKeyboard();
    }

    public void init() throws TelegramApiException {
            this.execute(new SetMyCommands(StartCommands.init(), new BotCommandScopeDefault(), null));
        }


        @Override
        public void onUpdateReceived(Update update) {


            if (update.hasCallbackQuery()) {
                var query = update.getCallbackQuery();
                String callData = query.getData();
                Long chatID = query.getMessage().getChatId();
                createSessionForThisUser(chatID);
                String resolverName = getResolverName(chatID);
                processCommand(callData, chatID, resolverName);

                try {
                    trainingKeyboard.updateTrainingKeyboard(this, chatID, update.getCallbackQuery().getMessage().getMessageId(), callData);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
            if (update.hasMessage()) {
                var message = update.getMessage();
                var text = message.getText();
                var chatID = message.getChatId();
                if (message.hasText()) {
                    createSessionForThisUser(chatID);

                    if (text.startsWith("/start") && !registrationService.hasChatId(chatID)) {
                        setSessionStateForThisUser(chatID, State.START);
                    }

                    String resolverName = getResolverName(chatID);
                    processCommand(text, chatID, resolverName);
                }
            }
    }

    @Override
    public String getBotUsername() {
        return mitrofanov.Configuration.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return mitrofanov.Configuration.BOT_TOKEN;
    }

    private void processCommand(String text, Long chatID, String resolverName) {
        CommandResolver commandResolver = resolvers.get(resolverName);
        commandResolver.resolveCommand(this, text, chatID);
    }

    private void createSessionForThisUser(Long chatID) {
        sessionManager.createSession(chatID);
    }

    private static String getResolverName(Long chatID) {
        Session session = SessionManager.getInstance().getSession(chatID);
        String resolverName = session.getState().getValue();
        return resolverName;
    }
    public static void setSessionStateForThisUser(Long chat_id, State state) {
        SessionManager.getInstance().getSession(chat_id).setState(state);
    }
}
