package mitrofanov.resolvers.impl;

import lombok.SneakyThrows;
import mitrofanov.keyboards.BadalkaButtonKeyboard;
import mitrofanov.keyboards.TrainingKeyboard;
import mitrofanov.resolvers.CommandResolver;
import mitrofanov.service.BadalkaService;
import mitrofanov.service.TrainingService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TrainingResolver implements CommandResolver {

    private final String COMMAND_NAME = "/training";
        private final TrainingService trainingService;

        public  TrainingResolver() {
            this.trainingService = new TrainingService();

        }
        @Override
        public String getCommandName() {
            return COMMAND_NAME;
        }


        @SneakyThrows
        @Override
        public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
            TrainingKeyboard trainingKeyboard = new TrainingKeyboard();
            trainingKeyboard.trainingKeyboard(tg_bot, chatId);
        }
    }

