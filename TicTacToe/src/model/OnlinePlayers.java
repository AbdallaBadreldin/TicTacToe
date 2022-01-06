package model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Abdo
 */
public class OnlinePlayers implements Serializable{
    
    private static final long serialVersionUID = 6529685098267723070L;

    private List<Player> players;

    public OnlinePlayers() {
        
    }

    public OnlinePlayers(List<Player> players) {
        this.players = players;
    }
         

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
}
