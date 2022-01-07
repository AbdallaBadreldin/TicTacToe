package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.LoginScreenController;
import helpers.Navigation;
import helpers.ReusableDialog;
import helpers.SocketSingleton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Player;
import model.PlayerMove;
import model.RegistrationModel;

/**
 *
 * @author Radwa
 */
public class RegisterScreenController implements Initializable {

    @FXML
    private AnchorPane loginStage;
    @FXML
    private ImageView backImage;
    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXButton imgBtn;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXPasswordField confirmPasswordTxt;

    private final Navigation navigator = new Navigation();
    //private Player player;
    Thread listener;
    @FXML
    private JFXButton signUpBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //registerImage.setImage(new Image("/Gallary/loginImage.png"));
        confirmPasswordTxt.setFocusTraversable(false);
        passwordTxt.setFocusTraversable(false);

        ReusableDialog dialog = new ReusableDialog();

        listener = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object object = SocketSingleton.getObjectInputStream().readObject();
                        if (object != null) {
                            if (object instanceof String) {
                                String registerRequest = (String) object;
                                if (registerRequest.equals("Already registered")) {
                                    //player not found
                                    Platform.runLater(new Runnable() { 
                                        @Override
                                        public void run() {
                                            dialog.showErrorDialog(registerRequest, "Registeration Failed");
                                        }
                                    });
                                } else if (registerRequest.equals("Registered Successfully")) {
                                    Stage stage = (Stage) signUpBtn.getScene().getWindow();
                                    navigator.navigateTo(stage, Navigation.PLAYER_ONLINE);
                                }
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        listener.stop();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                        listener.stop();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        listener.stop();
                    }
                }
            }
        });

    }

    private void passwordText(MouseEvent event) {
        passwordTxt.clear();
    }

    @FXML
    private void signInMouseClicked(MouseEvent event) {
        try {
            navigator.navigateTo(event, Navigation.LOGIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void backMouseClicked(MouseEvent event) {
        try {
            navigator.navigateTo(event, Navigation.LOGIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void usernameText(MouseEvent event) {
        usernameTxt.clear();
    }

    private void confirmPasswordText(MouseEvent event) {
        confirmPasswordTxt.clear();
    }

    @FXML
    private void onUsernameTxtAction(ActionEvent event) {
    }

    @FXML
    private void onPasswordTxtAction(ActionEvent event) {
    }

    @FXML
    private void onConfirmPasswordTxtAction(ActionEvent event) {
    }

    @FXML
    private void onSignUp(ActionEvent event) {
        System.out.println("signup tapped");
        ReusableDialog dialog = new ReusableDialog();
        if (passwordTxt.getText().equals(confirmPasswordTxt.getText())) {
            try {
                RegistrationModel playerToRegister = new RegistrationModel(usernameTxt.getText(), passwordTxt.getText());
                SocketSingleton.getObjectOutputStream().writeObject(playerToRegister);
                if (!listener.isAlive()) {
                    listener.start();
                }

            } catch (IOException ex) {
                Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    dialog.inValidIp("Please enter the same password");
                }
            });
        }

    }
}
