/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import helpers.GameRecorder;
import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import models.Common;
import models.DiffcultLevel.Move;
import models.DiffcultLevel;
import models.GameSession;

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
    private Text winnerName;
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
    private GameSession gameSession;

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

        isPlayer1BtnEditeClilcked = !isPlayer1BtnEditeClilcked;
        playerEditText.setPromptText("Enter player1 name");
        getPlayerNameDialog.show();

    }

    @FXML
    private void playerTwoImageClicked(MouseEvent event) {
    }

    @FXML
    private void onPlayer2HBoxClick(MouseEvent event) {
    }

    @FXML
    private void onBackClick(MouseEvent event) {

        newDialog.show();

    }

    private String player = "X";
    private Label LabelPressed;
    private boolean winner = false;
    private boolean display = false;
    private Preferences prefs;
    private int score = 0;
    private boolean firstWinner = false;
    private boolean secondWinner = false;
    private int computerScore = 0;
    private List<Line> arrayLine;
    int moveNum = 0;
    Move bestMove;
    private Boolean computerWin = false;
    public Label[][] board = new Label[3][3];

    private final Navigation navigator = new Navigation();
    Line line = new Line();
    private boolean isPlayer1BtnEditeClilcked;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerOneImageView.setImage(new Image("/Gallary/player-one-avatar.jpg"));
        playerTwoImageView.setImage(new Image("/Gallary/ai-avatar.png"));
        playerOneScoreLbl.setText("" + Common.PLAYER_ONE_SCORE);
        playerTwoScoreLbl.setText("" + Common.PLAYER_TWO_SCORE);
        newDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        newDialog.setDialogContainer(root);
        getPlayerNameDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        getPlayerNameDialog.setDialogContainer(root);
        playerEditText.setFocusTraversable(false);
        LeaveBtn.setOnAction((event) -> {
            try {
                navigator.navigateTo(event, Navigation.MAIN_SCREEN);
                newDialog.close();
            } catch (IOException ex) {
                Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cancelBtn.setOnAction((e) -> newDialog.close());
        confirm.setOnAction((e) -> {
            if (isPlayer1BtnEditeClilcked) {
                playerOneNameLbl.setText(playerEditText.getText());
                isPlayer1BtnEditeClilcked = !isPlayer1BtnEditeClilcked;
            } else {
                playerTwoNameLbl.setText(playerEditText.getText());
            }
            getPlayerNameDialog.close();
            playerEditText.setText("");
        });

        winnerDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        winnerDialog.setDialogContainer(root);
        RematchBtn.setOnAction((event) -> {
            try {
                winnerDialog.close();
                //reMatch
                //playerOneScoreLbl.setText("" + score);
                //playerTwoScoreLbl.setText("" + computerScore);
                navigator.navigateTo(event, Navigation.HARD_MODE);

            } catch (IOException ex) {
                Logger.getLogger(HardModeViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        CancelBtn.setOnAction((e) -> {
            try {
                navigator.navigateTo(e, Navigation.MAIN_SCREEN);
                Common.PLAYER_ONE_SCORE = 0;
                Common.PLAYER_TWO_SCORE = 0;
                newDialog.close();
            } catch (IOException ex) {
                Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cancel.setOnAction((e) -> getPlayerNameDialog.close());

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
                            checkState();
//
//                            int result = DiffcultLevel.evaluate(board);
//                            if (result == 10) {
//                                System.out.println("You lost :(");
//                                //txtWinner.setText("computer won!");
//
//                                computerScore += 1;
//                                playerOneScoreLbl.setText("" + score);
//                                playerTwoScoreLbl.setText("" + computerScore);
//                                winnerName.setText("YOU LOST");
//                                winnerImage.setImage(new Image("Gallary/loser.gif"));
//
//                                winnerDialog.show();
//
//                                //displayVideo("lose");
//                                // edit 
//                                //btnPlayAgain.setVisible(true);
//                                winner = true;
//
//                                //  xScore++;
//                                //  System.out.println("the x score is" + xScore);
//                            } else if (result == -10) {
//                                System.out.println("You won ^^");
//
//                                winnerImage.setImage(new Image("Gallary/congrats.gif"));
//                                winnerName.setText("Player 1 Winner");
//                                winnerDialog.show();
//                                //displayVideo("winner");
//                                score += 1;
//                                playerOneScoreLbl.setText("" + score);
//                                playerTwoScoreLbl.setText("" + computerScore);
//                                prefs.putInt("score", score);
//                                playerOneScoreLbl.setText("" + score);
//
//                                //displayVideo("winner");
//                                score += 10;
//                                prefs.putInt("score", score);
//                                playerOneScoreLbl.setText("" + score);
//
//                                //btnPlayAgain.setVisible(true);
//                                //txtWinner.setText("you won!");
//                                winner = true;
//
//                            } else if (DiffcultLevel.isMoveLeft(board) == false) {
//                                System.out.println("No One Wins !");
//
//                                winnerName.setText("****Draw****");
//                                winnerImage.setImage(new Image("Gallary/white_background.jpg"));
//                                winnerDialog.show();
//                                //  tieScore++;
//                                //btnPlayAgain.setVisible(true);
//                                //txtWinner.setText("NO One Wins!");
//                                // System.out.println("tie score " + tieScore);                                 //  tieScore++;
//                                //btnPlayAgain.setVisible(true);
//                                //txtWinner.setText("NO One Wins!");
//                                // System.out.println("tie score " + tieScore);
//
//                                winner = true;
//                            }
                        }
                    }
                });

            }

        }
    }

    private void removeLine() {
        mainPane.getChildren().remove(line);
    }

    private void setLabelsEnable() {

        label1.setDisable(false);
        label2.setDisable(false);
        label3.setDisable(false);
        label4.setDisable(false);
        label5.setDisable(false);
        label6.setDisable(false);
        label7.setDisable(false);
        label8.setDisable(false);
        label9.setDisable(false);

    }

    private void reMatch() {
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
        //gameSession.resetMove();
        removeLine();
        setLabelsEnable();
        firstWinner = false;
        secondWinner = false;
        winner = false;
        //XOCounter = 0;

    }

    public void drawLine(Label b1, Label b2) {
        Bounds bound1 = b1.localToScene(b1.getBoundsInLocal());
        Bounds bound2 = b2.localToScene(b2.getBoundsInLocal());
        double x1, y1, x2, y2;
        x1 = (bound1.getMinX() + bound1.getMaxX()) / 2;
        y1 = (bound1.getMinY() + bound1.getMaxY()) / 2;
        x2 = (bound2.getMinX() + bound2.getMaxX()) / 2;
        y2 = (bound2.getMinY() + bound2.getMaxY()) / 2;
        line = new Line(x1, y1, x2, y2);
        line.setFill(Color.rgb(255, 103, 1));
        line.setStroke(Color.rgb(255, 103, 1));
        line.setStrokeWidth(6);
        System.out.println("draw line");
        mainPane.getChildren().add(line);
    }

    private void checkRows() {

        if (label1.getText().equals(label2.getText())
                && label2.getText().equals(label3.getText())
                && !label1.getText().equals("")) {

            drawLine(label1, label3);

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

    private void checkState() {
        checkRows();
        checkColumns();
        checkDiagonal();
        if (firstWinner) {
            Common.PLAYER_ONE_SCORE++;
            playerOneScoreLbl.setText("" + Common.PLAYER_ONE_SCORE);
            playerTwoScoreLbl.setText("" + Common.PLAYER_TWO_SCORE);
            winnerImage.setImage(new Image("Gallary/congrats.gif"));
            winnerName.setText(playerOneNameLbl.getText());
            winnerDialog.show();
            gamePane.setDisable(true);

            GameRecorder rec = new GameRecorder();
            rec.writer(gameSession);

        } else if (secondWinner) {

            Common.PLAYER_TWO_SCORE++;
            playerTwoScoreLbl.setText("" + Common.PLAYER_TWO_SCORE);
            playerOneScoreLbl.setText("" + Common.PLAYER_ONE_SCORE);
            winnerName.setText("YOU LOST");
            winnerImage.setImage(new Image("Gallary/loser.gif"));
            winnerDialog.show();
            gamePane.setDisable(true);

        } else {
            if ((isFullGrid())) {
                gamePane.setDisable(true);

                winnerName.setText("****Draw****");
                winnerDialog.show();
                System.out.println("It's a Draw");

            }
        }
    }

}
