import mitrofanov.model.repository.TrainingRepository;
import mitrofanov.service.TrainingService;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    private TrainingRepository trainingRepository = new TrainingRepository();
    private TrainingService trainingService = new TrainingService();


    @Test
    void testCountCost() {
        // Arrange
        Long chatId = 123L;
        int power = 5;
        int agility = 4;
        int mastery = 3;
        int weight = 2;

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

        Long currCost = 100L;
        Long chatId = 123L;
        Long haveGold = 50L;
        when(trainingRepository.getGoldByChatId(chatId)).thenReturn(haveGold);


        boolean result = trainingService.enoughGoldForTraining(currCost, chatId);


        assertFalse(result);
    }

    @Test
    void testDecreaseGold() {

        Long chatId = 123L;
        Long gold = 50L;


        trainingService.decreaseGold(chatId, gold);


        verify(trainingRepository, times(1)).decreaseGoldByChatId(chatId, gold);
    }
}