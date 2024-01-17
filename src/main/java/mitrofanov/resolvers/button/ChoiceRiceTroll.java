package mitrofanov.resolvers.button;

import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.RegistrationService;
import mitrofanov.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class ChoiceRiceTroll implements CommandResolver {
    private final String COMMAND_NAME = "/choiceRiceTroll";
    private final RegistrationService registrationService;
    public ChoiceRiceTroll() {
        this.registrationService = new RegistrationService();

    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        registrationService.setRace("Troll", chatId);
        TelegramBotUtils.sendMessage(tg_bot, "Вы успешно зарегистрировались! Вам дано 100 золота на тренировку", chatId);


    }
}
