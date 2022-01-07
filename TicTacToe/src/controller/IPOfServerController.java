package controller;

import com.jfoenix.controls.JFXButton;
import helpers.IPValidation;
import helpers.Navigation;
import helpers.ReusableDialog;
import helpers.SocketSingleton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Abdo
 */
public class IPOfServerController implements Initializable {

    @FXML
    private TextField serverIpTextField;
    @FXML
    private Button connectBtn;
    @FXML
    private ImageView backImage;

    private final Navigation nav = new Navigation();
    ReusableDialog dialog = new ReusableDialog();
    @FXML
    private JFXButton imgBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onConnectBtnClick(MouseEvent event) {
        System.out.println("connect");
        //Thread thread = new Thread();
        if (IPValidation.isValidIPAddress(serverIpTextField.getText())) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Socket socket = SocketSingleton.getInstanceOf(serverIpTextField.getText());
                    if (socket != null && socket.isConnected()) {
                        try {
                            nav.navigateTo(event, Navigation.LOGIN_SCREEN);
                        } catch (IOException ex) {
                            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                dialog.showErrorDialog("Server is not available\nPlease try again later", "Test Connection");
                            }
                        });
                    }

                }
            }).start();
        } else {
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    dialog.showErrorDialog("Invalid IP Please\nPlease enter a valid one", "Invalid IP address");
                }
            });
        }

    }

    @FXML
    private void onBackImageClick(MouseEvent event) {
        try {
            nav.navigateTo(event, Navigation.MAIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
