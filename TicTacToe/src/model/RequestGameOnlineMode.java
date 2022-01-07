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
public class RequestGameOnlineMode implements Serializable {

    private int requesterPlayerId;
    private int recieverPlayerId;

    private boolean accepted;
    private boolean sent;

    private GameSessionOnlineMode gameSession;

    private static final long serialVersionUID = 6529685098267757690L;

    public RequestGameOnlineMode(int requesterPlayerId, int recieverPlayerId) {
        this.requesterPlayerId = requesterPlayerId;
        this.recieverPlayerId = recieverPlayerId;
        this.accepted = false;
        this.sent = false;
    }

    public RequestGameOnlineMode(int requesterPlayerId, int recieverPlayerId, boolean accetpance) {
        this.requesterPlayerId = requesterPlayerId;
        this.recieverPlayerId = recieverPlayerId;
        this.accepted = accetpance;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
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
        return "RequestGame{" + "requesterPlayerId=" + requesterPlayerId + ", recieverPlayerId=" + recieverPlayerId + ", accepted=" + accepted + ", gameSession=" + gameSession + '}';
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

}
