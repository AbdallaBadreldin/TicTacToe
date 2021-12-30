
package controller;

import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Abdo
 */
public class RecordesScreenController implements Initializable {

    @FXML
    private ImageView backImg;
    @FXML
    private ImageView exitImg;
    
    private final Navigation nav = new Navigation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onBackImageClick(MouseEvent event) {
        try {
            nav.navigateTo(event, Navigation.MAIN_SCREEN);
        } catch (IOException ex) {
            ex.getMessage();
            Logger.getLogger(RecordesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onExitImageClick(MouseEvent event) {
    }

    
}
