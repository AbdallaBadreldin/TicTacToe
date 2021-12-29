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
    protected static Vector<Clients> clientsVector = new Vector<Clients>();
    protected Socket clientSocket;
    protected String toekn;
    protected String username;
    
    private final int ONLINE = 1;
    private final int OFF_ONLINE = 2;
    private final int BUSY = 3;
    
    public abstract void update();
   
   public Clients getClient (int ID){
   return clientsVector.get(ID);
   }
   public void addClient (Clients client){
    clientsVector.add(client);
   }
     public int getState() {
        return 0;
       // return state;
    }

    public void setState(int state) {
        //this.state = state;
       // notifyAllObservers();
    }
}
