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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class DialogController implements Initializable {

    @FXML
    private ImageView dialogImage;
    @FXML
    private Button rematchBtn;
    @FXML
    private Button exitBtn;
        Navigation navigator = new Navigation();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void rematchBtnAction(ActionEvent event) {
        try {
            navigator.navigateTo(event, Navigation.MAIN_GRID_PAIN);
        } catch (IOException ex) {
            Logger.getLogger(DialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void exitBtnAction(ActionEvent event) {
        try {
            navigator.navigateTo(event, Navigation.MAIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(DialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
