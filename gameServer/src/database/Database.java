/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Player;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Mahmoud
 */
public class Database {

    private static Database instanceData;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pst;

    public synchronized ResultSet getResultSet() {
        return rs;
    }

    private Database() throws SQLException {
        DriverManager.registerDriver(new ClientDriver());
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/tictactoee", "root", "root");
    }

    public synchronized static Database getDataBase() throws SQLException {
        if (instanceData == null) {
            instanceData = new Database();
        }
        return instanceData;
    }

    public synchronized void updateResultSet() {

        try {
            this.pst = con.prepareStatement("Select * from playerdata", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.rs = pst.executeQuery(); // rs has all data
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized int getCountOfOfflineUsers() {
        try {
            this.pst = con.prepareStatement("select count(*) from player where status = 0", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet r = pst.executeQuery(); // rs has all data            System.out.println("has next"+r.next());

            return r.next() ? r.getInt(1) : -1;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("catch getactive");
        }
        return -1;
    }

    public synchronized void setActive(int status, String username) {
        try {
            pst = con.prepareStatement("update playerdata set status = ? where username = ?");
            pst.setInt(1, status);
            pst.setString(2, username);
            System.out.println("db mail active:" + username);
            pst.executeUpdate(); // rs has all data
            updateResultSet();
        } catch (SQLException ex) {
            System.out.println("change state to offline catch");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void setNotPlaying(String username) {
        try {
            pst = con.prepareStatement("update playerdata set status = 1 where username = ?");
            pst.setString(1, username);
            pst.executeUpdate();
            updateResultSet();
        } catch (SQLException ex) {
            System.out.println("change state to notplaying catch");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized ResultSet getActivePlayers() {

        try {
            this.pst = con.prepareStatement("Select * from playerdata where status = 1 ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return pst.executeQuery(); // rs has all data
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("catch getactive");
            return null;
        }

    }

    public synchronized void disableConnection() throws SQLException {
        changeStateToOffline();
        changeStateToNotPlaying();

        rs.close();
        pst.close();
        con.close();
        instanceData = null;
    }

    public synchronized void changeStateToNotPlaying() {
        try {
            pst = con.prepareStatement("update playerdata set status = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setInt(1, 1);
            pst.executeUpdate(); // rs has all data
            updateResultSet();
        } catch (SQLException ex) {
            System.out.println("change state to offline");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void changeStateToOffline() {
        try {
            pst = con.prepareStatement("update playerdata set status = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setInt(1, 0);
            pst.executeUpdate(); // rs has all data
            updateResultSet();
        } catch (SQLException ex) {
            System.out.println("change state to offline");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public synchronized void login(String username, String password) throws SQLException {

        pst = con.prepareStatement("update playerdata set status = ?  where username = ? and password = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pst.setInt(1, 1);
        pst.setString(2, username);
        pst.setString(3, password);
        pst.executeUpdate(); // rs has all data
        updateResultSet();
    }

    public synchronized void SignUp(String username, String password) throws SQLException {

        pst = con.prepareStatement("insert into playerdata(username,password) values(?,?)");
        pst.setString(1, username);
        pst.setString(2, password);
        pst.executeUpdate(); // rs has all data
        login(username, password);

    }

    public synchronized String checkRegister(String username) {
        ResultSet checkRs;
        PreparedStatement pstCheck;

        try {
            //        String queryString= new String("select username from player where username = ?");
            pstCheck = con.prepareStatement("select * from playerdata where username = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            if (checkRs.next()) {
                return "already signed-up";
            }
        } catch (SQLException ex) {
            System.out.println("here ");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Registered Successfully";
    }

    public synchronized String checkSignIn(String username, String password) {
        ResultSet checkRs;
        PreparedStatement pstCheck;
        String check;
        System.out.println("checkSignIn " + checkIsActive(username));
        if (checkIsActive(username) == 1) {
            System.out.println(" checkSignIn: " + checkIsActive(username));
            try {
                pstCheck = con.prepareStatement("select * from playerdata where username = ? ");
                pstCheck.setString(1, username);
                checkRs = pstCheck.executeQuery();
                if (checkRs.next()) {
                    if (password.equals(checkRs.getString("password"))) {
                        return "Logged in successfully";
                    }
                    return "Password is incorrect";
                }
                return "Email is incorrect";
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                return "Connection issue, please try again later";
            }
        } else {
            System.out.println("This Email alreay sign-in " + checkIsActive(username));
            return "This Email is alreay sign-in";
        }

    }

    public synchronized int getScore(String username) {
        int score;
        ResultSet checkRs;
        PreparedStatement pstCheck;

        try {
            pstCheck = con.prepareStatement("select * from playerdata where username = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            score = checkRs.getInt("win");

            return score * 5;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized void updateWin(String username) {
        try {
            pst = con.prepareStatement("update playerdata set win = win +1  where username = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //  pst.setInt(1, win);
            pst.setString(1, username);
            pst.executeUpdate();
            updateResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void updateLose(String username) {
        try {
            pst = con.prepareStatement("update playerdata set lose = lose +1  where username = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //  pst.setInt(1, win);
            pst.setString(1, username);
            pst.executeUpdate();
            updateResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void updateDraw(String username) {
        try {
            pst = con.prepareStatement("update playerdata set draw = draw +1  where username = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //  pst.setInt(1, win);
            pst.setString(1, username);
            pst.executeUpdate();
            updateResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized Player getPlayer(String username) {
        Player player = null;
        ResultSet checkRs;
        PreparedStatement pstCheck;
        try {
            pstCheck = con.prepareStatement("select * from playerdata where username = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            while (checkRs.next()) {
                player = new Player(
                        checkRs.getString("username"),
                        checkRs.getString("password"),
                        checkRs.getInt("status"),
                        checkRs.getInt("win"),
                        checkRs.getInt("lose"),
                        checkRs.getInt("draw")
                        //checkRs.getInt("totalscore")
                );
            }

            return player;
        } catch (SQLException ex) {
            System.out.println("Invalid Username");
        }
        return player;
    }

    public synchronized void makePlaying(Player player1, Player player2) {
        try {
            pst = con.prepareStatement("update playerdata set status = 2  where username = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, player1.getUserName());
            pst.executeUpdate(); // rs has all data
            pst = con.prepareStatement("update playerdata set status = 2  where username = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, player2.getUserName());
            pst.executeUpdate();
            updateResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int checkIsActive(String username) {
        ResultSet checkRs;
        PreparedStatement pstCheck;
        int status;
        try {
            pstCheck = con.prepareStatement("select status from playerdata where username = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            System.out.println("checkIsActive true ");
            status = checkRs.getInt("status");
            System.out.println("checkIsActive " + status);
            return status;
        } catch (SQLException ex) {
            System.out.println("Invalod Email address");
            //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized boolean checkPlaying(String player) {

        boolean available;
        ResultSet checkAv;
        PreparedStatement pstCheckAv;
        try {
            pstCheckAv = con.prepareStatement("select * from playerdata where username = ?");
            pstCheckAv.setString(1, player);
            checkAv = pstCheckAv.executeQuery();
            checkAv.next();
            available = checkAv.getBoolean(4);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public synchronized int getWinScore(String userName) {

        int win;
        ResultSet checkRs;
        PreparedStatement pstCheck;

        try {
            pstCheck = con.prepareStatement("select * from playerdata where username = ?");
            pstCheck.setString(1, userName);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            win = checkRs.getInt("win");

            return win;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    public synchronized int getLoseScore(String userName) {

        int lose;
        ResultSet checkRs;
        PreparedStatement pstCheck;

        try {
            pstCheck = con.prepareStatement("select * from playerdata where username = ?");
            pstCheck.setString(1, userName);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            lose = checkRs.getInt("lose");

            return lose;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    public synchronized int getDrawScore(String userName) {

        int draw;
        ResultSet checkRs;
        PreparedStatement pstCheck;

        try {
            pstCheck = con.prepareStatement("select * from playerdata where username = ?");
            pstCheck.setString(1, userName);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            draw = checkRs.getInt("draw");

            return draw;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    public synchronized int calculateTotalGames(String userName) {
        return getWinScore(userName) + getDrawScore(userName) + getLoseScore(userName);
    }
}
