import mitrofanov.model.repository.TrainingRepository;
import mitrofanov.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainingServiсeTest {
    @Mock
    private TrainingRepository mockTrainingRepository;
    @Mock
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        trainingService = new TrainingService();
    }

    @Test
    void countCost() {
        Long chatId = 6521768543L;

        HashMap<String, Long> expected = new HashMap<>();
        expected.put("power", 11L);
        expected.put("agility", 7L);
        expected.put("mastery", 8L);
        expected.put("weight", 9L);

        when(mockTrainingRepository.getPower(anyLong())).thenReturn(5);
        when(mockTrainingRepository.getAgility(anyLong())).thenReturn(3);
        when(mockTrainingRepository.getMastery(anyLong())).thenReturn(4);
        when(mockTrainingRepository.getWeight(anyLong())).thenReturn(5);

        HashMap<String, Long> result = trainingService.countCost(chatId);

        assertEquals(expected, result);
    }

    @Test
    void setNewPower() {
        Long chatId = 6521768543L;

        when(mockTrainingRepository.getPower(anyLong())).thenReturn(5);

        int oldPower = mockTrainingRepository.getPower(chatId);
        int newPower = trainingService.setNewPower(chatId);

        assertEquals(oldPower, newPower);
    }
    @Test
    void setNewAgility() {
        Long chatId = 6521768543L;

        when(mockTrainingRepository.getPower(anyLong())).thenReturn(5);

        int oldPower = mockTrainingRepository.getPower(chatId);
        int newPower = trainingService.setNewPower(chatId);

        assertEquals(oldPower, newPower);
    }
    @Test
    void setNewMastery() {
        Long chatId = 6521768543L;

        when(mockTrainingRepository.getPower(anyLong())).thenReturn(5);

        int oldPower = mockTrainingRepository.getPower(chatId);
        int newPower = trainingService.setNewPower(chatId);

        assertEquals(oldPower, newPower);
    }
    @Test
    void setNewWeight() {
        Long chatId = 6521768543L;

        when(mockTrainingRepository.getPower(anyLong())).thenReturn(5);

        int oldPower = mockTrainingRepository.getPower(chatId);
        int newPower = trainingService.setNewPower(chatId);

        assertEquals(oldPower, newPower);
    }


    @Test
    void enoughGoldForTrainingNotEnoughGold() {
        Long chatId = 6521768543L;
        Long currCost = 100L;
        Long haveGold = 50L;

        when(mockTrainingRepository.getGoldByChatId(chatId)).thenReturn(haveGold);

        boolean isEnoughGold = trainingService.enoughGoldForTraining(currCost, chatId);

        assertFalse(isEnoughGold);
    }
}