/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Observer;

import gameserver.FXMLDocumentController;
import java.net.Socket;
import socket.Clients;


/**
 *
 * @author Bossm
 */
public class ClientInterface extends Clients{

   public ClientInterface(Socket subject){
      //this.subject = subject;
      //this.subject.addObserver(this);
   }

   @Override
   public void update() {
     // System.out.println( "Binary String: " + Integer.toBinaryString( subject.getState() ) ); 
   
   }

    @Override
    public void updateUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   //FXMLDocumentController.updateTotalPlayers(5);
    }
}
