package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import models.GameRequest;
import models.Message;
import models.OnlinePlayers;
import models.PlayerMove;

/**
 * @author Abdo
 */
public class GameClient {

    private Socket mSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private static GameClient gameClient;

    private GameClient() {
    }

    public static GameClient getInstactance(String address, int port) throws IOException {
        if (gameClient == null) {
            gameClient = new GameClient();
            gameClient.openConnection(address, port);
            return gameClient;
        } else {
            return gameClient;
        }
    }

    /**
     * @throws IOException
     */
    private void openConnection(String address, int port) throws IOException {
        mSocket = new Socket(address, port);
        input = new ObjectInputStream(new BufferedInputStream(mSocket.getInputStream()));
        output = new ObjectOutputStream(new BufferedOutputStream(mSocket.getOutputStream()));
    }

    /**
     * @param request
     * @throws IOException
     */
    public void sendRequest(GameRequest request) throws IOException {
        System.out.println("method started.");
        output.writeObject(request);
        System.out.println("req sent.");
        output.flush();
        System.out.println("method finsehd.");
    }

    /**
     *
     * @param msg
     * @throws IOException
     */
    public void sendRequest(Message msg) throws IOException {
        output.writeObject(msg);
        output.flush();
    }
    
    /**
     *
     * @param move
     * @throws IOException
     */
    public void sendRequest(PlayerMove move) throws IOException {
        output.writeObject(move);
        output.flush();
    }

    /**
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public OnlinePlayers getOnlinePlayerList(Object request) throws IOException, ClassNotFoundException {
        OnlinePlayers players =  (OnlinePlayers) input.readObject();
        input.reset();
        return players;
    }
    
    public PlayerMove getGameMove() throws IOException, ClassNotFoundException{
        input.readLine();
        PlayerMove move = (PlayerMove) input.readObject();
        input.reset();
        return move;
    }
    
    
    public Message getMessage() throws IOException, ClassNotFoundException{
        Message msg = (Message) input.readObject();
        input.reset();
        return msg;
    }

    /**
     *
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        input.close();
        output.close();
        mSocket.close();
    }

}
