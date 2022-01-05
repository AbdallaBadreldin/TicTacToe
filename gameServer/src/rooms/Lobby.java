/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import Observer.LoggedPlayer;
import gameserver.FXMLDocumentController;
import static gameserver.FXMLDocumentController.fx;
import java.util.List;
import java.util.Vector;
import models.Message;
import socket.Clients;
import socket.Room;
import socket.SocketHandler;

/**
 *
 * @author Bossm
 */
public class Lobby implements Room {

    private static List<LoggedPlayer> publicConnections = new Vector<>();
    
    private static List<String> connectedPlayersUserNames = new Vector<>();
    private static List<Integer> connectedPlayersStatus = new Vector<>();

    @Override
    public void attach(LoggedPlayer c) {
        System.out.println("attach called");
     publicConnections.add(c);
     connectedPlayersUserNames.add(c.getLoggedPlayerName());
     connectedPlayersStatus.add(1);
        updateUsersListOfClients();    
    }

 
    @Override
    public void detach(LoggedPlayer c) {
        int i =publicConnections.indexOf(c);
        System.out.println("the deattach"+i);
        if(i==-1){
        //failed to find this client hrere it must throw exceptrion
        }
        else{
        publicConnections.remove(i);
        connectedPlayersUserNames.remove(i);
        connectedPlayersStatus.remove(i);
        
        }  updateUsersListOfClients();  }
    
    @Override
    public void notifyAllChat(Message m) {
        for (LoggedPlayer c : publicConnections) {
            c.updateChat(m);
        }
    }

 
    public void updateUI() {
        int i =publicConnections.size();
        System.out.println(i);
        fx.updateTotalPlayers(i);
    }

    
    //update online players list====================
    
    public void clearRoom() {
        publicConnections.clear();
    }

    private void updateUsersListOfClients() {
        
       updateUI();
       notifyUpdateList(connectedPlayersUserNames,connectedPlayersStatus);
 
    }

 

    @Override
    public void notifyUpdateList(List<String> listOfUsers, List<Integer> statusOfUsers) {
     for (LoggedPlayer c : publicConnections) {
            c.updatePlayersList(listOfUsers , statusOfUsers);
        }   
    
    }



   


}
