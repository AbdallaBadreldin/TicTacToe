package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GameRequest;
import client.interfaces.GameSessionInterface;
import client.interfaces.InGameInterface;
import models.LoginRequest;
import models.Message;
import models.OnlinePlayers;
import models.Player;
import models.PlayerMove;
import client.interfaces.SignInInterface;
import client.interfaces.SignUpInterface;
import models.Common;

/**
 * @author Abdo
 */
public class GameClient {

    private Socket mSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private static GameClient gameClient;
    private SignInInterface signInInterface;
    private SignUpInterface signUpInterface;
    private InGameInterface inGameInterface;
    private GameSessionInterface gameSessionInterface;
    private boolean isFirstTimeToRun = true;

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Object obj;
            while (true) {
                try {
                    if (input != null) {
                        obj = input.readObject();
                        if (obj instanceof PlayerMove) {

                            gameSessionInterface.onPlayerMoveRecive((PlayerMove) obj);

                        } else if (obj instanceof Message) {

                            inGameInterface.onMessageRecive((Message) obj);

                        } else if (obj instanceof OnlinePlayers) {

                            inGameInterface.onOnlinePlayersRecive((OnlinePlayers) obj);

                        } else if (obj instanceof GameRequest) {
                            if (((GameRequest) obj).getReciver()
                                    .equalsIgnoreCase(Common.signedInPlayer.getUserName())) {
                                inGameInterface.onGameRequestRecive((GameRequest) obj);
                            }

                        } else if (obj instanceof Player) {

                            if (signInInterface != null) {
                                signInInterface.onPlayerRevice((Player) obj);
                            } else {
                                signUpInterface.onStateRecive((Player) obj);
                            }

                        } else {
                            System.out.println("UKNOWEN OBJECT RECIVED");
                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.getMessage();
                    Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });

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
        input = new ObjectInputStream(mSocket.getInputStream());
        output = new ObjectOutputStream(mSocket.getOutputStream());
    }

    public boolean isSocketConnected() {
        return mSocket.isConnected();
    }

    /**
     * @param request
     * @throws IOException
     */
    public void sendRequest(GameRequest request) throws IOException {
        output.writeObject(request);
        output.flush();
    }

    /**
     *
     * @param player
     * @throws IOException
     */
    public void sendRequest(Player player) throws IOException {
        output.writeObject(player);
        output.flush();
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

    public void sendRequest(LoginRequest loginRequest) throws IOException {
        output.writeObject(loginRequest);
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

    public void startReading() throws InterruptedException {
        if (isFirstTimeToRun) {
            System.out.println("start called");

            thread.start();
        } else {
            System.out.println("notify called");
            thread.resume();
        }
        isFirstTimeToRun = false;
        System.out.println("reading");
    }

    public void stopReading() throws InterruptedException {
        thread.suspend();
        System.out.println("stop reading");
    }

    /**
     *
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        input.close();
        output.close();
        mSocket.close();
        gameClient = null ;
    }

    public void setSignInInterface(SignInInterface signInInterface) {
        this.signInInterface = signInInterface;
    }

    public void setSignUpInterface(SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
    }

    public void setInGameInterface(InGameInterface inGameInterface) {
        this.inGameInterface = inGameInterface;
    }

    public void setGameSessionInterface(GameSessionInterface gameSessionInterface) {
        this.gameSessionInterface = gameSessionInterface;
    }
}
