/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import Observer.LoggedPlayer;
import java.util.List;
import models.Message;


/**
 *
 * @author Bossm
 */
public interface Room {

    public abstract void attach(LoggedPlayer o);

    public abstract void detach(LoggedPlayer o);

    public abstract void notifyAllChat(Message m);
   
    public abstract void notifyUpdateList(List<String> listOfUsers,List<Integer> statusOfUsers);
    
    public abstract void clearRoom();

    public abstract void updateUI();
}
