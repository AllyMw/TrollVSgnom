package mitrofanov.session;

import lombok.Getter;

@Getter
public enum StateButton {
    CHOICE_RICE_TROLL("/choiceRiceTroll"),
    CHOICE_RICE_GNOME("/choiceRiceGnome");


    private String value;

    StateButton(String value) {
        this.value = value;
    }
}
