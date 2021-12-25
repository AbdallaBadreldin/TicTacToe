package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import models.GameRequest;
import models.Message;
import models.Player;

/**
 * @author Abdo
 */
public class GameClient {
    
    private static Socket mSocket;
    private static InputStreamReader mReader;
    private static OutputStreamWriter mWriter;
    private static BufferedReader mBufferedReader;
    private static BufferedWriter mBufferedWriter;
    
    private static ObjectOutputStream objOutput;
    private static ObjectInputStream objInput;
    /**
     *@throws IOException
     */
    public static void openConnection(String address, int port)
            throws IOException{
        mSocket = new Socket(address, port);
        mReader = new InputStreamReader(mSocket.getInputStream());
        mWriter = new OutputStreamWriter(mSocket.getOutputStream());
        
        objInput = new ObjectInputStream(mSocket.getInputStream());
        objOutput = new ObjectOutputStream(mSocket.getOutputStream());
        mBufferedReader = new BufferedReader(mReader);
        mBufferedWriter = new BufferedWriter(mWriter);
    }
    
    /**
     * @throws IOException
     */
    public static void sendAMessage(Message message) throws IOException{
        objOutput.writeObject(message);
        mBufferedWriter.write(message.getUsername());
        mBufferedWriter.write(message.getMessage());
        mBufferedWriter.newLine();
        mBufferedWriter.flush();
    }
    
    public static void makeAMove() throws IOException, ClassNotFoundException{
        boolean name = objInput.readObject().getClass().isInstance(Player.class);
        Object test = new GameRequest();
        Object test1 = new Player();
        System.out.println("is test1 player?"+test1.equals(Player.class));
        System.out.println("is test1 player?"+test1.equals(GameRequest.class));

    }
    
}
