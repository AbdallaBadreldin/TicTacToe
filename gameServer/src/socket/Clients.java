/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.Socket;
import java.util.List;
import java.util.Vector;
import models.Message;

/**
 *
 * @author Bossm
 */
public interface Clients {
    //  protected static Vector<Clients> playersConnected = new Vector<Clients>();
    // protected static Vector<Clients> gamesOnline = new Vector<Clients>();

    //protected static Vector<Clients> playersConnected = new Vector<Clients>();
    // protected Socket clientSocket;
    // protected String toekn;
    // protected String username;
    
    public abstract void updateChat(Message m);

    public abstract void updatePlayersList( List<String> l ,List<Integer> i);

    public abstract void updateUI();

    public abstract SocketHandler getSocketInformation();
    
    
// public Clients getClient (int ID){
    /*
       
       return playersConnected.get(ID);
   }
   public void addClient (Clients client){
    playersConnected.add(client);
   }
   public void removeClientByID(int ID){
   playersConnected.remove(ID);
   }
     */

}
