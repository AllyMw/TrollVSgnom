
import mitrofanov.model.repository.FermaRepository;
import mitrofanov.service.FermaService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FermaServiceTest {

    @Test
    void testIsRunOutTimeOfUser() {

        Long chatId = 123456789L;
        LocalDateTime userTime = LocalDateTime.now().plusHours(1);

        FermaRepository mockFermaRepository = mock(FermaRepository.class);

        when(mockFermaRepository.getThisUserTime(chatId)).thenReturn(userTime);
        mockFermaRepository.getThisUserTime(chatId);

        FermaService fermaService = new FermaService();

        boolean result = fermaService.isRunOutTimeOfUser(chatId);

        assertTrue(result);
    }
}