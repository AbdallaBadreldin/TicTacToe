/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdo
 */
public class ListOfRecorders {
    
    private List<GameSession> gameSession;

    public ListOfRecorders() {
        gameSession = new ArrayList<>();
    }

    
    public ListOfRecorders(List<GameSession> gameSession) {
        this.gameSession = gameSession;
    }

    
    
    public List<GameSession> getGameSession() {
        return gameSession;
    }

    public void setGameSession(List<GameSession> gameSession) {
        this.gameSession = gameSession;
    }
    
}
