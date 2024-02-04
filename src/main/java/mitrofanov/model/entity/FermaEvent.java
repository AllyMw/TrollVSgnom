package mitrofanov.model.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class FermaEvent {
    Long chatid;
    Long gold;
    LocalDateTime dateEvent;

    @Override
    public String toString() {
        return "За поход на ферму " + dateEvent.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "вы получили: " + gold + " золота";
    }

}
