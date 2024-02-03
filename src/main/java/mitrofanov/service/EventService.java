package mitrofanov.service;

import mitrofanov.model.entity.BadalkaEvent;
import mitrofanov.model.entity.FermaEvent;
import mitrofanov.model.repository.EventRepository;

import java.time.LocalDate;
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
        BadalkaEvent badalkaEvent = BadalkaEvent.builder().build();
        Long chatIdWinner = winner.get(0);
        Long chatIdLoser = winner.get(1);
        badalkaEvent.setChatIdWinner(chatIdWinner);
        badalkaEvent.setChatIdLoser(chatIdLoser);
        badalkaEvent.setNickNameWinner(userService.getNickNameByChatId(chatIdWinner));
        badalkaEvent.setNickNameLoser(userService.getNickNameByChatId(chatIdLoser));
        badalkaEvent.setChangeGold(changeGold.get(chatIdWinner));
        badalkaEvent.setDateBadalkaEvent(LocalDate.now());
        eventRepository.addNewBadalkaEvent(badalkaEvent);
    }
}

