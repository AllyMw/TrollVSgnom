package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.keyboards.DeleteHistoryButton;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.ProfileService;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.sql.SQLException;

import static mitrofanov.handlers.TelegramRequestHandler.setSessionStateForThisUser;


public class ProfileResolver implements CommandResolver {
    private final String COMMAND_NAME = "/profile";
    private final RegistrationService registrationService;
    private final ProfileService profileService;

    public ProfileResolver() {
        this.registrationService = new RegistrationService();
        this.profileService = new ProfileService();
    }


    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        registrationService.hasChatId(chatId);

        setSessionStateForThisUser(chatId, State.IDLE);

        String userProfile;

        try {
            userProfile = profileService.generateUserProfile(chatId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}