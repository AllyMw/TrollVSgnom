package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.ProfileService;
import mitrofanov.service.RegistrationService;
import mitrofanov.session.State;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


import static mitrofanov.resolvers.impl.StartNicknameResolver.setSessionStateForThisUser;


public class ProfileResolver implements CommandResolver {
    private final String COMMAND_NAME = "/profile";
    private final RegistrationService registrationService;
    private final ProfileService profileService;

    public ProfileResolver() {
        this.registrationService = new RegistrationService();
        this.profileService = new ProfileService();
    }

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId)  {
        registrationService.hasChatId(chatId);
        setSessionStateForThisUser(chatId, State.IDLE);
        String userProfile = profileService.generateUserProfile(chatId);
        TelegramBotUtils.sendMessage(tg_bot, userProfile, chatId);


    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}