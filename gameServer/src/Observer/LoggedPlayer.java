/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Observer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
  
    @Override
    public void setSocketInformation(SocketHandler clientConnectionData) {
        this.clientConnectionData = clientConnectionData;
    }
    @Override
    public void setLoggedPlayerName(String username) {
        this.username = username;
    }
    
    @Override
    public String getLoggedPlayerName() {
      return username;
    }
    @Override
    public SocketHandler getSocketInformation() {
        return clientConnectionData;

    @Override
    public void update(Message m) {
        try {
            clientConnectionData.getClientOOS().writeObject(m);
        } catch (IOException ex) {
            Logger.getLogger(LoggedPlayer.class.getName()).log(Level.SEVERE, null, ex);
            //we should delete this user
        }
    }

    @Override
    public void update(Message m) {
        try {
            clientConnectionData.getClientOOS().writeObject(m);
        } catch (IOException ex) {
            Logger.getLogger(LoggedPlayer.class.getName()).log(Level.SEVERE, null, ex);
            //we should delete this user
        }
    }

    @Override
    public void updateUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    public void Clients(SocketHandler clientConnectionData) {
        this.clientConnectionData = clientConnectionData;
    }

}
