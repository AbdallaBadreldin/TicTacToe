package tictactoe;

import controller.SplashScrennController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Mahmoud
 */
public class TicTacToe extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new SplashScrennController().startScreen();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(750);
        stage.setMinWidth(1000);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(600);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
