package mitrofanov.commands;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum Commands {
    START("/start", "запуск бота"),
    PROFILE("/profile", "Вывести свой профль"),
    BADALKA("/badalka", "Пойти в бадалку"),
    FARM("/farm", "Пойти на ферму");

    private final String command;
    private final String description;
}