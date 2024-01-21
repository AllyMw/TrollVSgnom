package mitrofanov.service;

import mitrofanov.model.entity.User;
import mitrofanov.model.repository.ProfileRepository;

import java.sql.SQLException;

public class ProfileService {
private final ProfileRepository profileRepository;
    public ProfileService() {
        this.profileRepository = new ProfileRepository();
    }
    public String generateUserProfile(Long chatId) throws SQLException {

        User user = profileRepository.getUserProfile(chatId);
        String profile =
                "------------------------------\n" +
                        "| Профиль персонажа:\n" +
                        "------------------------------\n" +
                        "| Никнейм: " + user.getNickname() + "\n" +
                        "| Раса: " + user.getRace() + "\n" +
                        "| Золото: " + user.getGold().toString() + "\n" +
                        "| Сила: " + Integer.toString(user.getPower()) + "\n" +
                        "| Ловкость: " + Integer.toString(user.getAgility()) + "\n" +
                        "| Мастерство: " + Integer.toString(user.getMastery()) + "\n" +
                        "| Вес: " + Integer.toString(user.getWeight()) + "\n" +
                        "| Боевая сила: " + Long.toString(user.getFightingPower()) + "\n" +
                        "------------------------------\n";
        return profile;
    }
}