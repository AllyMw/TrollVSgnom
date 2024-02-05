package mitrofanov.model.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class BadalkaEvent {
    Long chatIdWinner;
    Long chatIdLoser;
    String nickNameWinner;
    String nickNameLoser;
    Long changeGold;
    LocalDateTime dateBadalkaEvent;

    @Override
    public String toString() {
        return   nickNameWinner + " победил  "
                + nickNameLoser + " и отнял у проигравшего " + (changeGold - 100L) + " Дата: " + dateBadalkaEvent.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "\n";
    }
}
