package mitrofanov.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    static SessionManager instance = null;
    private Map<Long, Session> userToSession;

    public SessionManager() {
        this.userToSession = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }


    public void createSession(Long chatID) {
        if(userToSession.containsKey(chatID)) return;
        userToSession.put(chatID,
                Session.builder()
                        .sessionID(chatID)
                        .state(State.IDLE)
                        .build());
    }

    public Session getSession(Long chatID) {
        return userToSession.get(chatID);
    }
}
