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
import static gameserver.FXMLDocumentController.fx;
//import static gameserver.FXMLDocumentController.setTotalPlayers;
import gameserver.GameServer;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import models.GameRequest;
import models.LoginRequest;
import models.Message;
import models.OnlinePlayers;
import models.Player;
import models.PlayerMove;
import models.RegisterRequest;
import rooms.Lobby;

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
    private static Lobby log;
    // private static List<Socket> publicConnectionsSockets  = new ArrayList<Socket>();
    // private static List<Thread> publicConnectionsThreads  = new ArrayList<Thread>();
    //private static List<ObjectOutputStream> publicConnectionsOOS  = new ArrayList<ObjectOutputStream>();
    //private static List<ObjectInputStream> publicConnectionsOIS  = new ArrayList<ObjectInputStream>();
    private static List<SocketHandler> handlers = new ArrayList<SocketHandler>();
    private static List<String> handlersUsername = new ArrayList<String>();
    private static List<Integer> handlersStatus = new ArrayList<>();
    //private static FXMLDocumentController fx;
    public SocketHandler(Socket socket) {
        log = new Lobby();

        try {
            runningServer = new Thread(this, "SocketHandler");
            socket.setKeepAlive(true); 
            socket.setSoTimeout(5000);
            this.socket = socket;
            
            oos = new ObjectOutputStream(socket.getOutputStream());  //wedon't wait data objects only
            ois = new ObjectInputStream(socket.getInputStream());
            
            System.out.println("got input stream");
            handlers.add(this);
            fx.updateTotalPlayers(handlers.size());
            runningServer.start();
        } catch (IOException ex) {
            System.out.println("any catches3");
              int i = handlers.size() ;
                System.out.println(i);
                  fx.updateTotalPlayers(i);
                  closeStream();
                 
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public void run() {
        while (true) {
            try {

                receivedObject = ois.readObject();
                System.out.println("is timed out ?");
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

                } else if (receivedObject instanceof LoginRequest) {
                    System.out.println("got login request");
                    Player p = new Player("hello", "java", 0, "M", 5, 4, 6, 7, 9, true);
                    oos.writeObject(p);

                    // LoginRequest game = (LoginRequest) receivedObject;
                    //loginRoutine(game);
                    //from here oop
                    playerUsername="hello";
                    log.attach(new LoggedPlayer(this));

                } else if (receivedObject instanceof GameRequest) {
                    GameRequest game = (GameRequest) receivedObject;
                    System.out.println("Recieveid game request");
                    playerTwo = game.getReciver();
                    if (playerTwo==null) {
                        break;
                    } else {
                        //searchForPlayerByUsername(playerTwo);
                    }
                    if (playerTwo.equals(null)) {
                    } else {
                        HandlerPlayerTwo.oos.writeObject(game);
                    } //host and join rotuine
                } else if (receivedObject instanceof PlayerMove) {
                    PlayerMove game = (PlayerMove) receivedObject;
                    System.out.println("Recieveid player move");
                    //handle player move routines inside game room

                } else if (receivedObject instanceof Message) {
                    Message game = (Message) receivedObject;
                    // sendMessageToAllClients(game);
                    log.notifyAllChat(game);
                    //notify all logged in people
                    System.out.println("Recieveid Message");
                } else if (receivedObject instanceof Player) {
                    System.out.println("got player obj");
                    Player game = (Player) receivedObject;
                    System.out.println(game.getUserName());
                    System.out.println(game.getPassword());
                    int reusult = signUpPlayer(game);
                    System.out.println(reusult);
                    if (signUpPlayer(game) == 1) {//successfully registered}
                        game.setStatus(1);
                        oos.writeObject(game);

                    } else {
                        game.setStatus(-1);
                        game.setUserName(null);
                        game.setPassword(null);
                        oos.writeObject(game);
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
                System.out.println("any catches");
                int i = handlers.size() - 1;
                System.out.println(i);
                  fx.updateTotalPlayers(i);
                
                removeOnlinePlayerFromList(playerUsername);
               closeStream();
             
                //here we remove socket and thread permentaly also search for the socket every where then update every single UI&client
            } catch (ClassNotFoundException ex) {
                System.out.println("any catch 2 ?");
                // Logger.getLogger(SocketHandler.class.getName()).Lobby(Level.SEVERE, null, ex);
            }
        }
    }

    public void loginRoutine(LoginRequest game) {
        System.out.println(game.getPassword());
        System.out.println(game.getUsername());
        System.out.println(DatabaseAccess.login(game.getUsername(), game.getPassword()));
        Player loginData = DatabaseAccess.login(game.getUsername(), game.getPassword());
        if (loginData == null) {
            try {
                game.setToken(null);
                oos.writeObject(game);
                System.out.println("the password and username is wrong");
                if (numberOfLoginAttemptes >= 3) {
                    closeStreamBySocket(this);
                }

                numberOfLoginAttemptes++;
            } catch (IOException ex) {
                System.out.println("any catches 1");
                closeStreamBySocket(this);
            }

        } else {
            try {

                System.out.println("the password and username are right");
                game.setToken("success");
                Player playerInformation = new Player();
                playerInformation.setPassword(game.getPassword());
                playerInformation.setUserName(game.getUsername());
                playerInformation.setLose(DatabaseAccess.getLoseScore(game.getUsername()));
                playerInformation.setWin(DatabaseAccess.getWinScore(game.getUsername()));
                playerInformation.setDraw(DatabaseAccess.getDrawScore(game.getUsername()));
                oos.writeObject(playerInformation);
                client = new LoggedPlayer();
                //client.setSocketInformation(this);
                client.setStatus(GameServer.ONLINE);
                log.attach(client);
                playerUsername = game.getUsername();
                handlersUsername.add(playerUsername);
            } catch (IOException ex) {
                System.out.println("any catches6");
                closeStreamBySocket(this);
            }
        }
    }

    public void searchForPlayerByUsername(String username) {
        for (int i = 0; i < handlers.size(); i++) {
            if (handlers.get(i).equals(username)) {
                playerTwo = username;
                HandlerPlayerTwo = handlers.get(i);
            } else {
                playerTwo = null;
                HandlerPlayerTwo = null;
            }
        }
    }

    public void removeOnlinePlayerFromList(String useName) {
        int iteration = handlersUsername.indexOf(useName);
        if (iteration == -1) {
            //then the user didn't found
        } else {
            handlersUsername.remove(iteration);
        }

    }

    public ObjectOutputStream getClientOOS() {
        return oos;
    }
 private void closeStream() {
        //clearRooms();
        try {
          log.detach(client);
                this.ois.close();
                this.oos.close();
                this.socket.close();
                this.runningServer.stop();
         
        } catch (IOException ex) {
            System.out.println("any catches5");
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public static void closeStreams() {
        clearRooms();
        try {
            for (int i = 0; i < handlers.size(); i++) {
                handlers.get(i).ois.close();
                handlers.get(i).oos.close();
                handlers.get(i).socket.close();
                handlers.get(i).runningServer.stop();
            }
        } catch (IOException ex) {
            System.out.println("any catches5");
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeStreamBySocket(SocketHandler mySocket) {

        try {

            int i = handlers.indexOf(mySocket);
            if (i == -1) {
                System.out.println("we didn't find the socket in hgandlers list");
            } else {
                log.detach(handlers.get(i).client);
                handlers.get(i).ois.close();
                handlers.get(i).oos.close();
                handlers.get(i).socket.close();

                handlers.get(i).runningServer.stop();
            }
        } catch (IOException ex) {
            System.out.println("any catches4");
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void clearRooms() {
        log.clearRoom();
    }

    private List<String> getOnlinePlayersList() {
        handlersUsername.clear();
        for (int i = 0; i < handlers.size(); i++) {
            handlersUsername.add(handlers.get(i).playerUsername);
        }
        return handlersUsername;
    }

    private List<Integer> getOnlinePlayersStatus() {
        handlersStatus.clear();
        for (int i = 0; i < handlers.size(); i++) {
            //  handlersStatus.add(handlers.get(i).playerStatus);
        }
        return handlersStatus;
    }
}
