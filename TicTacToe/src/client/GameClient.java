package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        mSocket = new Socket(address, port);
        objInput = new ObjectInputStream(new BufferedInputStream(mSocket.getInputStream()));
        objOutput = new ObjectOutputStream(new BufferedOutputStream(mSocket.getOutputStream()));
    }

    /**
     * @throws IOException
     */
    public void sendRequest(Object request) throws IOException {
       objOutput.writeObject(request);
       objOutput.flush();
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
