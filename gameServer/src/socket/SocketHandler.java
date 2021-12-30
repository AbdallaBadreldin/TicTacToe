/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import Observer.LoginInterface;
import gameserver.FXMLDocumentController;
//import static gameserver.GameServer.serverSocket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GameRequest;
import models.Message;
import models.Player;
import models.PlayerMove;
//import gameserver.FXMLDocumentController;

/**
 *
 * @author Bossm
 */
public class SocketHandler extends Thread {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Object receivedObject;
    private static List<SocketHandler> publicConnections = new ArrayList<SocketHandler>();
    
    private static List<LoginInterface> RunningGames = new ArrayList<LoginInterface>();
    
    public SocketHandler(Socket socket) {
        try {
           
            this.socket = socket;
            oos = new ObjectOutputStream(socket.getOutputStream());  //wedon't wait data objects only
            ois = new ObjectInputStream(socket.getInputStream());

            addSocketHandler(this);
          
            start();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {
        while (true) {
            try {
                receivedObject = ois.readObject();

                if (receivedObject instanceof GameRequest) {
                    GameRequest game = (GameRequest) receivedObject;
                    System.out.println("Recieveid game request");
                    
                 
                }
                if (receivedObject instanceof PlayerMove) {
                    PlayerMove game = (PlayerMove) receivedObject;
                    System.out.println("Recieveid player move");
                }
                if (receivedObject instanceof Message) {
                    Message game = (Message) receivedObject;
                    sendMessageToAll(game);  
                    System.out.println("Recieveid Message");
                }
                if (receivedObject instanceof Player) {
                    Player game = (Player) receivedObject;
                    //sendMessageToAll(str);  
                    System.out.println("Recieveid Player data");
                } else {
                    //print error object is worng 
                }
                //sendMessageToAll(str);     /let's check what object we have
            } catch (IOException ex) {
                // Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   public void sendMessageToAll(Message msg) {
        for (SocketHandler ch : publicConnections) {
            try {
                ch.oos.writeObject((Object)msg);
            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
           //this user is disconnected
            }
    }
   }

  
    public void addSocketHandler (SocketHandler observer) {
        publicConnections.add(observer);
    }

    public void notifyAllObservers() {
        //    for (Clients observer : connectedobservers) {
        //       observer.update();
    }

    private void checkConnectionType() {

    }

    public int getTotalPlayers(){
   
        return publicConnections.size();
    }
    
    public void closeInputStream() throws IOException{ois.close();}
    
    public void closeOutputStream() throws IOException{oos.close();}
    
    public void closeSocket() throws IOException{socket.close();}
    
    
    
    public void closeStream(){
        try {
            closeOutputStream();
            closeInputStream();
            closeSocket();
            stop();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void closeStreams(){
       
   for(int i=0 ; i < publicConnections.size() ; i++){
    publicConnections.get(i).closeStream();
   
   }
  
   
   
   
    }      

}
