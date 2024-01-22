package mitrofanov.session;

import lombok.Getter;

@Getter
public enum State {
    IDLE("/idle"),
    START("/start"),
    BUTTON("/button"),
    START_NICKNAME("/start_nickname"),
    START_RACE("/start_race"),
    PROFILE("/profile"),
    TRAINING("/training"),
    BADALKA("/badalka"),
    FARM("/farm"),

    CHOICE_RICE_TROLL("/choiceRiceTroll"),
    CHOICE_RICE_GNOME("/choiceRiceGnome");


    private String value;

    State(String value) {
        this.value = value;
    }
}
