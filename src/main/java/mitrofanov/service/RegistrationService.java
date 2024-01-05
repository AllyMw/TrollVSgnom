package mitrofanov.service;

import lombok.Builder;
import mitrofanov.entity.User;
import mitrofanov.repository.RegistrationRepository;

public class RegistrationService {
    public void addNewPlayer(Long chatId) {
        RegistrationRepository registrationRepository = new RegistrationRepository();
        User newUser = User.builder().chatId(chatId).gold(100L).power(5).mastery(5).agility(5).weight(5).build();
        newUser.setFightingPower(newUser.getFightingPower());
        registrationRepository.addUser(newUser);
    }
    public void changeNickName(String nickName, Long chatId) {
        RegistrationRepository registrationRepository = new RegistrationRepository();
        registrationRepository.setNickNamebyChatId(nickName, chatId);
    }
    public void changeRace(String race, Long chatId) {
        RegistrationRepository registrationRepository = new RegistrationRepository();
        registrationRepository.setRaceByChatId(race, chatId);
    }
}
