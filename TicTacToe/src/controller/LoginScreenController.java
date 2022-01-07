package controller;

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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import helpers.ReusableDialog;
import helpers.SocketSingleton;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Player;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class LoginScreenController implements Initializable {

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
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog feedbackDialog;
    @FXML
    private JFXButton okBnt;
    @FXML
    private Label lab;
    @FXML
    private JFXButton imgBtn;
    @FXML
    private JFXButton signInBtn;

    private final Navigation navigator = new Navigation();
    Thread listener;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //loginImage.setImage(new Image("/Gallary/loginImage.png"));
        passwordText.setFocusTraversable(false);
        feedbackDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        feedbackDialog.setDialogContainer(root);
        okBnt.setOnAction((e) -> {
            feedbackDialog.close();
            passwordText.setText("");
            emailText.setText("");
        });

        ReusableDialog dialog = new ReusableDialog();

        listener = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object object = SocketSingleton.getObjectInputStream().readObject();
                        if (object != null) {
                            if (object instanceof String) {
                                String loginResultString = (String) object;
                                switch (loginResultString) {
                                    case "Password is incorrect":
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.showErrorDialog(loginResultString, "Signned In Failed");
                                            }
                                        });

                                        break;
                                    case "Username is incorrect":
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.showErrorDialog(loginResultString, "Signned In Failed");
                                            }
                                        });
                                        break;
                                    case "Logged in successfully":
                                        //Player player = (Player) object;
                                        //System.out.println(player.toString());
                                        Stage stage = (Stage) signInBtn.getScene().getWindow();
                                        navigator.navigateTo(stage, Navigation.PLAYER_ONLINE);
                                        break;
                                    case "This username is alreay sign-in":
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.showErrorDialog(loginResultString, "Signned In Failed");
                                            }
                                        });
                                        break;
                                    case "Connection issue, please try again later":
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.showErrorDialog(loginResultString, "Signned In Failed");
                                            }
                                        });
                                        break;

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

    @FXML
    private void passwordText(MouseEvent event) {

    }

    @FXML
    private void emailText(MouseEvent event) {

    }

    @FXML
    private void rememberCheckBox(ActionEvent event) {
    }

    @FXML
    private void signUp(MouseEvent event) {
        try {
            navigator.navigateTo(event, Navigation.REGISTER_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onBackImageClick(MouseEvent event) {
    }

    @FXML
    private void onSignInBtn(ActionEvent event) {
        try {
            Player player = new Player(emailText.getText(), passwordText.getText());
            SocketSingleton.getObjectOutputStream().writeObject(player);
            if (!listener.isAlive()) {
                listener.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
