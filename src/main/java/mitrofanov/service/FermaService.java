package mitrofanov.service;

import mitrofanov.model.repository.FermaRepository;


import java.time.LocalDateTime;

public class FermaService {

    public boolean isRunOutTimeOfUser(Long chatId) {
        LocalDateTime userTime = FermaRepository.getThisUserTime(chatId); //время из табл + сколько-то часов
        if (LocalDateTime.now().isBefore(userTime)) {
            return false;  //время еще не кончилось делать ничего нельзя
        } else  {
            return true; //можно выполнять дальнейшие действия
        }
    }


    public void updateUserDateLastFarm(Long chatId, LocalDateTime hours) {
        FermaRepository.updateUserTime(chatId, hours);
    }

    public void addGoldForUserByFarm(Long chatId, Long i) {
        Long currentGold = FermaRepository.getGoldForUser(chatId);
        Long newGold = currentGold + i;
        FermaRepository.addGoldForUser(chatId, newGold);
    }
}