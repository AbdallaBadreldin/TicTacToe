package models;

import java.io.Serializable;

/**
 * @author Abdo
 */
public class GameRequest implements Serializable {

    private static final long serialVersionUID = 6529685068267757690L;
    private String sender;
    private String reciver;

    public GameRequest() {
    }

    public GameRequest(Player sender, Player reciver) {
        this.sender = sender.getUserName();
        this.reciver = reciver.getUserName();
    }

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }

}
