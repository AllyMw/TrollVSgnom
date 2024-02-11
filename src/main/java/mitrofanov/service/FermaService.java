package mitrofanov.service;

import mitrofanov.model.repository.FermaRepository;

import java.time.Duration;
import java.time.LocalDateTime;

public class FermaService {
    private final FermaRepository fermaRepository;

    public FermaService() {
        this.fermaRepository = new FermaRepository();
    }

    public boolean isRunOutTimeOfUser(Long chatId) {
        LocalDateTime userTime = fermaRepository.getThisUserTime(chatId);

        if (LocalDateTime.now().isBefore(userTime)) {
            return false;
        } else  {
           return true;
        }
    }

    public String getRemainingTime(Long chatId) {
        LocalDateTime userTime = fermaRepository.getThisUserTime(chatId);

        if (LocalDateTime.now().isBefore(userTime)) {
            Duration remainingTime = Duration.between(LocalDateTime.now(), userTime);
            long hours = remainingTime.toHours();
            long minutes = remainingTime.toMinutesPart();
            long seconds = remainingTime.toSecondsPart();

            return String.format(" %d : %d : %d ", hours, minutes, seconds);
        } else {
            return "Время уже истекло";
        }
    }

    public void updateUserDateLastFarm(Long chatId, LocalDateTime hours) {
        fermaRepository.updateUserTime(chatId, hours);
    }
    public void addGoldForUserByFarm(Long chatId, Long i) {
        Long currentGold = fermaRepository.getGoldForUser(chatId);
        Long newGold = currentGold + i;
        fermaRepository.addGoldForUser(chatId, newGold);
    }
    public void  updateFarmHours(Long chatId, int farmHours) {
        int currentFarmHours = FermaRepository.getFarmHours(chatId);
        int newFarmHours = currentFarmHours + farmHours;
        FermaRepository.setFarmHours(chatId, newFarmHours);
    }
    public Long getCountGoldForFermByChatId(int hour, Long chatId) {
        Long result = (long) ((hour * 120 )+ (FermaRepository.getFarmHours(chatId) * 1000));
        return result;
    }
    public Long getGoldAfterByFerma(Long chatId) {
        Long result = fermaRepository.getGoldForUser(chatId);
        return result;
    }

}