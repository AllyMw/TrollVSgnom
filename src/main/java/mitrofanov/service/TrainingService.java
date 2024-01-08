package mitrofanov.service;

import mitrofanov.repository.TrainingRepository;

import java.util.HashMap;

public class TrainingService {
    public HashMap<String, Long> countCost(Long chatId) {
        TrainingRepository trainingRepository = new TrainingRepository();
        HashMap<String, Long> res = new HashMap<>();
        int power = trainingRepository.getPower(chatId);
        int agility = trainingRepository.getAgility(chatId);
        int mastery = trainingRepository.getMastery(chatId);
        int weight = trainingRepository.getWeight(chatId);
        res.put("power", (long) ((power * 2.1) * 1.1));
        res.put("agility", (long) ((agility * 1.4) * 1.1));
        res.put("mastery", (long) ((mastery * 1.5) * 1.1));
        res.put("weight", (long) ((weight * 1.7) * 1.1));
        return res;
    }
    public void setNewPower(Long chatId) {
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.setNewPower(chatId);
    }
    public void setNewAgility(Long chatId) {
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.setNewAgility(chatId);
    }
    public void setNewMastery(Long chatId) {
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.setNewMastery(chatId);
    }
    public void setNewWeight(Long chatId) {
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.setNewWeight(chatId);
    }
    public boolean enoughGoldForTraining(Long currCost, Long chatId) {
        TrainingRepository trainingRepository = new TrainingRepository();
        Long haveGold = trainingRepository.getGoldByChatId(chatId);
        return currCost <= haveGold;
    }

    public void decreaseGold(Long chatID, Long gold) {
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.decreaseGoldByChatId(chatID, gold);
    }
}
