/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import gameserver.FXMLDocumentController;
import java.util.Vector;
import models.Message;
import socket.Clients;
import socket.Room;

/**
 *
 * @author Bossm
 */
public class log implements Room {

    private static Vector<Clients> publicConnections = new Vector<>();

    public static void attach(Clients c) {
     publicConnections.add(c);
        updateUI();    
    }

  /*  @Override
    public void attach(Clients c) {
        publicConnections.add(c);
        updateUI();
    }
*/
 
    public static void detach(Clients c) {
        publicConnections.remove(c);
        updateUI();
    }

    
    public static void notifyUpdate(Message m) {
        for (Clients c : publicConnections) {
            c.update(m);
        }
    }

 
    public static void updateUI() {
        FXMLDocumentController.setTotalPlayers(publicConnections.size());
    }

    
    public static void clearRoom() {
        publicConnections.clear();
    }

}
