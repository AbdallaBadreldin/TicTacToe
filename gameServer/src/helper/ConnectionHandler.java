/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GameRequest;

/**
 *
 * @author Bossm
 */
public class ConnectionHandler extends Thread {
    DataInputStream dis;
    ObjectInputStream ois;
    
    PrintStream ps;
    String msg;
    static Vector<ConnectionHandler> clientsVector = new Vector<ConnectionHandler>();

    public ConnectionHandler(Socket cs) throws ClassNotFoundException {
        try {
            ois = new ObjectInputStream(cs.getInputStream());
            Object obj = ois.readObject();
            
            System.out.println("so we have inputstream here");
            if(obj instanceof GameRequest){
                System.out.println("You did it you recieved an object");
           GameRequest game =  (GameRequest) obj;
                System.out.println(game.getSender());
            }
            
            
            dis = new DataInputStream(cs.getInputStream());
            ps = new PrintStream(cs.getOutputStream());
            msg = dis.readLine();
            System.out.println(msg);
            ps.println(msg);
            ConnectionHandler.clientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConnectionHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void run() {
        while (true) {
            try {
                String str = dis.readLine();
                sendMessageToAll(str);
            } catch (IOException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void sendMessageToAll(String msg) {
        for (ConnectionHandler ch : clientsVector) {
            ch.ps.println(msg);
        }
    }
}
