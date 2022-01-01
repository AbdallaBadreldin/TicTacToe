/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import models.Message;

/**
 *
 * @author Bossm
 */
public interface Room {
    public void attach(Clients o);
    public void detach(Clients o);
    public void notifyUpdate(Message m);
    public void clearRoom();
    public void updateUI();
}
