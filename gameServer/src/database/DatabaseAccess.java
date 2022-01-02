package database;

import models.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Mahmoud
 */
public class DatabaseAccess {

    private final static String USER_NAME = "root";
    private final static String PASSWORD = "1234";
    private final static String URL = "jdbc:derby://localhost:1527/root";

    static Connection connection;
    static Statement statement;

    public static void setUpConnection() {
        try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //0 get All player From DataBase
    public static List<Player> selectAllPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String queryString = "SELECT * FROM playerdata ORDER BY win DESC";
            ResultSet resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                Player player = new Player();
                player.setUserName(resultSet.getString("username"));
                player.setWin(resultSet.getInt("win"));
                if (resultSet.getInt("status") == 0) {
                    player.setStatus(0);
                } else {
                    player.setStatus(1);
                }
                players.add(player);
            }
            statement.close();
            return players;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

    public static List<Player> selectActivePlayers() {
        List<Player> players = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM playerdata WHERE status = 1 order by win DESC");
            while (resultSet.next()) {
                Player player = new Player();
                player.setUserName(resultSet.getString("username"));
                player.setWin(resultSet.getInt("win"));
                if (resultSet.getInt("status") == 0) {
                    player.setStatus(0);
                } else {
                    player.setStatus(1);
                }
                players.add(player);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

    public static List<Player> selectInActivePlayers() {
        List<Player> players = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM playerdata WHERE status = 0 order by win DESC");
            while (resultSet.next()) {
                Player player = new Player();
                player.setUserName(resultSet.getString("username"));
                player.setWin(resultSet.getInt("win"));
                players.add(player);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }
    
    public static List<Player> selectActiveAndInGamesPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM playerdata WHERE status = 3 order by win DESC");
            while (resultSet.next()) {
                Player player = new Player();
                player.setUserName(resultSet.getString("username"));
                player.setWin(resultSet.getInt("win"));
                players.add(player);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

    //1
    public static int signUpPlayer(Player player) {
        ResultSet resultForSignUp;
        int result;
        PreparedStatement preparedStatment;
        try {
            String query = "SELECT * FROM playerdata where username= ?";

            preparedStatment = connection.prepareStatement(query);

            preparedStatment.setString(1, player.getUserName());

            resultForSignUp = preparedStatment.executeQuery();

            if (resultForSignUp.next()) {
                return 1;
            } else {
                String queryInsert = "INSERT INTO playerdata (username,password) VALUES (? , ?)";

                preparedStatment = connection.prepareStatement(queryInsert);

                preparedStatment.setString(1, player.getUserName());
                preparedStatment.setString(2, player.getPassword());

                result = preparedStatment.executeUpdate();

                preparedStatment.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    //2
    public static Player login(String userName, String password) {
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM playerdata where username='" + userName + "' and password = " + password);
            if (resultSet.next()) {
                if (setStatusActive(userName) == 1) {
                    return getPlayer(userName);
                }
                System.out.println(userName);
            }
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    //3
    public static int setStatusActive(String userName) {
        int result;
        try {
            String query = "UPDATE playerdata SET status = 1 WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setString(1, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    //4
    public static Player getPlayer(String userName) {
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM playerdata where username='" + userName + "'");
            if (resultSet.first()) {
                Player player = new Player();
                player.setUserName(resultSet.getString("username"));
                player.setWin(resultSet.getInt("win"));
                if (resultSet.getInt("status") == 0) {
                    player.setStatus(0);
                } else {
                    player.setStatus(1);
                }
                return player;
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    //5
    public static int setStatusInactive(String userName) {
        int result;
        try {
            String query = "UPDATE playerdata SET status = 0 WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setString(1, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    //6
    public static int updateWin(Player player) {
        int result;
        try {
            int win = player.getWin();
            win++;
            String query = "UPDATE playerdata SET win = ? WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setInt(1, win);
            preparedStatment.setString(2, player.getUserName());

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    //7
    public static int updateDraw(Player player) {
        int result;
        try {
            int draw = player.getDraw();
            draw++;
            String query = "UPDATE playerdata SET draw = ? WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setInt(1, draw);
            preparedStatment.setString(2, player.getUserName());

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    //8
    public static int updateLose(Player player) {
        int result;
        try {
            int lose = player.getLose();
            lose++;
            String query = "UPDATE playerdata SET lose = ? WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setInt(1, lose);
            preparedStatment.setString(2, player.getUserName());

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    public static int updateInGameStatus(String userName) {
        int result;
        try {
            String query = "UPDATE playerdata SET status = 2 WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setString(1, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    // By server
    // method called by server to set the username offline
    public static int setOffline(String userName) {
        int result;
        try {
            String query = "UPDATE playerdata SET status = 0 WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setString(1, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    // method called by server to delete client    
    public static int deleteClient(String userName) {
        int result;
        try {
            String query = "DELETE playerdata WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setString(1, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    // select all funtion
    // reurns result set of all items in the database
    public static ResultSet selectAll() throws SQLException {
        // statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from playerdata order by win DESC");
        return resultSet;
    }

    public static int updateWin(String userName) {
        int result;
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");
            int win = 0;

            if (resultSet.first()) {
                win = resultSet.getInt("win");
                win++;
            }
            String query = "UPDATE playerdata SET win = ? WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setInt(1, win);
            preparedStatment.setString(2, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    public static int updateDraw(String userName) {
        int result;
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");
            int draw = 0;

            if (resultSet.first()) {
                draw = resultSet.getInt("draw");
                draw++;
            }
            String query = "UPDATE playerdata SET draw = ? WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setInt(1, draw);
            preparedStatment.setString(2, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    public static int updateLose(String userName) {
        int result;
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");
            int lose = 0;

            if (resultSet.first()) {
                lose = resultSet.getInt("lose");
                lose++;
            }
            String query = "UPDATE playerdata SET draw = ? WHERE username = ? ";
            PreparedStatement preparedStatment = connection.prepareStatement(query);
            preparedStatment.setInt(1, lose);
            preparedStatment.setString(2, userName);

            result = preparedStatment.executeUpdate();

            preparedStatment.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result;
    }

    public static int getWinScore(String userName) {

        ResultSet resultSet;
        int win = 0;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");

            if (resultSet.first()) {
                win = resultSet.getInt("win");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return win;

    }

    public static int getLoseScore(String userName) {

        ResultSet resultSet;
        int lose = 0;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");

            if (resultSet.first()) {
                lose = resultSet.getInt("lose");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lose;

    }

    public static int getDrawScore(String userName) {

        ResultSet resultSet;
        int draw = 0;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");

            if (resultSet.first()) {
                draw = resultSet.getInt("draw");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return draw;

    }

    public static int calculateTotalGames(String userName) {
        return getWinScore(userName) + getDrawScore(userName) + getLoseScore(userName);
    }

    public static int getStatus(String userName) {
        ResultSet resultSet;
        int status = 0;
        try {
            resultSet = statement.executeQuery("SELECT * FROM playerdata where username='"
                    + userName + "'");

            if (resultSet.first()) {
                status = resultSet.getInt("status");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;

    }

}
