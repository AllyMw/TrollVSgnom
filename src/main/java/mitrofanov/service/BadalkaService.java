package mitrofanov.service;

import mitrofanov.model.entity.User;
import mitrofanov.model.repository.BadalkaRepository;

import java.util.*;

public class BadalkaService {
    private final BadalkaRepository badalkaRepository;
    public Map<Long, List<User>> usersForAttack;

    public BadalkaService() {

        this.badalkaRepository = new BadalkaRepository();
        this.usersForAttack = new HashMap<>();
    }
    public ArrayList<Long> fight(Long chatIdPlayer1, Long chatIdPlayer2) {
        ArrayList<Long> arrayList = new ArrayList<>();
        User attaker = badalkaRepository.getUserByChatId(chatIdPlayer1);
        User defender = badalkaRepository.getUserByChatId(chatIdPlayer2);
        while (attaker.getWeight() > 0 && defender.getWeight() > 0) {
            var accuracy = (attaker.getAgility() * (1 + (attaker.getMastery() - defender.getMastery()) / 100)) / (defender.getMastery() * (1 + (defender.getAgility() - attaker.getAgility()) / 100));
            if (Math.random() < accuracy) {
                defender.setWeight(defender.getWeight() - attaker.getPower());
            }
            var temp = attaker;
            attaker = defender;
            defender = temp;
        }
        arrayList.add(1, attaker.getChatId());
        arrayList.add(0, defender.getChatId());
        return arrayList;
    }

    public void setNewListUserForAttack(Long chatId) {
        List<User> userList = badalkaRepository.getListUserForAttack(chatId);
        usersForAttack.put(chatId, userList);
    }
    public User getUserForAttack(Long chatId) {
        List<User> userList = usersForAttack.get(chatId);
        return userList.get(0);
    }

    public boolean hasNotListForThisUser(Long chatId) {
        return !usersForAttack.containsKey(chatId);
    }

    public String generateUserProfileForAttack(Long chatId) {
        User user = badalkaRepository.getUserByChatId(chatId);
        String profileForAttack =
                "------------------------------\n" +
                        "------------------------------\n" +
                        "| Никнейм: " + user.getNickname() + "\n" +
                        "| Сила: " + Integer.toString(user.getPower()) + "\n" +
                        "| Ловкость: " + Integer.toString(user.getAgility()) + "\n" +
                        "| Мастерство: " + Integer.toString(user.getMastery()) + "\n" +
                        "| Вес: " + Integer.toString(user.getWeight()) + "\n" +
                        "| Боевая сила: " + Long.toString(user.getFightingPower()) + "\n" +
                        "------------------------------\n";

        return profileForAttack;
    }
}


