/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import models.Player;

/**
 *
 * @author Mahmoud
 */
public class ConnectedPlayer extends Thread implements Initializable {

    private Server server;
    private PrintStream ps;
    private Socket currentSocket;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    Object objectToSend;
    Player player;
    private Boolean loggedin;
    private Boolean updateList;
    private ResultSet result;
    private Thread thread;

    static ArrayList<ConnectedPlayer> activeUsers = new ArrayList(); // online

    static HashMap<String, ConnectedPlayer> game = new HashMap(); // 2 players in game

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedin = false;
        System.out.println("initi");
        result = server.getResultSet();
    }

    public ConnectedPlayer(Socket socket) {
        server = Server.getServer();
        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

            currentSocket = socket;
            this.start();
        } catch (IOException ex) {
            System.out.println("1");
            ex.printStackTrace();
            // alert 
            try {
                socket.close();
            } catch (IOException ex1) {
                Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void run() {
        while (currentSocket.isConnected()) {
            try {
                //clientData = dis.readLine();
                objectToSend = objectInputStream.readObject();

                System.out.println(objectToSend);
                /*if (objectToSend instanceof ObjectToSend) {
                    System.out.println("ObjectToSend   : " + ((ObjectToSend) objectToSend).getFirstName());
                } else if (objectToSend instanceof Player) {
                    System.out.println("ObjectToSend " + ((Player) objectToSend).getUserName());
                    player = (Player) objectToSend;

                } else {
                    System.out.println("MessageWalid   :  " + ((MessageWalid) objectToSend).getName());
                }*/
                /*
                if (clientData != null) {
                    token = new StringTokenizer(clientData, "####");
                    query = token.nextToken();
                    switch (query) {
                        case "SignIn":
                            signIn();
                            break;
                        case "SignUp":
                            signUp();
                            break;
                        case "playerlist":
                            listOnlinePlayers();
                            break;
                        case "request":
                            requestPlaying();
                            break;
                        case "accept":
                            acceptChallenge();
                            break;
                        case "decline":
                            refusedChallenge();
                            break;
                        case "withdraw":
                            withdraw();
                            break;
                        case "gameTic":
                            forwardPress();
                            break;
                        case "finishgameTic":
                            fforwardPress();
                            break;
                        case "updateScore":
                            updateScore();
                            break;
                        case "available":
                            reset();
                            break;
                        case "logout":
                            logout();
                            break;
                        default:
                            break;
                    }
                }*/

            } catch (IOException ex) {
                System.out.println("2");
                System.out.println("Closing try");
                System.out.println("UserName: " + player.getUserName());
               // withdraw();

                if (player.getUserName() != null) {
                    server.setActive(0, player.getUserName());
                    server.setNotPlaying(player.getUserName());
                    activeUsers.remove(this);

                } else {
                    System.out.println("nulllllll");
                    updateList = true;
                }
                try {
                    currentSocket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex1);
                }
                this.stop();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (currentSocket.isClosed()) {
            System.out.println("3");
            System.out.println("close");
            server.getActivePlayers1();
        }
    }

    private void signIn() {
        String userName = player.getUserName();
        String password = player.getPassword();
        String check;
        int score;
        System.out.println(userName + " " + password);
        try {
            check = server.checkSignIn(userName, password);

            if (check.equals("Logged in successfully")) {
                //   score = server.getScore(email);
//                email = server.databaseInstance.getEmail(email);
                userName = server.getUserName(userName).getUserName();
                server.login(userName, password);
            //    ps.println(check + "###" + score);
             //   ps.println(username + "###" + email + "###" + score); // send data to registerController
                loggedin = true;
                activeUsers.add(this);
            } else if (check.equals("This Email is alreay sign-in")) {
                System.out.println("alread in connected");
               // ps.println(check + "###");
            } else if (check.equals("Email is incorrect")) {
              //  ps.println(check + "###");
            } else if (check.equals("Password is incorrect")) {
               // ps.println(check + "###");
            } else if (check.equals("Connection issue, please try again later")) {
               // ps.println(check + "###");
            }
        } catch (SQLException e) {
            //alert
            System.out.println("Connection Issues");
        }
        check = null;
    }

    private void signUp() {
       String userName = player.getUserName();
        String password = player.getPassword();
        System.out.println(userName + " "+ " " + password);
        String check;

        try {
            check = server.checkRegister(userName);
            ps.println(check);
            if (check.equals("Registered Successfully")) {
                ps.println(player); // send data to registerController
                server.SignUp(userName, password);
                System.out.println("User is registered now , check database");
                activeUsers.add(this);
            } else if (check.equals("already signed-up")) {
                ps.println("already signed-up" + "###");
            }
        } catch (SQLException e) {
            //alert
            e.printStackTrace();
            System.out.println("Connection Issues");
        }
    }
/*
    private void listOnlinePlayers() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (ServerMainPageController.serverState) {
                        result = server.getActivePlayers();
                        try {
                            while (result.next()) {
                                ps.println(result.getString("username") + "###"
                                        + result.getString("email") + "###"
                                        + result.getBoolean("isActive") + "###"
                                        + result.getBoolean("isPlaying") + "###"
                                        + result.getInt("score")
                                );
                            }
                            ps.println("null");
                        } catch (SQLException ex) {
                            System.out.println("4");
                            System.out.println("catch");
                            Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        ps.println("close");
                        thread.stop();
                    }
                }
            }
        });
        thread.start();
    }

    private void requestPlaying() { // player1 send request to player 2 (server) 
        String player2Mail = token.nextToken(); // opponent
        String player1Data = token.nextToken(""); // "mail###username"
        for (ConnectedPlayer i : activeUsers) {
            if (i.email.equals(player2Mail)) {
                System.out.println("sending request");
                i.ps.println("requestPlaying");
                i.ps.println(player1Data);
            }
        }
    }

    private void acceptChallenge() {
        System.out.println("accepted");
        String player2 = token.nextToken(); // who recieve the request to play
        String player2Name = token.nextToken();
        String player1 = token.nextToken();
        server.makePlaying(player1, player2);
        ConnectedPlayer p1 = null, p2 = null;
        for (ConnectedPlayer i : activeUsers) {
            if (i.email.equals(player1)) {
                p1 = i;
            } else if (i.email.equals(player2)) {
                p2 = i;
            }
        }
        if (p1 == null || p2 == null) {
            System.out.println("cannot adding player in game , not found, connected player");
        } else {
            game.put(player1, p2);
            game.put(player2, p1);
            p1.ps.println("gameOn");
            p1.ps.println(player2Name);
            p1.ps.println(p2.server.getScore(p2.email));
        }
    }

    private void refusedChallenge() {
        System.out.println("refused");
        String OpponentMail = token.nextToken();
        for (ConnectedPlayer i : activeUsers) {
            if (i.email.equals(OpponentMail)) {
                i.ps.println("decline");
            }
        }
    }

    private synchronized void reset() {
        System.out.println("reset: " + email);
        server.setNotPlaying(email);
    }

    private void forwardPress() {
        String mail = token.nextToken();
        String btn = token.nextToken();
        ConnectedPlayer x = game.get(mail);
        x.ps.println("gameTic");
        x.ps.println(btn);
    }

    private void fforwardPress() {
        String mail = token.nextToken();
        String btn = token.nextToken();
        ConnectedPlayer x = game.get(mail);
        if (game.containsKey(email)) {
            game.remove(x.email);
            game.remove(this.email);
        }
        x.ps.println("finalgameTic");
        x.ps.println(btn);
    }

    private void updateScore() {
        String mail = token.nextToken();
        int score = Integer.parseInt(token.nextToken());
        System.out.println(score);
        server.updateScore(mail, score);
    }

    private void withdraw() {
        ConnectedPlayer cpr = null;
        cpr = game.get(this.email);
        if (cpr != null) {
            cpr.ps.println("withdraw");
            game.remove(this.email);
            game.remove(cpr.email);
        }
    }

    
    private void logout() {
        email = token.nextToken();
        System.out.println("Logout Email " + email);
        if (email != null) {
            server.setActive(false, email);
            activeUsers.remove(email);
        }
        try {
            currentSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}

