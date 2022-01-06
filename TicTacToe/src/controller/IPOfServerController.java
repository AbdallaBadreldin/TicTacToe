package controller;


import com.jfoenix.controls.JFXButton;
import helpers.IPValidation;
import helpers.Navigation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.GameOnlineMode;

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
    static Socket socket = null;
    static ObjectInputStream objInputStream = null;
    static ObjectOutputStream objOutputStream = null;
    static Thread listener;

    private GameOnlineMode game;

    private final Navigation nav = new Navigation();
    //private GameClient client;
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socket = new Socket(s, 5006);

                            objOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objInputStream = new ObjectInputStream(socket.getInputStream());
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
                nav.navigateTo(event, Navigation.LOGIN_SCREEN);
            } else {
                serverIpTextField.setPromptText("You entered a wrong ip please try again");
                serverIpTextField.selectAll();

            }

        }
    }

}
