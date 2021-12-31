/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Bossm
 */
public abstract class Clients {
    protected static Vector<Clients> playersConnected = new Vector<Clients>();
    protected static Vector<Clients> gamesOnline = new Vector<Clients>();
    
    //protected static Vector<Clients> playersConnected = new Vector<Clients>();
    protected Socket clientSocket;
    protected String toekn;
    protected String username;
    
    public abstract void update();
   public abstract void updateUI();
   public Clients getClient (int ID){
   return playersConnected.get(ID);
   }
   public void addClient (Clients client){
    playersConnected.add(client);
   }
   public void removeClientByID(int ID){
   playersConnected.remove(ID);
   }
 

}
