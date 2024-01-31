package mitrofanov.service;

import mitrofanov.model.repository.FermaRepository;

import java.time.Duration;
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

    public String getRemainingTime(Long chatId) {
        LocalDateTime userTime = FermaRepository.getThisUserTime(chatId);
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.isBefore(userTime)) {
            Duration remainingTime = Duration.between(currentTime, userTime);
            long hours = remainingTime.toHours();
            long minutes = remainingTime.toMinutesPart();
            long seconds = remainingTime.toSecondsPart();

            return String.format("Осталось: %d часов, %d минут, %d секунд", hours, minutes, seconds);
        } else {
            return "Время уже истекло";
        }
    }

    public void updateUserDateLastFarm(Long chatId, LocalDateTime hours) {
        FermaRepository.updateUserTime(chatId, hours);
    }

    public String getUserTimeNow(Long chatId) {
        FermaRepository.getThisUserTime(chatId);
        return null;
    }

    public void addGoldForUserByFarm(Long chatId, Long i) {
        Long currentGold = FermaRepository.getGoldForUser(chatId);
        Long newGold = currentGold + i;
        FermaRepository.addGoldForUser(chatId, newGold);
    }
    public void  updateFarmHours(Long chatId, int farmHours) {
        int currentFarmHours = FermaRepository.getFarmHours(chatId, farmHours);
        int newFarmHours = currentFarmHours + farmHours;
        FermaRepository.setFarmHours(chatId, newFarmHours);
    }
}
