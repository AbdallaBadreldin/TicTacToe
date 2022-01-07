package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Mahmoud
 */
public class GameSession implements Serializable {

    private int counter;
    private Player playerOne;
    private Player playerTwo;
    private PlayerMove[] playersMoves;
    private boolean isPlayerOneTurn;
    private String gameName;

    public GameSession(Player player1, Player player2) {
        playersMoves = new PlayerMove[9];
        playerOne = player1;
        playerTwo = player2;
        counter = 0;
    }

    public void createGameName() {
        gameName = playerOne.getUserName()
                + " vs "
                + playerTwo.getUserName()
                + " " + new Date().toString() + LocalDate.now();
    }

    public void addMove(PlayerMove move) {
        playersMoves[counter] = move;
        counter++;
    }

    public void resetMove() {
        playersMoves = new PlayerMove[9];
        counter = 0;
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

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return gameName;
    }

}
