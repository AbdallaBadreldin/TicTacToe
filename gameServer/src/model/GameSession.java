
package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud
 */
public class GameSession extends Thread {

    int counter;
    ClientHandler playerOne;
    ClientHandler playerTwo;
    PlayerMove[] playersMoves;
    boolean isPlayerOneTurn;
    boolean isGamerunning;

    public GameSession(ClientHandler playerOne, ClientHandler playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        counter = 0;
        playersMoves = new PlayerMove[9];
        isGamerunning = true;
        isPlayerOneTurn = true;
        System.out.println(this.toString());
    }

    @Override
    public void run() {
        try {
            while (isGamerunning) {
                //player one have to play
                Object move = playerOne.objectInputStream.readObject();
                if (move instanceof PlayerMove) {
                    System.out.println(move.toString());
                    playerTwo.objectOutputStream.writeObject(move);
                    playerTwo.objectOutputStream.flush();
                    playersMoves[counter] = (PlayerMove) move;
                    counter++;
                    isPlayerOneTurn = !isPlayerOneTurn;
                    for(int i = 0; i < counter ; i ++){
                        System.out.println(playersMoves[i].toString());
                    }
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMove(PlayerMove move) {
        playersMoves[counter] = move;
        counter++;
        isPlayerOneTurn = !isPlayerOneTurn;

    }

    public PlayerMove[] getPlayersMoves() {
        return playersMoves;
    }

    public void setPlayersMoves(PlayerMove[] playersMoves) {
        this.playersMoves = playersMoves;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ClientHandler getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(ClientHandler playerOne) {
        this.playerOne = playerOne;
    }

    public ClientHandler getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(ClientHandler playerTwo) {
        this.playerTwo = playerTwo;
    }

    public boolean isIsPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    public void setIsPlayerOneTurn(boolean isPlayerOneTurn) {
        this.isPlayerOneTurn = isPlayerOneTurn;
    }

    @Override
    public String toString() {
        return "GameSession{" + "counter=" + counter + ", playerOne=" + playerOne + ", playerTwo=" + playerTwo + ", playersMoves=" + playersMoves + ", isPlayerOneTurn=" + isPlayerOneTurn + ", isGamerunning=" + isGamerunning + '}';
    }

}
