package model;

import model.Player;

/**
 *
 * @author Mahmoud
 */
public class GameSessionOnlineMode {

    private int counter;
    
    Player playerOne;
    Player playerTwo;
    PlayerMoveOnlineMode[] playersMoves;
    boolean isPlayerOneTurn;

    public GameSessionOnlineMode() {
        playersMoves = new PlayerMoveOnlineMode[9];
        counter = 0;
    }
    
    public void addMove(PlayerMoveOnlineMode move){
        playersMoves[counter] = move;
        counter++;
    }

    public PlayerMoveOnlineMode[] getPlayersMoves() {
        return playersMoves;
    }

    public void setPlayersMoves(PlayerMoveOnlineMode[] playersMoves) {
        this.playersMoves = playersMoves;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
}
