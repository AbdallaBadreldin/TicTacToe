package models;

import java.io.Serializable;

/**
 * @author Abdo
 */
public class Message implements Serializable {

    private String message;
    private String username;
    private static final long serialVersionUID = 6525095098267757690L;

    public Message() {
    }

    public Message(String msg, String username) {
        this.message = msg;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Message{" + "message=" + message + ", username=" + username + '}';
    }

}
