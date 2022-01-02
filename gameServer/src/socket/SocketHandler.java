/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

//import Observer.LoginInterface;
import Observer.LoggedPlayer;
import database.DatabaseAccess;
import static database.DatabaseAccess.signUpPlayer;
import gameserver.FXMLDocumentController;
import gameserver.GameServer;
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
import models.LoginRequest;
import models.Message;
import models.OnlinePlayers;
import models.Player;
import models.PlayerMove;
import models.RegisterRequest;
import rooms.log;

//import static socket.Clients.clientsSocket;
//import gameserver.FXMLDocumentController;
/**
 *
 * @author Bossm
 */
public class SocketHandler implements Runnable {
private String playerUsername;
private String playerTwo;
private Socket socketPlayerTwo;
private SocketHandler HandlerPlayerTwo;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Object receivedObject;
    private Thread runningServer;
    LoggedPlayer client;
    private int numberOfLoginAttemptes;
   
    // private static List<Socket> publicConnectionsSockets  = new ArrayList<Socket>();
    // private static List<Thread> publicConnectionsThreads  = new ArrayList<Thread>();
    //private static List<ObjectOutputStream> publicConnectionsOOS  = new ArrayList<ObjectOutputStream>();
    //private static List<ObjectInputStream> publicConnectionsOIS  = new ArrayList<ObjectInputStream>();
    private static List<SocketHandler> handlers = new ArrayList<SocketHandler>();
private static  List<String> handlersUsername = new ArrayList<String>();
private static  List<Integer> handlersStatus = new ArrayList<>();

    public SocketHandler(Socket socket) {
        try {
            runningServer = new Thread(this, "SocketHandler");
            //socket.setKeepAlive(false); 
            //socket.setSoTimeout(30000);
            this.socket = socket;
            oos = new ObjectOutputStream(socket.getOutputStream());  //wedon't wait data objects only
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("got input stream");
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
                //System.out.println("is timed out ?");
                //if(socket.isClosed()){

                //            System.out.println("got the id: "+publicConnectionsSockets.indexOf(socket)+ "  ... and going to remove it");
                //            closeStreamByID(publicConnectionsSockets.indexOf(socket));
                // }
                //  System.out.println("hey object equal what ??");
                // System.out.println(receivedObject);
                //System.out.println(receivedObject.toString());
     
                    //host and join rotuine
             /*         if (receivedObject instanceof ChangeStatue) {
                    OnlinePlayers game = (OnlinePlayers) receivedObject;
                    getOnlinePlayersList();
                   
                }
               */
                if (receivedObject instanceof OnlinePlayers) {
                    OnlinePlayers game = (OnlinePlayers) receivedObject;
                    getOnlinePlayersList();
                   
                }


                if (receivedObject instanceof LoginRequest) {
                    LoginRequest game = (LoginRequest) receivedObject;
                    loginRoutine(game);
                   
                }

                if (receivedObject instanceof GameRequest) {
                    GameRequest game = (GameRequest) receivedObject;
                    System.out.println("Recieveid game request");
                    playerTwo=game.getReciver();
                   searchForPlayerByUsername(playerTwo);
                   if(playerTwo.equals(null))
                   {}
                   else{HandlerPlayerTwo.oos.writeObject(game);
                   } //host and join rotuine
                }
                if (receivedObject instanceof PlayerMove) {
                    PlayerMove game = (PlayerMove) receivedObject;
                    System.out.println("Recieveid player move");
                    //handle player move routines inside game room

                }
                if (receivedObject instanceof Message) {
                    Message game = (Message) receivedObject;
                    // sendMessageToAllClients(game);
                    log.notifyUpdate(game);
                    //notify all logged in people
                    System.out.println("Recieveid Message");
                }
                if (receivedObject instanceof Player) {
                    System.out.println("got player obj");
                    Player game = (Player) receivedObject;
                    System.out.println(game.getUserName());
                    System.out.println(game.getPassword());
                    int reusult = signUpPlayer(game);
                    System.out.println(reusult);
                    if(signUpPlayer(game)==1)
                    {//successfully registered}
                        
                        
                    }
                    else {
                    //Handle failed process
                    }
                     
                    //just for test or maybe for further usage
                    System.out.println("Recieveid Player data");
                } else {
                    //print error object is worng seems milicious data or attack
                    System.out.println("milicious code");
                }
                //sendMessageToAll(str);     /let's check what object we have
            } catch (IOException ex) {
                closeStreamBySocket(socket);
                //here we remove socket and thread permentaly also search for the socket every where then update every single UI&client
            } catch (ClassNotFoundException ex) {
                System.out.println("any catch 2 ?");
                // Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loginRoutine(LoginRequest game) {
        if (DatabaseAccess.login(game.getUsername(), game.getPassword()).equals(null)) {
            try {
                game.setToken(null);
                oos.writeObject(game);

                if (numberOfLoginAttemptes >= 3) {
                    closeStreamBySocket(socket);
                }

                numberOfLoginAttemptes++;
            } catch (IOException ex) {
                closeStreamBySocket(socket);
            }

        } else {
            try {
                game.setToken("success");
                oos.writeObject(game);
                client = new LoggedPlayer();
                client.setSocketInformation(this);
                client.setStatus(GameServer.ONLINE);
                log.attach(client);
                playerUsername=game.getUsername();
            } catch (IOException ex) {
                closeStreamBySocket(socket);
            }
        }
    }
public void searchForPlayerByUsername(String username){
   for (int i = 0; i < handlers.size(); i++) {
   if(handlers.get(i).equals(username)){
   playerTwo=username;
   HandlerPlayerTwo=handlers.get(i);}
   else{
   playerTwo=null;
   HandlerPlayerTwo=null;
   }
   }
}
    public ObjectOutputStream getClientOOS() {
        return oos;
    }

    public static void closeStream() {
        clearRooms();
        try {
            for (int i = 0; i < handlers.size(); i++) {
                handlers.get(i).ois.close();
                handlers.get(i).oos.close();
                handlers.get(i).socket.close();
                handlers.get(i).runningServer.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeStreamBySocket(Socket mySocket) {
      
        try {
           
            int i = handlers.indexOf(mySocket);
            if(i==-1){
                System.out.println("we didn't find the socket in hgandlers list");}
            else{
            log.detach( handlers.get(i).client);
            handlers.get(i).ois.close();
            handlers.get(i).oos.close();
            handlers.get(i).socket.close();
           
            handlers.get(i).runningServer.stop();}
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void clearRooms() {
        log.clearRoom();
    }

    private List<String> getOnlinePlayersList() {
        handlersUsername.clear();
   for (int i=0;i<handlers.size();i++){
   handlersUsername.add(handlers.get(i).playerUsername);
   }
   return handlersUsername;
    }
    
      private List<String> getOnlinePlayersStatus() {
        handlersUsername.clear();
   for (int i=0;i<handlers.size();i++){
   handlersUsername.add(handlers.get(i).playerUsername);
   }
   return handlersUsername;
    }
}
