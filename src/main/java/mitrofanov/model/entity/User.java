package mitrofanov.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    Long chatId;
    String nickname;
    String race;
    Long gold;
    int power;
    int agility;
    int mastery;
    int weight;
    Long fightingPower;
    LocalDateTime dateLastAtack;
    LocalDateTime dateLastGuard;
    LocalDateTime dateLastFarme;
    int globalCountFarmHours;

    public Long getFightingPower() {
        fightingPower = (long) (power * 2.1 + agility * 1.4 + mastery * 1.5 + weight * 1.7);
        return fightingPower;
    }
}
