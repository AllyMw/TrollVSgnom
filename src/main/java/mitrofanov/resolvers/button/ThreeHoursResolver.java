package mitrofanov.resolvers.button;

import lombok.SneakyThrows;
import mitrofanov.model.repository.FermaRepository;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.FermaService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;

public class ThreeHoursResolver implements CommandResolver {
   private final FermaService fermaService;
    private final String COMMAND_NAME = "/threeHours";

    public ThreeHoursResolver() {
        fermaService = new FermaService();
    }

    @SneakyThrows
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Вы ушли на ферму на 3 часа");
        sendMessage.setChatId(chatId);
        tg_bot.execute(sendMessage);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime hours = currentTime.plusHours(3);

        fermaService.updateUserDateLastFarm(chatId, hours);

        fermaService.addGoldForUserByFarm(chatId, 3);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}