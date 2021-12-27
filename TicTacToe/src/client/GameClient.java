package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Abdo
 */
public class GameClient {

    private Socket mSocket;
    private ObjectOutputStream objOutput;
    private ObjectInputStream objInput;
    private static GameClient gameClient;
    
    private GameClient(){}
    
    public static GameClient getInstactance(String address, int port) throws IOException{
        if (gameClient == null) {
            gameClient = new GameClient();
            gameClient.openConnection(address, port);
            return gameClient;
        }else {
            return gameClient;
        }
    }
    
    /**
     * @throws IOException
     */
    private void openConnection(String address, int port) throws IOException {
        new Thread(){
          public void run() {
              try {
                  mSocket = new Socket(address, port);
                  objInput = new ObjectInputStream(new BufferedInputStream(mSocket.getInputStream()));
                  objOutput = new ObjectOutputStream(new BufferedOutputStream(mSocket.getOutputStream()));
              } catch (IOException ex) {
                  Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
              }
              
          }
      }.start();
        
    }

    /**
     * @throws IOException
     */
    public void sendRequest(Object request) throws IOException {
        System.out.println("method started.");
        objOutput.writeObject(request);
        System.out.println("req sent.");
        objOutput.flush();
        System.out.println("method finsehd.");
    }
    
    public Object reciveRequest(Object request) throws IOException, ClassNotFoundException {
        Object object = objInput.readObject();
        objInput.reset();
        return object;
    }
    
    public void closeConnection() throws IOException {
        objInput.close();
        objOutput.close();
        mSocket.close();
    }
    
}
