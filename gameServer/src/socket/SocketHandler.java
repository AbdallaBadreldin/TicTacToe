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
import static socket.Clients.clientsSocket;
//import gameserver.FXMLDocumentController;

/**
 *
 * @author Bossm
 */
public class SocketHandler implements Runnable {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Object receivedObject;
    private Thread runningServer;
    
    
    private static List<Socket> publicConnectionsSockets  = new ArrayList<Socket>();
    private static List<Thread> publicConnectionsThreads  = new ArrayList<Thread>();
    private static List<ObjectOutputStream> publicConnectionsOOS  = new ArrayList<ObjectOutputStream>();
    private static List<ObjectInputStream> publicConnectionsOIS  = new ArrayList<ObjectInputStream>();
   
    private static List<Clients> RunningGames = new ArrayList<Clients>();
    
    public SocketHandler(Socket socket) {
        try {
            
           runningServer = new Thread(this,"SocketHandler");
            this.socket = socket;
            this.socket.setSoTimeout(1000);
            oos = new ObjectOutputStream(socket.getOutputStream());  //wedon't wait data objects only
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("got input stream");
           
            addSocketHandlerAttributes(socket,oos,ois,runningServer);
       //   FXMLDocumentController.updateTotalPlayers(5);
            runningServer.start();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //don't know which better
    public static void addSocketHandlerAttributes(Socket socket , ObjectOutputStream oos, ObjectInputStream ois, Thread runningServer){
   
        publicConnectionsSockets.add(socket);
    publicConnectionsOOS.add(oos);
    publicConnectionsOIS.add(ois);
    publicConnectionsThreads.add(runningServer);          
    }
   //not used
     public static void killAllService(Socket socket , ObjectOutputStream oos, ObjectInputStream ois, Thread runningServer){
    publicConnectionsSockets.add(socket);
   // publicConnectionsOOS.add(oos);
   // publicConnectionsOIS.add(ois);
    publicConnectionsThreads.add(runningServer);          
    }
    
    
    public void run() {
        while (true) {
            try {
                
                receivedObject = ois.readObject();
                System.out.println("is timed out ?");
                if(socket.isClosed()){
                   
                         System.out.println("got the id: "+publicConnectionsSockets.indexOf(socket)+ "  ... and going to remove it");
                    closeStreamByID(publicConnectionsSockets.indexOf(socket));
                            
                }
                
              //  System.out.println("hey object equal what ??");
               // System.out.println(receivedObject);
                //System.out.println(receivedObject.toString());
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
                    //sendMessageToAll(game);  
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
               // Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   public void sendMessageToAll(Message msg) {
        for (Socket soc : clientsSocket) {
            try {
                publicConnectionsOOS.get(publicConnectionsSockets.indexOf(soc)).writeObject((Object)msg);
            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
           //this user is disconnected
            }
    }
   }

/*  
    public void addSocketHandler (SocketHandler observer) {
        publicConnections.add(observer);
    }
*/
    public void notifyAllObservers() {
        //    for (Clients observer : connectedobservers) {
        //       observer.update();
       
    }

    private void checkConnectionType() {

    }


    
    public void closeStream(){
        try {
            ois.close();
            oos.close();
            socket.close();
            runningServer.stop();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static void closeAllStreams(){
       
   for(int i=0 ; i < publicConnectionsSockets.size() ; i++){
  
       try {
           System.out.println("called close for loop"+i+"size"+publicConnectionsSockets.size());
           
         
             publicConnectionsSockets.get(i).close();
           publicConnectionsOIS.get(i).close();
    
           publicConnectionsSockets.get(i).getOutputStream().close();
           publicConnectionsSockets.get(i).getInputStream().close();
           publicConnectionsSockets.get(i).close();  
           publicConnectionsThreads.get(i).stop();
       } catch (IOException ex) {
           Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   publicConnectionsSockets.clear();
 }      
     public void closeStreamByID(int ID){
       
       try {
           System.out.println("called close for ID"+ID+"size"+publicConnectionsSockets.size());
        
             publicConnectionsOOS.get(ID).close();
           publicConnectionsOIS.get(ID).close();
           publicConnectionsSockets.get(ID).close(); 
           
       } catch (IOException ex) {
           Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
       }
           publicConnectionsOOS.remove(ID);
           publicConnectionsOIS.remove(ID);
       
           publicConnectionsSockets.remove(ID); 
           System.out.println("before remove thread");
           publicConnectionsThreads.remove(ID);
           System.out.println("after remove thread");
            System.out.println("before stop method ");
           runningServer.stop();
           System.out.println("after stop method");
     }
  
       
     

}
