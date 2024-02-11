
import mitrofanov.model.entity.User;
import mitrofanov.model.repository.ProfileRepository;
import mitrofanov.service.BadalkaService;
import mitrofanov.service.ProfileService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    private BadalkaService badalkaService = new BadalkaService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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