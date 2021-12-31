package models;

import java.io.Serializable;
import java.util.List;

/**
 * @author Abdo
 */
public class OnlinePlayers implements Serializable{
    //need to be modified  getters and setters
    private List<String> players;
    private List<Integer> status;
    public OnlinePlayers() {    }


    public void setPlayers(List<String> players) {
        this.players = players;
    }
    public void addPlayer(String player,int state) {
        players.add(player);
        status.add(state);    }
    
    
    public void getPlayerUserName(int ID) {
        players.get(ID);
            }
    
    public void setPlayerUserName(int ID ,String userName) {
        players.add(ID,userName);
            }
    
    
    public int getPlayerStatus(int ID) {
        return status.get(ID);    }
    
    public void setPlayerStatue(int playerID,int statusNumber ) {
        this.status.set(playerID, statusNumber);
    }
    
    
}
