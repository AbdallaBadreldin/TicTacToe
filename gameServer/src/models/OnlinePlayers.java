package models;

import java.io.Serializable;
import java.util.List;

/**
 * @author Abdo
 */
public class OnlinePlayers implements Serializable{
    //need to be modified  getters and setters
    
    public final int ONLINE = 1;
    public final int OFF_ONLINE = 2;
    public final int BUSY = 3;
    
    static private List<String> players;
    static private List<Integer> status;
    
    public OnlinePlayers(List<String> players,List<Integer> status) {  
    this.players=players;
    this.status=status;
    }
   public OnlinePlayers() {    }


    static public void addPlayer(String player,int state) {
        players.add(player);
        status.add(state);    }
    
    
    static public void removePlayer(String player) {
        status.remove(players.indexOf(player));
        players.remove(player);
            }
    
   static public void removePlayerById(int ID) {
        players.remove(ID);
        status.remove(ID);    }
    
    
   static public void getPlayerUserName(int ID) {
        players.get(ID);
            }
    
   static public void setPlayerUserName(int ID ,String userName) {
        players.add(ID,userName);
            }
    
   static public int getPlayerStatus(int ID) {
        return status.get(ID);    }
    
   static public void setPlayerStatue(int playerID,int statusNumber ) {
        status.set(playerID, statusNumber);
    }
    
   static public void setPlayerStatueByID(int ID,int statue){
    status.set(ID, statue);
    }
    static public void setPlayerStatue(String userName,int statue){
    status.set(players.indexOf(userName),statue);
    
    }
    
    
}
