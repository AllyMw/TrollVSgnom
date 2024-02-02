package mitrofanov.service;

import mitrofanov.model.repository.TrainingRepository;

import java.util.HashMap;

public class TrainingService {
    TrainingRepository trainingRepository;

    public TrainingService() {
        this.trainingRepository = new TrainingRepository();
    }

    public HashMap<String, Long> countCost(Long chatId) {

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

    public int setNewPower(Long chatId) {
        trainingRepository.setNewPower(chatId);
        int newPower = trainingRepository.getPower(chatId);
        return newPower;
    }

    public int setNewAgility(Long chatId) {
        trainingRepository.setNewAgility(chatId);
        int newAgility = trainingRepository.getAgility(chatId);
        return newAgility;
    }

    public int setNewMastery(Long chatId) {
        trainingRepository.setNewMastery(chatId);
        int newMastery = trainingRepository.getMastery(chatId);
        return newMastery;
    }

    public int setNewWeight(Long chatId) {
        trainingRepository.setNewWeight(chatId);
        int newWeight = trainingRepository.getWeight(chatId);
        return newWeight;
    }

    public boolean enoughGoldForTraining(Long currCost, Long chatId) {
        Long haveGold = trainingRepository.getGoldByChatId(chatId);
        return currCost <= haveGold;
    }

    public void decreaseGold(Long chatID, Long gold) {
        trainingRepository.decreaseGoldByChatId(chatID, gold);
    }

    public int setNewGold(Long chatId) {
        trainingRepository.getGoldByChatId(chatId);
        int newGold = Math.toIntExact(trainingRepository.getGoldByChatId(chatId));
        return newGold;
    }
}
