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
    }

    @Override
    public void Clients(SocketHandler clientConnectionData) {
        this.clientConnectionData = clientConnectionData;
    }

}
