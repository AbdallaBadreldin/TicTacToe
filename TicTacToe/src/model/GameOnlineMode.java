/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Mahmoud
 */
public class GameOnlineMode implements Serializable{
    
    
    private static final long serialVersionUID = 6529685098267757610L;
    

    PlayerMoveOnlineMode[] playersMoves;
    boolean isPlayerOneTurn;
    boolean isGameEnd;

    boolean firstPlayerWinner = false;
    boolean secondPlayerWinner = false;
    PlayerMoveOnlineMode[][] pmfc;

    //TODO add players id to use it in database 
    int counter;

    public GameOnlineMode() {
        counter = 0;
        playersMoves = new PlayerMoveOnlineMode[9];
        isGameEnd = true;
        isPlayerOneTurn = true;
        System.out.println(this.toString());

        pmfc = new PlayerMoveOnlineMode[3][3];
    }
    
    public void addMove(PlayerMoveOnlineMode move){
        playersMoves[counter] = move;
        counter++;
    }

}
