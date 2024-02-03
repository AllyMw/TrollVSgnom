package mitrofanov.model.entity;

import lombok.*;

import java.time.LocalDate;
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
    LocalDate dateBadalkaEvent;

    @Override
    public String toString() {
        return "Игрок" + nickNameWinner + " победил игрока "
                + nickNameLoser + ", победитель дополнительно отнял у проигравшего " + (changeGold - 100L) + " Дата: " + dateBadalkaEvent + "\n";
    }
}
