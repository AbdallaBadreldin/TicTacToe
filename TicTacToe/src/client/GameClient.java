package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GameRequest;
import models.GameSessionInterface;
import models.InGameInterface;
import models.LoginRequest;
import models.Message;
import models.OnlinePlayers;
import models.Player;
import models.PlayerMove;
import models.SignInInterface;
import models.SignUpInterface;

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

    public void sendRequest(Player player) throws IOException {
        System.out.println("method started.");
        output.writeObject(player);
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

    public void read() {
        new Thread(new Runnable() {
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

                                inGameInterface.onGameRequestRecive((GameRequest) obj);

                            } else if (obj instanceof Player) {

                                signInInterface.onPlayerRevice((Player) obj);
                                //signUpInterface.onStateRecive((Player) obj);

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
        }).start();
        System.out.println("read thread stoped.");
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
