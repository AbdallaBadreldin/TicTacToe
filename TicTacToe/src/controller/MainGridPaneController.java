package controller;

import client.GameClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import helpers.GameRecorder;
import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import static models.Common.PREV_SESSION;
import models.GameSession;
import models.Player;

import models.PlayerMove;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class MainGridPaneController implements Initializable {

    @FXML
    private AnchorPane mainPane;
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
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog newDialog;
    @FXML
    private JFXDialog winnerDialog;
    @FXML
    private ImageView backImage;
    @FXML
    private JFXButton LeaveBtn;
    @FXML
    private JFXButton cancelBtn;
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
    private boolean isPlayer1BtnEditeClilcked;
    @FXML
    private HBox player1HBox;
    @FXML
    private HBox player2HBox;
    @FXML
    private Text winnerName;
    @FXML
    private ImageView editBtn2;
    @FXML
    private Label newDialogLabel;

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private boolean playerTurn = true;
    private boolean firstWinner = false;
    private boolean secondWinner = false;
    private boolean isXSymbol = true;
    private boolean winner;
    private String symbol;
    private boolean isAIMode;
    private boolean isItOnlineGame;
    private Label[] labelArr = new Label[9];
    private boolean isGameEnded;
    private boolean isPlayerTurn = true;
    private boolean isPcTurn = false;
    private int XOCounter = 0;
    private List<Line> arrayLine;
    private boolean isGameActive = true;
    private final Navigation navigator = new Navigation();
    private GameSession gameSession;
    private GameClient client;
    private Random random = new Random();
    private int randomNumber;
    public static boolean isItPrev;
    private Player player1;
    private Player player2;
    @FXML
    private ImageView editIcon1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerOneImageView.setImage(new Image("/Gallary/player-one-avatar.jpg"));
        playerTwoImageView.setImage(new Image("/Gallary/player-two-avatar.jpg"));
        player1 = new Player(playerOneNameLbl.getText());
        player2 = new Player(playerTwoNameLbl.getText());
        gameSession = new GameSession(player1, player2);
        playerTwoScoreLbl.setText("" + playerTwoScore);
        playerOneScoreLbl.setText("" + playerOneScore);
        newDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        newDialog.setDialogContainer(root);
        getPlayerNameDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        getPlayerNameDialog.setDialogContainer(root);
        playerEditText.setFocusTraversable(false);
        arrayLine = new ArrayList<>();
        if (isItPrev) {
            playerOneNameLbl.setText(PREV_SESSION.getPlayerOne().getUserName());
            playerTwoNameLbl.setText(PREV_SESSION.getPlayerTwo().getUserName());
            editIcon1.setVisible(false);
            editBtn2.setVisible(false);
            player2HBox.setDisable(true);
            player1HBox.setDisable(true);
            thread.start();
        }
        LeaveBtn.setOnAction((event) -> {
            if (isItPrev) {
                try {
                    isItPrev = false;
                    PREV_SESSION = null;
                    navigator.navigateTo(event, Navigation.RECORDERS_SCREEN);
                    newDialog.close();
                } catch (IOException ex) {
                    ex.getMessage();
                    Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    navigator.navigateTo(event, Navigation.MAIN_SCREEN);
                    newDialog.close();
                } catch (IOException ex) {
                    ex.getMessage();
                    Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        confirm.setOnAction((e) -> {
            String updatedName;
            if (isPlayer1BtnEditeClilcked) {
                updatedName = playerEditText.getText();
                playerOneNameLbl.setText(updatedName);
                player1.setUserName(updatedName);
                isPlayer1BtnEditeClilcked = !isPlayer1BtnEditeClilcked;
            } else {
                updatedName = playerEditText.getText();
                playerTwoNameLbl.setText(updatedName);
                player2.setUserName(updatedName);
            }
            getPlayerNameDialog.close();
            playerEditText.setText("");
        });
        cancelBtn.setOnAction((e) -> {
            if (isItPrev) {
                newDialogLabel.setText("Do you want to go back to Recorders Screen!");
                thread.resume();
            }
            newDialog.close();
        });
        winnerDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        winnerDialog.setDialogContainer(root);
        RematchBtn.setOnAction((event) -> {
            winnerDialog.close();
            reMatch();
        });
        CancelBtn.setOnAction((e) -> {
            try {
                navigator.navigateTo(e, Navigation.MAIN_SCREEN);
                newDialog.close();
            } catch (IOException ex) {
                ex.getMessage();
                Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cancel.setOnAction((e) -> getPlayerNameDialog.close());
        addLabelArray();
    }

    private void addLabelArray() {
        labelArr[0] = label1;
        labelArr[1] = label2;
        labelArr[2] = label3;
        labelArr[3] = label4;
        labelArr[4] = label5;
        labelArr[5] = label6;
        labelArr[6] = label7;
        labelArr[7] = label8;
        labelArr[8] = label9;
    }

    @FXML
    private void onBackClick(MouseEvent event) {
        if (isItPrev) {
            newDialogLabel.setText("Do you want to go back to recordersScreen!");
            thread.suspend();
        }
        newDialog.show();
    }

    @FXML
    private void handleLabels(MouseEvent mouseEvent) throws ClassNotFoundException {
        ((Label) mouseEvent.getSource()).setDisable(true);

        if (!isAIMode) {
            gameSession.addMove(returnMove((Label) mouseEvent.getSource()));
            ((Label) mouseEvent.getSource()).setText(returnSymbol());
        } else if (isItOnlineGame) {
            //TODO: handel the online game logic
        } else {
            Label clickedButton = (Label) mouseEvent.getSource();

            if (isGameEnded == false && clickedButton.getText().equals("")) {
                XOCounter++;
                isPlayerTurn = true;
                clickedButton.setText("X");
                gameSession.addMove(returnMove(labelArr[randomNumber], isXSymbol));
                System.out.println("comp with x move saved");
                if (isGameEnded == false) {

                    XOCounter++;
                    isPlayerTurn = !isPlayerTurn;

                    for (int i = 0; i < 9; i++) {
                        randomNumber = random.nextInt(9);
                        if (labelArr[randomNumber].getText().equals("")) {
                            labelArr[randomNumber].setText("O");
                            System.out.println("comp with o move saved");
                            labelArr[randomNumber].setDisable(true);
                            gameSession.addMove(returnMove(labelArr[randomNumber], isXSymbol));
                            break;
                        }
                    }
                }
            }
        }
        checkState();
    }

    private void removeLine() {
        for (int i = 0; i < arrayLine.size(); i++) {
            mainPane.getChildren().remove(arrayLine.get(i));
        }
        arrayLine.clear();
    }

    private void setLabelState(boolean state) {
        label1.setDisable(state);
        label2.setDisable(state);
        label3.setDisable(state);
        label4.setDisable(state);
        label5.setDisable(state);
        label6.setDisable(state);
        label7.setDisable(state);
        label8.setDisable(state);
        label9.setDisable(state);
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
        gameSession = new GameSession(player1, player2);
        removeLine();
        setLabelState(false);
        firstWinner = false;
        secondWinner = false;
        winner = false;
        XOCounter = 0;

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
        line.setFill(Color.rgb(255, 103, 1));
        line.setStroke(Color.rgb(255, 103, 1));
        line.setStrokeWidth(6);
        arrayLine.add(line);
        mainPane.getChildren().add(line);
    }

    private String returnSymbol() {
        if (isXSymbol == true) {
            symbol = "X";
        } else {
            symbol = "O";
        }
        isXSymbol = !isXSymbol;
        return symbol;
    }

    private PlayerMove returnMove(Label label, boolean comp) {
        PlayerMove move = new PlayerMove();
        if (label == label1) {
            move = new PlayerMove(0, 0, comp);
        } else if (label == label2) {
            move = new PlayerMove(0, 1, comp);
        } else if (label == label3) {
            move = new PlayerMove(0, 2, comp);
        } else if (label == label4) {
            move = new PlayerMove(1, 0, comp);
        } else if (label == label5) {
            move = new PlayerMove(1, 1, comp);
        } else if (label == label6) {
            move = new PlayerMove(1, 2, comp);
        } else if (label == label7) {
            move = new PlayerMove(2, 0, comp);
        } else if (label == label8) {
            move = new PlayerMove(2, 1, comp);
        } else if (label == label9) {
            move = new PlayerMove(2, 2, comp);
        }
        return move;
    }

    private PlayerMove returnMove(Label label) {
        PlayerMove move = new PlayerMove();
        if (label == label1) {
            move = new PlayerMove(0, 0, isXSymbol);
        } else if (label == label2) {
            move = new PlayerMove(0, 1, isXSymbol);
        } else if (label == label3) {
            move = new PlayerMove(0, 2, isXSymbol);
        } else if (label == label4) {
            move = new PlayerMove(1, 0, isXSymbol);
        } else if (label == label5) {
            move = new PlayerMove(1, 1, isXSymbol);
        } else if (label == label6) {
            move = new PlayerMove(1, 2, isXSymbol);
        } else if (label == label7) {
            move = new PlayerMove(2, 0, isXSymbol);
        } else if (label == label8) {
            move = new PlayerMove(2, 1, isXSymbol);
        } else if (label == label9) {
            move = new PlayerMove(2, 2, isXSymbol);
        }
        return move;
    }

    private void drawPreviweGame(PlayerMove move) {
        isXSymbol = move.isIsX();
        if (move.getX() == 0 && move.getY() == 0) {
            label1.setText(returnSymbol());
        } else if (move.getX() == 0 && move.getY() == 1) {
            label2.setText(returnSymbol());
        } else if (move.getX() == 0 && move.getY() == 2) {
            label3.setText(returnSymbol());
        } else if (move.getX() == 1 && move.getY() == 0) {
            label4.setText(returnSymbol());
        } else if (move.getX() == 1 && move.getY() == 1) {
            label5.setText(returnSymbol());
        } else if (move.getX() == 1 && move.getY() == 2) {
            label6.setText(returnSymbol());
        } else if (move.getX() == 2 && move.getY() == 0) {
            label7.setText(returnSymbol());
        } else if (move.getX() == 2 && move.getY() == 1) {
            label8.setText(returnSymbol());
        } else if (move.getX() == 2 && move.getY() == 2) {
            label9.setText(returnSymbol());
        }
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
            playerOneScore++;
            playerOneScoreLbl.setText("" + playerOneScore);
            playerTwoScoreLbl.setText("" + playerTwoScore);
            isGameActive = !isGameActive;
            winnerImage.setImage(new Image("Gallary/congrats.gif"));
            winnerName.setText(playerOneNameLbl.getText());
            gamePane.setDisable(true);
            if (!isItPrev && !isAIMode) {
                gameSession.createGameName();
                GameRecorder rec = new GameRecorder();
                rec.writer(gameSession);
            }
            winnerDialog.show();

        } else if (secondWinner) {
            if (isAIMode) {
                playerTwoScore++;
                playerTwoScoreLbl.setText("" + playerTwoScore);
                playerOneScoreLbl.setText("" + playerOneScore);
                isGameActive = !isGameActive;
                winnerName.setText("YOU LOST");
                winnerImage.setImage(new Image("Gallary/loser.gif"));
                gamePane.setDisable(true);
                if (!isItPrev && !isAIMode) {
                    gameSession.createGameName();
                    GameRecorder rec = new GameRecorder();
                    rec.writer(gameSession);
                }
                winnerDialog.show();

            } else {
                playerTwoScore++;
                playerTwoScoreLbl.setText("" + playerTwoScore);
                playerOneScoreLbl.setText("" + playerOneScore);
                isGameActive = !isGameActive;
                winnerImage.setImage(new Image("Gallary/congrats.gif"));
                winnerName.setText(playerTwoNameLbl.getText());
                gamePane.setDisable(true);
                if (!isItPrev && !isAIMode) {
                    gameSession.createGameName();
                    GameRecorder rec = new GameRecorder();
                    rec.writer(gameSession);
                }
                winnerDialog.show();

            }
        } else {
            if ((isFullGrid())) {
                gamePane.setDisable(true);
                winnerName.setText("****Draw****");
                System.out.println("It's a Draw");
                isGameActive = !isGameActive;
                if (!isItPrev && !isAIMode) {
                    gameSession.createGameName();
                    GameRecorder rec = new GameRecorder();
                    rec.writer(gameSession);
                }
                winnerDialog.show();

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

    public void setPlayerOneName(String name) {
        playerOneNameLbl.setText(name);

    }

    public void setPlayerTwoName(String name) {
        playerTwoNameLbl.setText(name);

    }

    public void setPlayerOneImage(String imgURL) {
        playerOneImageView.setImage(new Image(imgURL));

    }

    public void setPlayerTwoImage(String imgURL) {
        playerTwoImageView.setImage(new Image(imgURL));
    }

    public void setIsAIMode(boolean isAIMode) {
        this.isAIMode = isAIMode;
        gameSession = new GameSession(player1, new Player("AI"));
        editBtn2.setVisible(false);
        player2HBox.setDisable(true);
    }

    @FXML
    private void onPlayer1HBoxClick(MouseEvent event) {
        isPlayer1BtnEditeClilcked = !isPlayer1BtnEditeClilcked;
        playerEditText.setPromptText("Enter player1 name");
        getPlayerNameDialog.show();
    }

    @FXML
    private void onPlayer2HBoxClick(MouseEvent event) {
        playerEditText.setPromptText("Enter player2 name");
        getPlayerNameDialog.show();
    }

    public void setIsItOnlineGame(boolean isItOnlineGame) {
        this.isItOnlineGame = isItOnlineGame;
        if (isGameActive) {
            try {
                client = GameClient.getInstactance(Common.IP, Common.PORT);
            } catch (IOException ex) {
                Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    Thread thread = new Thread(() -> {
        try {
            setLabelState(true);
            gameSession = new GameSession(Common.PREV_SESSION.getPlayerOne(),
                    Common.PREV_SESSION.getPlayerOne());
            for (PlayerMove move : Common.PREV_SESSION.getPlayersMoves()) {
                if (move != null) {
                    Thread.sleep(2000);
                    Platform.runLater(() -> {
                        gameSession.addMove(move);
                        drawPreviweGame(move);
                        checkState();
                    });
                } else {
                    Thread.sleep(2000);
                    Platform.runLater(() -> {
                        newDialogLabel.setText("Nice moves, click Ok! to go back to recorders screen.");
                        LeaveBtn.setText("Ok");
                        cancelBtn.setVisible(false);
                        newDialog.show();
                    });
                    break;
                }
            }
        } catch (InterruptedException ex) {
            ex.getMessage();
            Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
}
