package mitrofanov.commands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

import static mitrofanov.commands.Commands.values;

public class StartCommands {

    public static List<BotCommand> init() {
        List<BotCommand> botCommands = new ArrayList<>();

        for (Commands command : values()) {
            botCommands.add(new BotCommand(command.getCommand(), command.getDescription()));
        }

        return botCommands;
    }
}