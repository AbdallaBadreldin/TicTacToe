/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ServerMainViewController;
import database.Database;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud
 */
public class Server {

    private static Server server;
    public Database databaseInstance;

    static ServerSocket serverSocket;
    static Thread socketAccpetListener;
    //public static Vector<ClientHandler> clients;
//    public ResultSet rs ;

    private Server() {

    }

    public static Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public void enableConnections() throws SQLException {

        databaseInstance = Database.getDataBase();
        databaseInstance.changeStateToOffline();
        databaseInstance.changeStateToNotPlaying();
        databaseInstance.updateResultSet();
        initServer(); // enable socket server
    }

    public void disableConnections() {
        try {
            databaseInstance.disableConnection();
            //listener.stop();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.print("disable connection server");
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        //alert connection issue

    }

    private void initServer() {
        ServerMainViewController.clients = new Vector<ClientHandler>();
        try {
            serverSocket = new ServerSocket(5006);
            System.out.println("ServerStarted");
            socketAccpetListener = new Thread(() -> {
                while (true) {
                    System.out.println("Socket Listener Thread");
                    try {
                        Socket s = serverSocket.accept();
                        ServerMainViewController.clients.add(new ClientHandler(s));
                        System.out.println("Clients number : " + ServerMainViewController.clients.size());
                        for (int i = 0; i < ServerMainViewController.clients.size(); i++) {
                            System.out.println(ServerMainViewController.clients.get(i).toString());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            socketAccpetListener.setDaemon(true);
            socketAccpetListener.start();
        } catch (IOException ex) {

        }
    }

    public void stopServer() {
        try {
            for (int i = 0; i < ServerMainViewController.clients.size(); i++) {

                ServerMainViewController.clients.get(i).closeConnection();
                ServerMainViewController.clients.remove(this);
            }
            ServerMainViewController.clients.clear();
            socketAccpetListener.stop();
            serverSocket.close();

            System.out.println("ServerStopped");
        } catch (IOException ex) {
            try {
                serverSocket.close();
            } catch (IOException ex1) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void setActive(Boolean state, String mail) {
        databaseInstance.setActive(false, mail);
    }

    public void setNotPlaying(String email) {
        databaseInstance.setNotPlaying(email);
    }

    public void getActivePlayers1() {
        databaseInstance.getActivePlayers();
    }

    public String checkSignIn(String username, String password) {
        return databaseInstance.checkSignIn(username, password);
    }

    public int getScore(String username) {
        return databaseInstance.getScore(username);
    }

    public Player getPlayer(String username) {  
        return databaseInstance.getPlayer(username);
    }

    public void login(String username, String password) throws SQLException {
        databaseInstance.login(username, password);
    }

    public String checkRegister(String username) {
        return databaseInstance.checkRegister(username);
    }

    public void SignUp(String username, String password) throws SQLException {
        databaseInstance.SignUp(username, password);
    }

    public ResultSet getActivePlayers() {
        return databaseInstance.getActivePlayers();
    }

    public void updateScore(String mail, int score) {
        databaseInstance.updateScore(mail, score);
    }


    public ResultSet getResultSet() {
        return databaseInstance.getResultSet();
    }


    public int getWinScore(String userName) {
        return databaseInstance.getWinScore(userName);
    }

    public int getLoseScore(String userName) {
        return databaseInstance.getLoseScore(userName);
    }

    public int getDrawScore(String userName) {
        return databaseInstance.getDrawScore(userName);
    }

    public int getTotalMatches(String userName) {
        return databaseInstance.calculateTotalGames(userName);
    }

}
