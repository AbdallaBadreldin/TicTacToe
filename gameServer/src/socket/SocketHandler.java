/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

//import Observer.LoginInterface;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GameRequest;
import models.Message;
import models.Player;
import models.PlayerMove;
//import static socket.Clients.clientsSocket;
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
    
    
   // private static List<Socket> publicConnectionsSockets  = new ArrayList<Socket>();
   // private static List<Thread> publicConnectionsThreads  = new ArrayList<Thread>();
    //private static List<ObjectOutputStream> publicConnectionsOOS  = new ArrayList<ObjectOutputStream>();
    //private static List<ObjectInputStream> publicConnectionsOIS  = new ArrayList<ObjectInputStream>();
    private static List<SocketHandler> handlers  = new ArrayList<SocketHandler>();
   
    protected static Vector<Socket> clientsSocket = new Vector<>();
    private static List<Clients> RunningGames = new ArrayList<Clients>();
    
    public SocketHandler(Socket socket) {
        try {
            
           runningServer = new Thread(this,"SocketHandler");
            this.socket = socket;
            this.socket.setSoTimeout(1000);
            this.socket.setKeepAlive(true);
            oos = new ObjectOutputStream(socket.getOutputStream());  //wedon't wait data objects only
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("got input stream");
         
           // addSocketHandlerAttributes(socket,oos,ois,runningServer);
       //   FXMLDocumentController.updateTotalPlayers(5);
         handlers.add(this);
            runningServer.start();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void run() {
        while (true) {
            try {
                
                receivedObject = ois.readObject();
                System.out.println("is timed out ?");
                if(socket.isClosed()){
                   
      //                   System.out.println("got the id: "+publicConnectionsSockets.indexOf(socket)+ "  ... and going to remove it");
        //            closeStreamByID(publicConnectionsSockets.indexOf(socket));
                            
                }
                
              //  System.out.println("hey object equal what ??");
               // System.out.println(receivedObject);
                //System.out.println(receivedObject.toString());
                if (receivedObject instanceof GameRequest) {
                    GameRequest game = (GameRequest) receivedObject;
                    System.out.println("Recieveid game request");
        //host and join rotuine
                }
                if (receivedObject instanceof PlayerMove) {
                    PlayerMove game = (PlayerMove) receivedObject;
                    System.out.println("Recieveid player move");
                    //handle player move routines inside game room
                }
                if (receivedObject instanceof Message) {
                    Message game = (Message) receivedObject;
                   // sendMessageToAllClients(game);
                   //notify all logged in people
                    System.out.println("Recieveid Message");
                }
                if (receivedObject instanceof Player) {
                    Player game = (Player) receivedObject;
                    //sendMessageToAll(str);  
                    //just for test or maybe for further usage
                    System.out.println("Recieveid Player data");
                } else {
                    //print error object is worng seems milicious data or attack
                }
                //sendMessageToAll(str);     /let's check what object we have
            } catch (IOException ex) {
                // Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("any catch ?");
            } catch (ClassNotFoundException ex) {
                System.out.println("any catch 2 ?");
               // Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ObjectOutputStream getClientOOS(){
    return oos;
    }
    public static void closeStream(){
        try {
            for(int i = 0 ; i<handlers.size();i++){
            handlers.get(i).ois.close();
            handlers.get(i).oos.close();
            handlers.get(i).socket.close();
            handlers.get(i).runningServer.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
     

}
