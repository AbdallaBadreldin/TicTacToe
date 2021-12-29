/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import models.GameSession;
import models.PlayerMove;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class PlayerVsAIScreenController implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button backBtn;
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
    private Pane gamePane;
    @FXML
    private GridPane GridPane;
    @FXML
    private Pane grid00;
    @FXML
    private Label label1;
    @FXML
    private Pane grid01;
    @FXML
    private Label label2;
    @FXML
    private Pane grid02;
    @FXML
    private Label label3;
    @FXML
    private Pane grid10;
    @FXML
    private Label label4;
    @FXML
    private Pane grid11;
    @FXML
    private Label label5;
    @FXML
    private Pane grid12;
    @FXML
    private Label label6;
    @FXML
    private Pane grid20;
    @FXML
    private Label label7;
    @FXML
    private Pane grid21;
    @FXML
    private Label label8;
    @FXML
    private Pane grid22;
    @FXML
    private Label label9;
    GameSession gameSession = new GameSession();

    private int computerMove;
    private boolean playerTurn = true;
    private boolean isXSymbol = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLabels(MouseEvent event) {
        ((Label) event.getSource()).setDisable(true);
        gameSession.addMove(returnMove((Label) event.getSource()));
        ((Label) event.getSource()).setText(returnSymbol());
        // checkState();
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

    /*  private void checkRows() {
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
            //dialogHandle();

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
     */
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

    /* private void dialogHandle() {
        Dialog dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setGraphic(new ImageView(this.getClass().
          getResource("/Gallary/congrats.gif").toString()));
        dialog.setContentText("Do you want to play again");
        
       ButtonType rematchButtonType = new ButtonType("Rematch", ButtonBar.ButtonData.OTHER);
        ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.OTHER);
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
    }*/
    private void computerTurn() {
        Random rand = new Random();
        playerTurn = false;
        while (true) {
            computerMove = rand.nextInt(9);
        }
        //returnSymbol();
        
        

    }

    @FXML
    private void backBtnAction(ActionEvent event) {
    }

    @FXML
    private void playerOneImageClicked(MouseEvent event) {
    }

    @FXML
    private void playerTwoImageClicked(MouseEvent event) {
    }


}