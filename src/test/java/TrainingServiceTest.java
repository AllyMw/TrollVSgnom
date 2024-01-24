import mitrofanov.model.repository.TrainingRepository;
import mitrofanov.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceTest {
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingService = new TrainingService();
    }

    @Test
    void testCountCost() {
        // Arrange
        Long chatId = 123L;
        int power = 5;
        int agility = 4;
        int mastery = 3;
        int weight = 2;
        when(trainingRepository.getPower(chatId)).thenReturn(power);
        when(trainingRepository.getAgility(chatId)).thenReturn(agility);
        when(trainingRepository.getMastery(chatId)).thenReturn(mastery);
        when(trainingRepository.getWeight(chatId)).thenReturn(weight);

        // Act
        HashMap<String, Long> result = trainingService.countCost(chatId);

        // Assert
        assertEquals((long) ((power * 2.1) * 1.1), result.get("power"));
        assertEquals((long) ((agility * 1.4) * 1.1), result.get("agility"));
        assertEquals((long) ((mastery * 1.5) * 1.1), result.get("mastery"));
        assertEquals((long) ((weight * 1.7) * 1.1), result.get("weight"));
    }

    @Test
    void testEnoughGoldForTraining_EnoughGold() {
        // Arrange
        Long currCost = 100L;
        Long chatId = 123L;
        Long haveGold = 200L;
        when(trainingRepository.getGoldByChatId(chatId)).thenReturn(haveGold);

        // Act
        boolean result = trainingService.enoughGoldForTraining(currCost, chatId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testEnoughGoldForTraining_NotEnoughGold() {
        // Arrange
        Long currCost = 100L;
        Long chatId = 123L;
        Long haveGold = 50L;
        when(trainingRepository.getGoldByChatId(chatId)).thenReturn(haveGold);

        // Act
        boolean result = trainingService.enoughGoldForTraining(currCost, chatId);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDecreaseGold() {
        // Arrange
        Long chatId = 123L;
        Long gold = 50L;

        // Act
        trainingService.decreaseGold(chatId, gold);

        // Assert
        verify(trainingRepository, times(1)).decreaseGoldByChatId(chatId, gold);
    }
}