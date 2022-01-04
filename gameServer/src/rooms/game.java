/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import Observer.LoggedPlayer;
import java.util.List;
import java.util.Vector;
import models.Message;
import socket.Clients;
import socket.Room;

/**
 *
 * @author Bossm
 */
public class game implements Room {
    private static Vector<Clients> gameSessions = new Vector<>();
    private String user1;
    private String user2;

    @Override
    public void attach(LoggedPlayer o) {
    //create new room
    
    }

    @Override
    public void detach(LoggedPlayer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyAllChat(Message m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyUpdateList(List<String> listOfUsers, List<Integer> statusOfUsers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   



  
    
}
