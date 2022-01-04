package controller;

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
    @FXML
    private ImageView exitImage;

    static boolean checkip = false;
    static Socket socket;
    Preferences pref;
    static DataInputStream dis;
    static PrintStream ps;

    private final Navigation nav = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        System.out.println("clicked.");
        try {
            nav.navigateTo(event, Navigation.MAIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onExitImageClick(MouseEvent event) {
    }

    public boolean connection(String s) {

        if (IPValidation.isValidIPAddress(s)) {
            try {
                System.out.println("enter try valip ip");
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(s, 9081);
                    System.out.println("conncet valid ip ");
                    System.out.println(IPValidation.getIp());
                    dis = new DataInputStream(socket.getInputStream());
                    ps = new PrintStream(socket.getOutputStream());
                }

                return true;
            } catch (IOException ex) {
                try {
                    System.out.println("closing socket in IPOfServerController");
                    if (socket != null) {
                        socket.close();
                        dis.close();
                        ps.close();
                    }

                } catch (IOException ex1) {
                    Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                return false;
            }

        } else {
            serverIpTextField.setText("Please Enter Valid Ip");
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

                System.out.println("socket is " + socket.isConnected() + " from Ip server controller");
                nav.navigateTo(event, Navigation.LOGIN_SCREEN);
            } else {
                serverIpTextField.setText("You entered a wrong ip please try again");
                serverIpTextField.selectAll();

            }

        }
    }

}
