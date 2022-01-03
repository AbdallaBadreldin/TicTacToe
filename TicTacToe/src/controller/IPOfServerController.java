package controller;

import helpers.Navigation;
import java.io.IOException;
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

    private final Navigation nav = new Navigation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onConnectBtnClick(MouseEvent event) {
        try {
            nav.navigateTo(event, Navigation.LOGIN_SCREEN);
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

}
