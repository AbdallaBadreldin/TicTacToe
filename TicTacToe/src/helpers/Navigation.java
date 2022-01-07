package helpers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Mahmoud
 */
public class Navigation {

    public final static String PLAYER_VS_AI = "/view/PlayerVsAi.fxml";
    public final static String PLAYER_ONLINE = "/view/OnlinePlayerList.fxml";
    public final static String MAIN_GRID_PAIN = "/view/MainGridPane.fxml";
    public final static String RECORDERS_SCREEN = "/view/RecordesScreen.fxml";
    public final static String MAIN_SCREEN = "/view/MainScreen.fxml";
    public final static String IP_OF_SERVER = "/view/IPOfServer.fxml";
    //public final static String ONLINE_PLAYER = "/view/OnlinePlayers.fxml";
    public final static String LOGIN_SCREEN = "/view/LoginScreen.fxml";
    public final static String REGISTER_SCREEN = "/view/RegisterScreen.fxml";
    public final static String HARD_MODE = "/view/HardModeView.fxml";

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void navigateTo(ActionEvent event, String destination) throws IOException {
        root = FXMLLoader.load(getClass().getResource(destination));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateTo(Event event, String destination) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    root = FXMLLoader.load(getClass().getResource(destination));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public void navigateTo(Stage stage, String destination) throws IOException {
           Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    root = FXMLLoader.load(getClass().getResource(destination));
                    //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        root = FXMLLoader.load(getClass().getResource(destination));
//        this.stage = stage;
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

}
