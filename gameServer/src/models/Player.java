/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mahmoud
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    
    private String userName;
    private String password;
    private int status;
    private String gender;
    private int win;
    private int lose;
    private int draw;
    private int totalScore;
    private int totalMatches;
    private boolean isInGame;

    public Player(String userName, String password, int status, String gender,
            int win, int lose, int draw, int totalScore, int totalMatches, boolean isInGame) {
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.gender = gender;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.totalScore = totalScore;
        this.totalMatches = totalMatches;
        this.isInGame = isInGame;
    }


    public Player() {
    }

    public Player(String userName, String password, int status, int win, int lose, int draw, int totalScore) {
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.totalScore = totalScore;
    }

    
    public ImageView getMyImage() {
        return myImage;
    }

    public void setMyImage(ImageView myImage) {
        this.myImage = myImage;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }
    
    public boolean isIsInGame() {
        return isInGame;
    }

    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }
}
