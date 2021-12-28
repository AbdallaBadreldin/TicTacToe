/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helpers.Navigation;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.GameSession;
import models.PlayerMove;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class MainGridPaneController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Pane gamePane;
    @FXML
    private GridPane GridPane;
    @FXML
    private ImageView playerOneImageView;
    @FXML
    private Label playerOneNameLbl;
    @FXML
    private Label playerOneScoreLbl;
    @FXML
    private Label playerTwoScoreLbl;
    @FXML
    private ImageView playerTwoImageView;
    @FXML
    private Label playerTwoNameLbl;
    @FXML
    private Pane grid00;
    @FXML
    private Pane grid01;
    @FXML
    private Pane grid02;
    @FXML
    private Pane grid10;
    @FXML
    private Pane grid11;
    @FXML
    private Pane grid12;
    @FXML
    private Pane grid20;
    @FXML
    private Pane grid21;
    @FXML
    private Pane grid22;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    Navigation navigator= new Navigation();
    GameSession gameSession = new GameSession();
    Label label = new Label();
    int playerOneScore =0;
    int playerTwoScore =0;
    private boolean playerTurn = true;
    private boolean firstWinner = false;
    private boolean secondWinner = false;
    private boolean isXSymbol = true;
    private boolean winner;
    @FXML
    private Button backBtn;
  


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerOneImageView.setImage(new Image("/resources/player-one-avatar.jpg"));
        playerTwoImageView.setImage(new Image("/resources/player-two-avatar.jpg"));
    }

    @FXML
    private void handleLabels(MouseEvent mouseEvent) {
        //System.out.println("befire if");
        if(winner==false){
           // System.out.println("after");
            ((Label) mouseEvent.getSource()).setDisable(true);
        gameSession.addMove(returnMove((Label) mouseEvent.getSource()));
        ((Label) mouseEvent.getSource()).setText(returnSymbol());
        checkState();
        }
        
       }
   /* private void removeLine(Label b1, Label b2) {
        Bounds bound1 = b1.localToScene(b1.getBoundsInLocal());
        Bounds bound2 = b2.localToScene(b2.getBoundsInLocal());
        double x1, y1, x2, y2;
        x1 = (bound1.getMinX() + bound1.getMaxX()) / 2;
        y1 = (bound1.getMinY() + bound1.getMaxY()) / 2;
        x2 = (bound2.getMinX() + bound2.getMaxX()) / 2;
        y2 = (bound2.getMinY() + bound2.getMaxY()) / 2;
        Line line = new Line(x1, y1, x2, y2);
        mainPane.getChildren().clear();
    }*/
    private void reMatch()
    {
        System.out.println("controller.MainGridPaneController.reMatch()");
        gamePane.setDisable(false);
        label1.setText("");
        label2.setText("");
        label3.setText("");
        label4.setText("");
        label5.setText("");
        label6.setText("");
        label7.setText("");
        label8.setText("");
        label9.setText("");
        winner=false;
        //gamePane.getChildren().clear();
        //removeLine();
        //if(winner){checkState();}
        
        
        
    }

    private void drawLine(Label b1, Label b2) {
        Bounds bound1 = b1.localToScene(b1.getBoundsInLocal());
        Bounds bound2 = b2.localToScene(b2.getBoundsInLocal());
        double x1, y1, x2, y2;
        x1 = (bound1.getMinX() + bound1.getMaxX()) / 2;
        y1 = (bound1.getMinY() + bound1.getMaxY()) / 2;
        x2 = (bound2.getMinX() + bound2.getMaxX()) / 2;
        y2 = (bound2.getMinY() + bound2.getMaxY()) / 2;
        Line line = new Line(x1, y1, x2, y2);
        mainPane.getChildren().add(line);
    }

    private String returnSymbol() {
        String symbol;
        if (isXSymbol == true) {
            symbol = "X";
        } else {
            symbol = "O";
        }
        isXSymbol = !isXSymbol;
        return symbol;
    }

    private PlayerMove returnMove(Label label) {
        PlayerMove move = new PlayerMove();
        if (label == label1) {
            move = new PlayerMove(0, 0, isXSymbol);
        } else if (label == label2) {
            move = new PlayerMove(0, 1, isXSymbol);
        } else if (label == label2) {
            move = new PlayerMove(0, 2, isXSymbol);
        } else if (label == label3) {
            move = new PlayerMove(1, 0, isXSymbol);
        } else if (label == label4) {
            move = new PlayerMove(1, 1, isXSymbol);
        } else if (label == label5) {
            move = new PlayerMove(1, 2, isXSymbol);
        } else if (label == label6) {
            move = new PlayerMove(2, 0, isXSymbol);
        } else if (label == label7) {
            move = new PlayerMove(2, 1, isXSymbol);
        } else if (label == label8) {
            move = new PlayerMove(2, 2, isXSymbol);
        }
        return move;
    }

    private void checkRows() {
        if (label1.getText().equals(label2.getText())
                && label2.getText().equals(label3.getText())
                && !label1.getText().equals("")) {

            drawLine(label1, label3);
            dialogHandle();

            if (label1.getText().equals("X")) {
                firstWinner = true;
            } else {
                secondWinner = true;
            }
            winner = true;
        } else if (label4.getText().equals(label5.getText())
                && label4.getText().equals(label6.getText())
                && !label4.getText().equals("")) {
            drawLine(label4, label6);
            dialogHandle();

            if (label4.getText().equals("X")) {
                firstWinner = true;

            } else {
                secondWinner = true;

            }
            winner = true;
        } else if (label7.getText().equals(label8.getText())
                && label7.getText().equals(label9.getText())
                && !label9.getText().equals("")) {
            drawLine(label7, label9);
            dialogHandle();

            if (label9.getText().equals("X")) {
                System.out.println("x is winning");
                firstWinner = true;
            } else {
                System.out.println("o is winning");
                secondWinner = true;
            }
            winner = true;
        }

    }

    private void checkColumns() {
        if (label1.getText().equals(label4.getText())
                && label1.getText().equals(label7.getText())
                && !label1.getText().equals("")) {

            drawLine(label1, label7);
            dialogHandle();

            if (label1.getText().equals("X")) {
                firstWinner = true;
            } else {
                secondWinner = true;
            }
            winner = true;
        } else if (label2.getText().equals(label5.getText())
                && label2.getText().equals(label8.getText())
                && !label2.getText().equals("")) {
            drawLine(label2, label8);
            dialogHandle();

            if (label2.getText().equals("X")) {
                firstWinner = true;

            } else {
                secondWinner = true;
            }
            winner = true;
        } else if (label3.getText().equals(label6.getText())
                && label3.getText().equals(label9.getText())
                && !label3.getText().equals("")) {
            drawLine(label3, label9);
            dialogHandle();

            if (label3.getText().equals("X")) {
                System.out.println("x is winning");
                firstWinner = true;
            } else {
                System.out.println("o is winning");
                secondWinner = true;
            }
            winner = true;
        }

    }

    private void checkDiagonal() {
        if (label1.getText().equals(label5.getText())
                && label1.getText().equals(label9.getText())
                && !label1.getText().equals("")) {

            drawLine(label1, label9);
            dialogHandle();

            if (label1.getText().equals("X")) {
                firstWinner = true;
            } else {
                secondWinner = true;
            }
            winner = true;
        } else if (label3.getText().equals(label5.getText())
                && label3.getText().equals(label7.getText())
                && !label3.getText().equals("")) {
            drawLine(label3, label7);
            dialogHandle();

            if (label3.getText().equals("X")) {
                firstWinner = true;

            } else {
                secondWinner = true;

            }
            winner = true;
        }
    }

    private boolean isFullGrid() {
        return !label1.getText().equals("")
                && !label2.getText().equals("")
                && !label3.getText().equals("")
                && !label4.getText().equals("")
                && !label5.getText().equals("")
                && !label6.getText().equals("")
                && !label7.getText().equals("")
                && !label8.getText().equals("")
                && !label9.getText().equals("");
    }

    private void dialogHandle() {
        Dialog dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setGraphic(new ImageView(this.getClass().
          getResource("/Gallary/congrats.gif").toString()));
        dialog.setContentText("Do you want to play again");
        
       ButtonType rematchButtonType = new ButtonType("Rematch", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(rematchButtonType, exitButtonType);
        dialogPane.lookupButton(exitButtonType).setVisible(true);
        
        Button rematchButton = (Button) dialog.getDialogPane().lookupButton(rematchButtonType);
        rematchButton.setAlignment(Pos.CENTER);
        Button exitButton = (Button) dialog.getDialogPane().lookupButton(exitButtonType);
        exitButton.setAlignment(Pos.CENTER);
        rematchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("mahoud");
               reMatch();
                
            }
        });
       
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    
                      navigator.navigateToMainScreen(event);
                } catch (IOException ex) {
                    Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
   
        dialog.showAndWait();
    }

    private void checkState() {
        checkRows();
        checkColumns();
        checkDiagonal();
        if (firstWinner) {
             playerOneScore++;
            playerOneScoreLbl.setText(""+playerOneScore);
            playerTwoScoreLbl.setText(""+playerTwoScore);
            //dialogHandle();
            //gamePane.setDisable(true);
            //System.out.println("X is win");
           
        } else if (secondWinner) {
             playerTwoScore++;
            playerTwoScoreLbl.setText(""+playerTwoScore);
            playerOneScoreLbl.setText(""+playerOneScore);
            //dialogHandle();
            //System.out.println("O is win");
           
            //gamePane.setDisable(true);
        } else {
            if ((isFullGrid())) {
                gamePane.setDisable(true);
                System.out.println("It's a Draw");
            }
        }
    }


    @FXML
    private void playerOneImageClicked(MouseEvent event) {
        /* TODO:
        show dialog to view the player data from the server 
        
        
        */
    }
    @FXML
    private void playerTwoImageClicked(MouseEvent event) {
         /* TODO:
        show dialog to view the player data from the server 
        
        
        */
    }

    @FXML
    private void backBtnAction(ActionEvent event) {
        Dialog dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setContentText("Do you want to exit game!");
        
        ButtonType forfeitButtonType = new ButtonType("Forfeit", ButtonBar.ButtonData.OK_DONE);
        
            
        ButtonType cancelButtonType = new ButtonType( "Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(forfeitButtonType,cancelButtonType);
        dialogPane.lookupButton(cancelButtonType).setVisible(true);
        
        Button forfeitButton = (Button) dialog.getDialogPane().lookupButton(forfeitButtonType);
        forfeitButton.setAlignment(Pos.CENTER);
        Button exitButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        exitButton.setAlignment(Pos.CENTER);
        forfeitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("mahoud");
                    navigator.navigateToMainScreen(event);
                } catch (IOException ex) {
                    Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        dialog.showAndWait();
       
        
    }

}
