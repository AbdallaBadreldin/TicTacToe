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
    Socket socket;
    GameSession gameSession;
    Server server;

    public ClientHandler(Socket s) {

        try {
            if (s.isConnected()) {
                socket = s;
                objectInputStream = new ObjectInputStream(s.getInputStream());
                objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                server = Server.getServer();
            }
            start();
        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Client handler Thread");
            try {
                if (objectInputStream != null) {
                    Object recievedObject = objectInputStream.readObject();
                    if (recievedObject instanceof RequestGame) {
                        RequestGame request = (RequestGame) recievedObject;
                        if (!request.isSent()) {
                            ClientHandler client = ServerMainViewController.clients.get(request.getRecieverPlayerId() - 1);
                            request.setSent(true);
                            client.objectOutputStream.writeObject(request);
                        } else {
                            //already sent
                            if (request.isAccepted()) {
                                gameSession = new GameSession(
                                        ServerMainViewController.clients.get(request.getRequesterPlayerId() - 1),
                                        ServerMainViewController.clients.get(request.getRecieverPlayerId() - 1)
                                );

                                ServerMainViewController.clients.get(request.getRecieverPlayerId() - 1).objectOutputStream.writeObject(gameSession.game);
                                ServerMainViewController.clients.get(request.getRequesterPlayerId() - 1).objectOutputStream.writeObject(gameSession.game);
                                ServerMainViewController.clients.get(request.getRequesterPlayerId() - 1).gameSession = gameSession;
                            }
                        }
                    } else if (recievedObject instanceof PlayerMove) {

//                        ((PlayerMove) recievedObject).setIsX(gameSession.turn);
//                        gameSession.playerOne.objectOutputStream.writeObject(recievedObject);
//                        gameSession.playerTwo.objectOutputStream.writeObject(recievedObject);
//
//                        gameSession.game.playersMoves[gameSession.game.counter] = (PlayerMove) recievedObject;
//                        gameSession.game.counter++;
//
//                        gameSession.turn = !gameSession.turn;
                    } else if (recievedObject instanceof RegistrationModel) {
                        RegistrationModel register = (RegistrationModel) recievedObject;
                        if (server.checkRegister(register.getUsername()).equals("already signed-up")) {
                            String msg = "Already registered";
                            System.out.println(register.getUsername());
                            objectOutputStream.writeObject(msg);
                            System.err.println("object sent to client");
                            objectOutputStream.flush();

                        } else {
                            server.SignUp(register.getUsername(), register.getPassword());
                        }
                        System.out.println("player recieved " + register.getUsername());
                    } else if (recievedObject instanceof LoginModel) {
                        LoginModel signIn = (LoginModel) recievedObject;
                        if (server.getPlayer(signIn.getUsername()) == null) {
                            server.login(signIn.getUsername(), signIn.getPassword());
                        } else {
                            String msg = "Already signned";
                            objectOutputStream.writeObject(msg);
                            objectOutputStream.flush();
                        }
                    }

                }

            } catch (Exception ex) {
                //ex.printStackTrace();
                closeConnection();

                try {
                    closeConnection();
                    socket.close();

//                    if (gameSession.playerOne.socket.isClosed()) {
//                        gameSession.playerTwo.objectOutputStream.writeObject("Player disconnected");
//                    }
//
//                    if (gameSession.playerTwo.socket.isClosed()) {
//                        gameSession.playerOne.objectOutputStream.writeObject("Player disconnected");
//                    }
                    this.stop();
                    ServerMainViewController.clients.remove(this);

                } catch (IOException exe) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public void closeConnection() {
        try {
            ServerMainViewController.clients.remove(this);
            this.stop();
            this.objectInputStream.close();
            this.objectOutputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return "ClientHandler{" + "objectInputStream=" + objectInputStream + ", objectOutputStream=" + objectOutputStream + '}';
    }

}
