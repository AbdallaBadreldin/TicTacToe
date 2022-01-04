/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.DiffcultLevel.Move;
import models.DiffcultLevel;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class HardModeViewController implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private AnchorPane mainPane;
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
    @FXML
    private ImageView playerOneImageView;
    @FXML
    private HBox player1HBox;
    @FXML
    private Label playerOneNameLbl;
    @FXML
    private Label playerOneScoreLbl;
    @FXML
    private Label playerTwoScoreLbl;
    @FXML
    private ImageView playerTwoImageView;
    @FXML
    private HBox player2HBox;
    @FXML
    private Label playerTwoNameLbl;
    @FXML
    private ImageView backImage;
    @FXML
    private JFXDialog newDialog;
    @FXML
    private JFXButton LeaveBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXDialog winnerDialog;
    @FXML
    private Label winnerName;
    @FXML
    private ImageView winnerImage;
    @FXML
    private JFXButton RematchBtn;
    @FXML
    private JFXButton CancelBtn;
    @FXML
    private JFXDialog getPlayerNameDialog;
    @FXML
    private TextField playerEditText;
    @FXML
    private JFXButton confirm;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleLabels(MouseEvent event) {
    }

    @FXML
    private void playerOneImageClicked(MouseEvent event) {
    }

    @FXML
    private void onPlayer1HBoxClick(MouseEvent event) {
    }

    @FXML
    private void playerTwoImageClicked(MouseEvent event) {
    }

    @FXML
    private void onPlayer2HBoxClick(MouseEvent event) {
    }

    @FXML
    private void onBackClick(MouseEvent event) {
    }

    private String player = "X";
    private Label LabelPressed;
    private boolean winner = false;
    private boolean display = false;
    private Preferences prefs;
    private int score = 0;
    int moveNum = 0;
    Move bestMove;
    private Boolean computerWin = false;
    public Label[][] board = new Label[3][3];

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        board[0][0] = label1;
        board[0][1] = label2;
        board[0][2] = label3;
        board[1][0] = label4;
        board[1][1] = label5;
        board[1][2] = label6;
        board[2][0] = label7;
        board[2][1] = label8;
        board[2][2] = label9;

        for (Label[] labels : board) {
            for (Label label : labels) {
                label.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                    if (!winner) {

                        label.setText("X");

                        System.out.println("xxx");
                        label.setMouseTransparent(true);
                        if (moveNum + 1 < 9) {
                            bestMove = DiffcultLevel.findBestMove(board);
                            board[bestMove.row][bestMove.col].setText("O");
 
                            board[bestMove.row][bestMove.col].setMouseTransparent(true);
                        }

                        moveNum += 2;
                        if (moveNum >= 5) {

                            int result = DiffcultLevel.evaluate(board);
                            if (result == 10) {
                                System.out.println("You lost :(");
                                //txtWinner.setText("computer won!");
                                //displayVideo("lose");
                                // edit 
                                //btnPlayAgain.setVisible(true);
                                winner = true;

                                //  xScore++;
                                //  System.out.println("the x score is" + xScore);
                            } else if (result == -10) {
                                System.out.println("You won ^^");
                                //displayVideo("winner");
                                score += 10;
                                prefs.putInt("score", score);
                                playerOneScoreLbl.setText("" + score);
                                //btnPlayAgain.setVisible(true);
                                //txtWinner.setText("you won!");
                                winner = true;

                            } else if (DiffcultLevel.isMoveLeft(board) == false) {
                                System.out.println("No One Wins !");
                                //  tieScore++;
                                //btnPlayAgain.setVisible(true);
                                //txtWinner.setText("NO One Wins!");
                                // System.out.println("tie score " + tieScore);
                                winner = true;
                            }
                        }
                    }
                });

            }

        }
    }




}
