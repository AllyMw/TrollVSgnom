package mitrofanov.handlers;

import lombok.SneakyThrows;
import mitrofanov.commands.StartCommands;
import mitrofanov.configuration.Configuration;
import mitrofanov.model.repository.StatusRepository;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.resolvers.impl.StartResolver;
import mitrofanov.service.RegistrationService;
import mitrofanov.service.TrainingService;
import mitrofanov.session.Session;
import mitrofanov.session.SessionManager;
import mitrofanov.session.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;

public class TelegramRequestHandler extends TelegramLongPollingBot {

    private final StatusRepository statusRepository;
    private final RegistrationService registrationService;
    private final TrainingService trainingService;

    public static Map<String, CommandResolver> resolvers = Configuration.resolvers;
    private final SessionManager sessionManager = SessionManager.getInstance();

    static {
        StartResolver startResolver = new StartResolver();
        resolvers.put(startResolver.getCommandName(), startResolver);

    }

    public TelegramRequestHandler() {
        statusRepository = new StatusRepository();
        registrationService = new RegistrationService();
        trainingService = new TrainingService();


    }

    public void init() throws TelegramApiException {
            this.execute(new SetMyCommands(StartCommands.init(), new BotCommandScopeDefault(), null));
        }

        @SneakyThrows
        @Override

        public void onUpdateReceived(Update update) {

            /* Обработка кнопок */
            if (update.hasCallbackQuery()) {
                var query = update.getCallbackQuery();
                String callData = query.getData();
                Long chatID = query.getMessage().getChatId();
                String username = update.getCallbackQuery().getFrom().getUserName();
                createSessionForThisUser(chatID);
                processCommand(callData, chatID, callData);

            }

            /* Обработка сообщений пользователя */
            if (update.hasMessage()) {
                var message = update.getMessage();
                var text = message.getText();
                var chatID = message.getChatId();
                if (message.hasText()) {
                    String username = message.getChat().getUserName();
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
        return "TrollVSgnom_bot";
    }

    @Override
    public String getBotToken() {
        return "6978497435:AAHJjcrb03lsitkS-MYuq6cPElDI5dPfOI8";
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
}
