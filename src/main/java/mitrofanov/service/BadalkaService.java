package mitrofanov.service;

import mitrofanov.model.entity.User;
import mitrofanov.model.repository.BadalkaRepository;
import mitrofanov.model.repository.UserRepository;

import java.util.*;

public class BadalkaService {
    private final BadalkaRepository badalkaRepository;
    private final UserRepository userRepository;
    private final Map<Long, List<User>> usersForAttack;
    private Map<Long, Integer> currIndexes;

    public BadalkaService() {
        this.badalkaRepository = new BadalkaRepository();
        this.userRepository = new UserRepository();
        this.usersForAttack = new HashMap<>();
        this.currIndexes = new HashMap<>();
    }

    public ArrayList<Long> fight(Long chatIdAttaker, Long chatIdDefender) {
        ArrayList<Long> arrayList = new ArrayList<>();
        User attaker = badalkaRepository.getUserByChatId(chatIdAttaker);
        User defender = badalkaRepository.getUserByChatId(chatIdDefender);
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

    public User getUserForAttack(Long chatId, int index) {
        return  usersForAttack.get(chatId).get(index);
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

    public int getCurrIndexInUserForAttack(Long chatId) {
        if (currIndexes.isEmpty()) {
            return 0;
        }
        return currIndexes.get(chatId);
    }

    public void setCurrIndexInUserForAttack(Long chatId) {
        if (currIndexes.containsKey(chatId)) {
            currIndexes.replace(chatId, currIndexes.get(chatId) + 1);
        } else {
            currIndexes.put(chatId, 0);
        }
    }

    public void deleteCurrIndexInUserForAttack(Long chatId) {
        currIndexes.remove(chatId);
    }

    public Map<Long, Long> changeGoldAfterFight(Long winnerChatId, Long wonnerChatId) {
        Map<Long, Long> table = new HashMap<>();
        Long goldForWin = (long) (userRepository.getGoldByChatId(winnerChatId)
                + 100 + 0.15 * userRepository.getGoldByChatId(wonnerChatId));
        userRepository.setGoldByChatId(winnerChatId, goldForWin);
        table.put(winnerChatId, goldForWin);

        Long goldForWon = (long) ((userRepository.getGoldByChatId(wonnerChatId)) * 0.85);
        userRepository.setGoldByChatId(wonnerChatId, goldForWon);
        table.put(wonnerChatId, goldForWon);
        return table;
    }
}


