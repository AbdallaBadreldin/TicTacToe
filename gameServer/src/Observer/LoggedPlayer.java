/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Observer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;
import models.OnlinePlayers;

import socket.Clients;
import socket.SocketHandler;

/**
 *
 * @author Bossm
 */
public class LoggedPlayer implements Clients {

    private SocketHandler clientConnectionData;
private String username;
private int status;

public LoggedPlayer(){}

public LoggedPlayer(SocketHandler clientConnectionData){
    System.out.println("Logged player created");
this.clientConnectionData=clientConnectionData;
    
username=clientConnectionData.getPlayerUsername();
    System.out.println(username);
System.out.println(clientConnectionData.getPlayerUsername());
status=1; // make it onlien and offline and final 
}


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
  
    public void setLoggedPlayerName(String username) {
        this.username = username;
    }
    
    public String getLoggedPlayerName() {
      return username;
    }
    @Override
    public SocketHandler getSocketInformation() {
        return clientConnectionData;
    }
   

    
   /* 

        public void Clients(SocketHandler clientConnectionData) {
        this.clientConnectionData = clientConnectionData;
    }
*/
    
    @Override
    public void updateUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateChat(Message m) {
        try {
            clientConnectionData.getClientOOS().writeObject(m);
        } catch (IOException ex) {
            Logger.getLogger(LoggedPlayer.class.getName()).log(Level.SEVERE, null, ex);
            //we should delete this user
        }
    }

    @Override
    public void updatePlayersList(List<String> l, List<Integer> i) {
    OnlinePlayers m = new OnlinePlayers(l,i);
     try {
            clientConnectionData.getClientOOS().writeObject(m);
        } catch (IOException ex) {
            Logger.getLogger(LoggedPlayer.class.getName()).log(Level.SEVERE, null, ex);
            //we should delete this user
        }
    
    }

   

    }
