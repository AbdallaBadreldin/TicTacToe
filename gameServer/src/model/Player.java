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
public class Player implements Serializable{

    private static final long serialVersionUID = 6529682930267757690L;
    private String userName;
    private String password;
    private int score;
    private boolean isActive;
    private boolean isPlayong;
    private int win;
    private int lose;
    private int draw;

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
  
    


    public Player(){}

    public Player(String userName) {
        this.userName = userName;
    }

    public Player(String userName, String password, int score, boolean isActive, boolean isPlayong, int win, int lose, int draw) {
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.isActive = isActive;
        this.isPlayong = isPlayong;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsPlayong() {
        return isPlayong;
    }

    public void setIsPlayong(boolean isPlayong) {
        this.isPlayong = isPlayong;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
    
}
