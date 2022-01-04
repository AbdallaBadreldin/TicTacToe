/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author Eslam Esmael
 */
public class RequestGame implements Serializable{

    private int requesterPlayerId;
    private int recieverPlayerId;
    private static final long serialVersionUID = 6529685098267757690L;

    public RequestGame(int requesterPlayerId, int recieverPlayerId) {
        this.requesterPlayerId = requesterPlayerId;
        this.recieverPlayerId = recieverPlayerId;
    }

    public int getRequesterPlayerId() {
        return requesterPlayerId;
    }

    public void setRequesterPlayerId(int requesterPlayerId) {
        this.requesterPlayerId = requesterPlayerId;
    }

    public int getRecieverPlayerId() {
        return recieverPlayerId;
    }

    public void setRecieverPlayerId(int recieverPlayerId) {
        this.recieverPlayerId = recieverPlayerId;
    }

    @Override
    public String toString() {
        return "RequestGame{" + "requesterPlayerId=" + requesterPlayerId + ", recieverPlayerId=" + recieverPlayerId + '}';
    }



}
