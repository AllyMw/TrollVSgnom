package mitrofanov.service;

import mitrofanov.model.entity.User;
import mitrofanov.model.repository.RegistrationRepository;
import java.time.LocalDateTime;
import java.time.LocalDate;


public class RegistrationService {
    RegistrationRepository registrationRepository;

    public RegistrationService() {
        registrationRepository = new RegistrationRepository();
    }

    public void addNewPlayer(Long chatId) {

        User newUser = User.builder().chatId(chatId).gold(100L).power(5).mastery(5)
                .agility(5).weight(5)
                .dateLastAtack(LocalDate.of(1999,6, 6))
                .dateLastGuard(LocalDate.of(1999,6, 6))
                .dateLastFarme(LocalDate.of(1999,6, 6))
                .build();

        newUser.setFightingPower(newUser.getFightingPower());
        registrationRepository.addUser(newUser);
    }
    public void setNickName(String nickName, Long chatId) {
        registrationRepository.setNickNamebyChatId(nickName, chatId);
    }
    public boolean isNicknameIsFree(String nickName) {
        return registrationRepository.isNicknameExists(nickName);
    }
    public void setRace(String race, Long chatId) {
        registrationRepository.setRaceByChatId(race, chatId);
    }
    public boolean hasChatId(Long chatId) {
        return registrationRepository.hasChatId(chatId);
    }

    public boolean nickNameNotNull(Long chatId) {return registrationRepository.nicknameIsNull(chatId);}
    //true если null
    public boolean raceNotNull(Long chatId) {return registrationRepository.raceIsNull(chatId);}
    //true если null

}
