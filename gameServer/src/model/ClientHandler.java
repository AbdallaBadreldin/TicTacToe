/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ServerMainViewController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Mahmoud
 */
public class ClientHandler extends Thread {

    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public ClientHandler(Socket s) {

        try {
            if (s.isConnected()) {
                objectInputStream = new ObjectInputStream(s.getInputStream());
                objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            }
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        GameSession game = null;

        while (true) {
            try {
                if (objectInputStream != null) {
                    Object recievedObject = objectInputStream.readObject();
                    System.out.println("Object in ClientHandler\t" + recievedObject.toString());
                    if (recievedObject instanceof RequestGame) {
                        System.out.println(recievedObject.toString());
                        RequestGame request = (RequestGame) recievedObject;
                        game = new GameSession(
                                ServerMainViewController.clients.get(request.getRequesterPlayerId() - 1),
                                ServerMainViewController.clients.get(request.getRecieverPlayerId() - 1)
                        );
                    } else if (recievedObject instanceof PlayerMove) {
                        if (recievedObject instanceof PlayerMove) {
                            System.out.println(recievedObject.toString());
                            game.playerTwo.objectOutputStream.writeObject(recievedObject);
                            game.playerTwo.objectOutputStream.flush();
                            game.playersMoves[game.counter] = (PlayerMove) recievedObject;
                            game.counter++;
                            for (int i = 0; i < game.counter; i++) {
                                System.out.println(game.playersMoves[i].toString());
                            }
                        }
                    }
                } else {
                    closeConnection();
                    Server.clients.remove(this);
                    this.stop();
                }
            } catch (IOException ex) {
                closeConnection();
                Server.clients.remove(this);
                this.stop();
                System.out.println("Exception");
                //ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    void closeConnection() {
        try {
            this.objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
