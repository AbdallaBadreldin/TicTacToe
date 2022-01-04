/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.Database;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Player;

/**
 *
 * @author Mahmoud
 */
public class Server {

    private static Server server;
    public Database databaseInstance;

    private Socket socket;
    private Thread listener;
    Player player;
    public static Vector<ClientHandler> clients;
    static ServerSocket serverSocket;
    static Thread socketAccpetListener;
//    public ResultSet rs ;

    private Server() {
        //server = new Server();
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
            listener.stop();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.print("disable connection server");
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        //alert connection issue

    }

    private void initServer() {
        clients = new Vector<ClientHandler>();
        try {
            serverSocket = new ServerSocket(5006);
            System.out.println("ServerStarted");
            socketAccpetListener = new Thread(() -> {
                while (true) {
                    try {
                        Socket s = serverSocket.accept();
                        clients.add(new ClientHandler(s));
                        System.out.println("Clients number : " + clients.size());
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            socketAccpetListener.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setActive(int status, String userName) {
        databaseInstance.setActive(0, userName);
    }

    public void setNotPlaying(String userName) {
        databaseInstance.setNotPlaying(userName);
    }

    public void getActivePlayers1() {
        databaseInstance.getActivePlayers();
    }

    public String checkSignIn(String userName, String password) {
        return databaseInstance.checkSignIn(userName, password);
    }

    public int getScore(String userName) {
        return databaseInstance.getScore(userName);
    }

    public Player getUserName(String username) {
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

    public void makePlaying(Player player1, Player player2) {
        databaseInstance.makePlaying(player1, player2);
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
