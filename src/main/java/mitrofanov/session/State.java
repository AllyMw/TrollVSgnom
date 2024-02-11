package mitrofanov.session;

import lombok.Getter;

@Getter
public enum State {
    IDLE("/idle"),
    START("/start"),
    TRAINING("/training"),
    BADALKA("/badalka"),
    FARM("/farm");

    private String value;

    State(String value) {
        this.value = value;
    }
}
