package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import models.GameRequest;
import models.Message;
import models.Player;

/**
 * @author Abdo
 */
public class GameClient {
    
    private static Socket mSocket;
    
    private static ObjectOutputStream objOutput;
    private static ObjectInputStream objInput;
    
    /**
     *@throws IOException
     */
    public static void openConnection(String address, int port)
            throws IOException{
        mSocket = new Socket(address, port);
        objInput = new ObjectInputStream(new BufferedInputStream(mSocket.getInputStream()));
        objOutput = new ObjectOutputStream(new BufferedOutputStream(mSocket.getOutputStream()));
    }
    
    /**
     * @throws IOException
     */
    public static void sendAMessage(Message message) throws IOException{
       
    }
    
    public static void makeAMove() throws IOException, ClassNotFoundException{
        boolean name = objInput.readObject().getClass().isInstance(Player.class);
        Object test = new GameRequest();
        Object test1 = new Player();
        System.out.println("is test1 player?"+test1.equals(Player.class));
        System.out.println("is test1 player?"+test1.equals(GameRequest.class));

    }
    
}
