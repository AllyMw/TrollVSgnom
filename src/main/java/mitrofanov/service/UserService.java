package mitrofanov.service;

import mitrofanov.model.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public String getNickNameByChatId(Long chatId) {
        return userRepository.getNickNameByChatId(chatId);
    }
}
