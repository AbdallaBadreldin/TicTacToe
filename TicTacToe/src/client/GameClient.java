package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import pojo.Message;

/**
 * @author Abdo
 */
public class GameClient {
    
    private static Socket mSocket;
    private static InputStreamReader mReader;
    private static OutputStreamWriter mWriter;
    private static BufferedReader mBufferedReader;
    private static BufferedWriter mBufferedWriter;
    
    /**
     *@throws IOException
     */
    public static void openConnection(String address, int port)
            throws IOException{
        mSocket = new Socket(address, port);
        mReader = new InputStreamReader(mSocket.getInputStream());
        mWriter = new OutputStreamWriter(mSocket.getOutputStream());
        mBufferedReader = new BufferedReader(mReader);
        mBufferedWriter = new BufferedWriter(mWriter);
    }
    
    /**
     * @throws IOException
     */
    public static void sendAMessage(Message message) throws IOException{
        mBufferedWriter.write(message.getUsername());
        mBufferedWriter.write(message.getMessage());
        mBufferedWriter.newLine();
        mBufferedWriter.flush();
    }
    
    public static void makeAMove(){}
    
}
