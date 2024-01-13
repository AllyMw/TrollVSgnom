package mitrofanov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@AllArgsConstructor
@Data
public class Event {
    int id;
    String nicknameWin;
    String nicknameLose;
    Date dateEvent;
    int goldForWin;
}
