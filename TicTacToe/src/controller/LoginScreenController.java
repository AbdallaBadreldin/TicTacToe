package controller;

import client.GameClient;
import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Common;
import models.LoginRequest;
import models.Player;
import client.interfaces.SignInInterface;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class LoginScreenController implements Initializable, SignInInterface {

    @FXML
    private ImageView backImage;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField emailText;
    @FXML
    private AnchorPane loginStage;
    @FXML
    private ImageView loginImage;
    private GameClient gameClient;
    private final Navigation nav = new Navigation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginImage.setImage(new Image("/Gallary/loginImage.png"));
        passwordText.setFocusTraversable(false);
        try {
            gameClient = GameClient.getInstactance(Common.IP, Common.PORT);
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void passwordText(MouseEvent event) {
        passwordText.clear();
    }

    @FXML
    private void emailText(MouseEvent event) {
        emailText.clear();
    }

    @FXML
    private void rememberCheckBox(ActionEvent event) {
    }

    @FXML
    private void signInBtn(ActionEvent event) {
        gameClient.setSignInInterface(this);
        try {
            gameClient.sendRequest(new LoginRequest(emailText.getText(), passwordText.getText()));
            gameClient.startReading();
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signUp(MouseEvent event) {
        try {
            nav.navigateTo(event, Navigation.REGISTER_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void backMouseClicked(MouseEvent event) {
    }

    @Override
    public void onPlayerRevice(Player player) {
        if (player.getUserName() == null) {
            System.out.println("data is not vailed.");
        } else {
            ///TODO: navigate to inGameSreacn;
            System.out.println("DONE!s " + player.getUserName());
            try {
                gameClient.stopReading();
                //nav.navigateTo(event, Navigation.ONLINE_SCREEN);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
