package mitrofanov.service;

import mitrofanov.model.entity.BadalkaEvent;
import mitrofanov.model.entity.FermaEvent;
import mitrofanov.model.repository.EventRepository;
import org.glassfish.grizzly.http.util.TimeStamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService() {
        this.eventRepository = new EventRepository();
        this.userService = new UserService();
    }
    
    public String generateEventUser(Long chatId) {
        StringBuilder badalkaBilder = new StringBuilder();
        StringBuilder fermaBilder = new StringBuilder();

        ArrayList<BadalkaEvent> badalkaEvents = eventRepository.getBadalkaEventsByChatId(chatId);
        badalkaBilder.append("События бадалки: \n");
        ArrayList<FermaEvent> fermaEvents = eventRepository.getFermaEventsByChatId(chatId);

        for (BadalkaEvent badalkaEvent: badalkaEvents) {
            badalkaBilder.append(badalkaEvent);
        }
        for (FermaEvent fermaEvent: fermaEvents) {
            fermaBilder.append(fermaEvent);
        }

        return "События бадалки: " + "\n" + badalkaEvents.toString() + "\n" + "Походы на ферму: " + fermaBilder;
    }
    public void addNewBadalkaEvent(ArrayList<Long> winner, Map<Long, Long> changeGold) {


        Long chatIdWinner = winner.get(0);
        Long chatIdLoser = winner.get(1);

        BadalkaEvent badalkaEvent = BadalkaEvent.builder().build();
        badalkaEvent.setChatIdWinner(chatIdWinner);
        badalkaEvent.setChatIdLoser(chatIdLoser);
        badalkaEvent.setNickNameWinner(userService.getNickNameByChatId(chatIdWinner));
        badalkaEvent.setNickNameLoser(userService.getNickNameByChatId(chatIdLoser));
        badalkaEvent.setChangeGold(changeGold.get(chatIdWinner));
        badalkaEvent.setDateBadalkaEvent(LocalDateTime.now());

        eventRepository.addNewBadalkaEvent(badalkaEvent);
    }

    public void addNewFermaEvent(Long chatId, Long gold, LocalDateTime dateTime) {
        FermaEvent fermaEvent = FermaEvent.builder().build();
        fermaEvent.setDateEvent(dateTime);
        fermaEvent.setGold(gold);
        fermaEvent.setChatid(chatId);

        eventRepository.addNewFermEvent(fermaEvent);
    }

    public void deleteHistoryByChatId(Long chatId) {
        eventRepository.deleteHistoryByChatId(chatId);
    }
}

