package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import helpers.Navigation;
import helpers.ReusableDialog;
import java.io.IOException;
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
    private ImageView registerImage;
    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXButton imgBtn;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXPasswordField confirmPasswordTxt;

    private final Navigation navigator = new Navigation();
    private Player player;
    @FXML
    private JFXButton signUpBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerImage.setImage(new Image("/Gallary/loginImage.png"));
        confirmPasswordTxt.setFocusTraversable(false);
        passwordTxt.setFocusTraversable(false);
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
        ReusableDialog dialog = new ReusableDialog();
        if (passwordTxt.getText().equals(confirmPasswordTxt.getText())) {
            boolean registered = checkRegister();
            if (registered) {
                signUpBtn();
                try {
                    navigator.navigateTo(event, Navigation.PLAYER_ONLINE);
                } catch (IOException ex) {
                    Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void signUpBtn() {
        RegistrationModel obj;
        ReusableDialog dialog = new ReusableDialog();

        obj = new RegistrationModel(usernameTxt.getText(), passwordTxt.getText());
        System.out.println("Object output" + IPOfServerController.objOutputStream.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (IPOfServerController.objOutputStream != null) {
                    try {
                        IPOfServerController.objOutputStream.writeObject(obj);
                        System.out.println("Data Sent " + obj.toString());

                    } catch (IOException ex) {
                        Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    dialog.showErrorDialog("Cannot send request to the server!\nPlease check your connection.","Connection Test");
                    closeConnection();
                }

            }
        }).start();

    }

    private boolean checkRegister() {
        ReusableDialog dialog = new ReusableDialog();
        boolean registered = false;
        if (IPOfServerController.objInputStream != null) {
            try {
                Object objToRead = IPOfServerController.objInputStream.readObject();
                if (objToRead instanceof String) {
                    dialog.showErrorDialog((String) objToRead,"Registation Failed");
                    registered = false;
                } else {
                    registered = true;
                }
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return registered;
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

/*
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            dialog.showErrorDialog((String) objToRead);
                            
                        }
                    });

        IPOfServerController.listener = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (IPOfServerController.objOutputStream != null) {
                        try {
                            IPOfServerController.objOutputStream.writeObject(obj);
                            System.out.println("Object Recieved");
                            if (obj instanceof Player) {
                                Player player = (Player) obj;
                                player.setUserName(usernameTxt.getText());
                                player.setPassword(passwordTxt.getText());
                            } else if (obj instanceof String) {
                                showErrorDialog((String) obj);
                                this.stop();
                            }

                        } catch (IOException | ClassNotFoundException ex) {

                            showErrorDialog("Server disconnected");
                            closeConnection();
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("ObjectStream is null");
                    }
                }
            }
        };
        IPOfServerController.listener.setDaemon(true);
        IPOfServerController.listener.start();
 */
