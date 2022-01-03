/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author Bossm
 */
public class FeedBack implements Serializable {

    private String requestType;
    private boolean isSucceed;

    public boolean isIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

}
