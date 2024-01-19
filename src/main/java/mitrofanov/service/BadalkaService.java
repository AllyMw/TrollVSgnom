package mitrofanov.service;

import mitrofanov.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BadalkaService {
    public ArrayList<User> fight(User player1, User player2) {
        ArrayList<User> arrayList = new ArrayList<>();

        User attaker = player1;
        User defender = player2;

        while (attaker.getWeight() > 0 && defender.getWeight() > 0) {
            var accuracy = (attaker.getAgility() * (1 + (attaker.getMastery() - defender.getMastery())/100)) / (defender.getMastery() * (1 + (defender.getAgility() - attaker.getAgility())/100));



            // Проверяем попадание первого персонажа по второму
            if (Math.random() < accuracy) {
                    // Применяем урон к второму персонажу
                    defender.setWeight(defender.getWeight() - attaker.getPower());
            }
            var temp = attaker;
            attaker = defender;
            defender = temp;

        }

        // Определяем победителя
        if (attaker.getWeight() > 0) {
            arrayList.add(0, attaker);
            arrayList.add(1, defender);
            return arrayList;
        } else {
            arrayList.add(1, attaker);
            arrayList.add(0, defender);
            return arrayList;
        }
    }
}
