package controller;

import client.GameClient;
import com.jfoenix.controls.JFXButton;
import helpers.IPValidation;
import helpers.Navigation;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
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

    static boolean checkip = false;
    static Socket socket;
    Preferences pref;
    static DataInputStream dis;
    static PrintStream ps;

    private final Navigation nav = new Navigation();
    private GameClient client;
    @FXML
    private JFXButton imgBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onConnectBtnClick(MouseEvent event) {
        try {
            changeSceneToOnlineGame(event);
        } catch (IOException ex) {
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean connection(String s) {

        if (IPValidation.isValidIPAddress(s)) {
            System.out.println("enter try valip ip");
            if (socket == null || socket.isClosed()) {
                /* socket = new Socket(s, 9081);
                System.out.println("conncet valid ip ");
                System.out.println(IPValidation.getIp());
                dis = new DataInputStream(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            try {
                                client = GameClient.getInstactance(s, 5006);
                            } catch (IOException ex) {
                                System.out.println("Could not connect");
                                Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                    }
                }).start();
            }
            return true;

        } else {
            serverIpTextField.setPromptText("Please Enter Valid Ip");
            serverIpTextField.selectAll();
            checkip = false;
            return false;
        }
    }

    public void changeSceneToOnlineGame(MouseEvent event) throws IOException {
        System.out.println(checkip);

        Boolean isCancled = false;

        System.out.println("you entered ip =" + serverIpTextField.getText());

        if (!isCancled) {
            boolean conn = connection(serverIpTextField.getText());
            if (conn) {
                checkip = true;

                //System.out.println("socket is " + socket.isConnected() + " from Ip server controller");
                nav.navigateTo(event, Navigation.LOGIN_SCREEN);
            } else {
                serverIpTextField.setPromptText("You entered a wrong ip please try again");
                serverIpTextField.selectAll();

            }

        }
    }

}
