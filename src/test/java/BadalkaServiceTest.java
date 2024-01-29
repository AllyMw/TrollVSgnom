import mitrofanov.model.entity.User;
import mitrofanov.model.repository.BadalkaRepository;
import mitrofanov.model.repository.UserRepository;
import mitrofanov.service.BadalkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BadalkaServiceTest {
    private BadalkaRepository badalkaRepository;
    private UserRepository userRepository;
    private BadalkaService badalkaService;

    @BeforeEach
    public void setup() {
        badalkaRepository = new BadalkaRepository();
        userRepository = Mockito.mock(UserRepository.class);
        badalkaService = BadalkaService.getInstance();
    }

    @Test
    public void testFight() {
        User userAttacker = badalkaRepository.getUserByChatId(1232314345L);
        User userDeffender = badalkaRepository.getUserByChatId(1270883725L);

        assertEquals(userAttacker.getPower(), 500);
        assertEquals(userAttacker.getAgility(), 500);
        assertEquals(userAttacker.getMastery(), 500);
        assertEquals(userAttacker.getWeight(), 500);

        assertEquals(userDeffender.getPower(), 101);
        assertEquals(userDeffender.getAgility(), 106);
        assertEquals(userDeffender.getMastery(), 101);
        assertEquals(userDeffender.getWeight(), 100);


        ArrayList<Long> result = badalkaService.fight(1232314345L, 1270883725L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(result.get(0),1232314345L);

    }

    @Test
    public void testSetNewListUserForAttack() {
        List<User> users;
        badalkaService.setNewListUserForAttack(1232314345L);
        users = badalkaRepository.getListUserForAttack(1232314345L);
        assertEquals(users.size(), 2);
    }
    @Test
    void testHasNotListForThisUser() {
        Long chatId = 1L;
        assertTrue(badalkaService.hasNotListForThisUser(chatId));
    }

    @Test
    void testGenerateUserProfileForAttack() {
        Long chatId = 1L;
        String expectedProfile = "------------------------------\n" +
                "------------------------------\n" +
                "| Никнейм: test\n" +
                "| Сила: 10\n" +
                "| Ловкость: 10\n" +
                "| Мастерство: 10\n" +
                "| Вес: 10\n" +
                "| Боевая сила: 67\n" +
                "------------------------------\n";
        assertEquals(expectedProfile, badalkaService.generateUserProfileForAttack(chatId));
    }


}

