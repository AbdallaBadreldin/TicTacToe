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
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.LoginModel;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginImage.setImage(new Image("/Gallary/loginImage.png"));
        passwordText.setFocusTraversable(false);
        feedbackDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        feedbackDialog.setDialogContainer(root);
        okBnt.setOnAction((e) -> {
            feedbackDialog.close();
            passwordText.setText("");
            emailText.setText("");
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
            ///TODO: send sigin in request to the server and if it is valied, navigate to online board.
            changeSceneToOnlineGame(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void signInBtn() {
        LoginModel obj;
        obj = new LoginModel(emailText.getText(), passwordText.getText());
        System.out.println("Object output" + IPOfServerController.objOutputStream.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (IPOfServerController.objOutputStream != null) {
                    try {
                        IPOfServerController.objOutputStream.writeObject(obj);
                        System.out.println("Data Sent " + obj.toString());
                        if (IPOfServerController.objInputStream != null) {
                            try {
                                Object objToRead = IPOfServerController.objInputStream.readObject();
                                if (objToRead instanceof String) {
                                    ReusableDialog dialog = new ReusableDialog();
                                    dialog.inValidIp((String) objToRead);
                                } else {

                                }
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    } catch (IOException ex) {
                        closeConnection();
                    }
                } else {
                    ReusableDialog dialog = new ReusableDialog();
                    dialog.showErrorDialog("Cannot send request to the server!\nPlease check your connection.","Connection test");
                    closeConnection();
                }

            }
        }).start();

        
    }

    public void changeSceneToOnlineGame(ActionEvent event) throws IOException {

        signInBtn();

        try {
            navigator.navigateTo(event, Navigation.PLAYER_ONLINE);
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void closeConnection() {
        try {
            IPOfServerController.listener.stop();
            IPOfServerController.objInputStream.close();
            IPOfServerController.objOutputStream.close();
            IPOfServerController.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
