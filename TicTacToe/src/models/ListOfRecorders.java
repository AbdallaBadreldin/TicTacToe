
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdo
 */
public class ListOfRecorders {
    
    private List<GameSession> gameSession;

    public ListOfRecorders() {
        gameSession = new ArrayList<>();
    }

    public ListOfRecorders(List<GameSession> gameSession) {
        this.gameSession = gameSession;
    }

    public List<GameSession> getGameSession() {
        return gameSession;
    }

    public void setGameSession(List<GameSession> gameSession) {
        this.gameSession = gameSession;
    }
    
}
