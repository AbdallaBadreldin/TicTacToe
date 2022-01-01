package controller;

import helpers.Navigation;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    private boolean isGameActive = true;
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
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Stage dialogStage;
    private Line line;

    Navigation navigator = new Navigation();
    GameSession gameSession = new GameSession();
    DialogController dialog = new DialogController();
    Label label = new Label();
    int playerOneScore = 0;
    int playerTwoScore = 0;
    private boolean playerTurn = true;
    private boolean firstWinner = false;
    private boolean secondWinner = false;
    private boolean isXSymbol = true;
    private boolean winner;
    private String symbol;
    private boolean isAIMode = false;
    Label[] labelArr = new Label[9];


    boolean isGameEnded;
    boolean isPlayerTurn = true;
    boolean isPcTurn = false;
    int XOCounter = 0;

    @FXML
    private Button backBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerOneImageView.setImage(new Image("/resources/player-one-avatar.jpg"));
        playerTwoImageView.setImage(new Image("/resources/player-two-avatar.jpg"));
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
    private void onExitImageClick(MouseEvent event) {
    }

    @FXML
    private void onBackClick(MouseEvent event) {
    }
    Random random = new Random();
    int randomNumber;

    @FXML
    private void handleLabels(MouseEvent mouseEvent) {
        ((Label) mouseEvent.getSource()).setDisable(true);

        if (!isAIMode) {
            gameSession.addMove(returnMove((Label) mouseEvent.getSource()));
            //playersMoves[counter++] = returnMove((Label) mouseEvent.getSource());
            ((Label) mouseEvent.getSource()).setText(returnSymbol());

        } else {
            Label clickedButton = (Label) mouseEvent.getSource();
            if (isGameEnded == false && clickedButton.getText().equals("")) {
                XOCounter++;
                isPlayerTurn = true;

                clickedButton.setText("X");

                if (isGameEnded == false) {

                    XOCounter++;
                    isPlayerTurn = false;

                    for (int i=0;i<9;i++) {
                        randomNumber = random.nextInt(9);
                        if (labelArr[randomNumber].getText().equals("")) {

                            labelArr[randomNumber].setText("O");
                            labelArr[randomNumber].setDisable(true);

                            break;
                        }
                    }

                }

            }

        }
        checkState();

    }

    private void removeLine() {
        mainPane.getChildren().remove(line);

    }

    private void reMatch() {
        isGameActive = !isGameActive;
        //System.out.println("controller.MainGridPaneController.reMatch()");
        gamePane.setDisable(isGameActive);
        label1.setText("");
        label2.setText("");
        label3.setText("");
        label4.setText("");
        label5.setText("");
        label6.setText("");
        label7.setText("");
        label8.setText("");
        label9.setText("");
        removeLine();
        winner = false;
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
        line = new Line(x1, y1, x2, y2);
        mainPane.getChildren().add(line);
    }

    private String returnSymbol() {
        //  String symbol;
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

    private void checkRows() {
        if (label1.getText().equals(label2.getText())
                && label2.getText().equals(label3.getText())
                && !label1.getText().equals("")) {

            drawLine(label1, label3);
            //dialogHandle();

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
            //dialogHandle();

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
            //dialogHandle();

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
            //dialogHandle();

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
            //dialogHandle();

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
            //dialogHandle();

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
            //dialogHandle();

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

    /* private void dialogHandle() {

        StackPane root = new StackPane();
        Stage dialogStage = new Stage();

        Image image = new Image("/Gallary/congrats.gif", 150, 150, false, true, true);
        ImageView imageView = new ImageView(image);
         root.getChildren().add(imageView);
        Button btn = new Button();
        btn.setText("Rematch");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();

                reMatch();
            }
        });
        root.getChildren().addAll( btn);

        Scene scene = new Scene(root, 500,500);
        dialogStage.setScene(scene);
        dialogStage.show();

    }*/
    private void checkState() {
        checkRows();
        checkColumns();
        checkDiagonal();
        if (firstWinner) {
            playerOneScore++;
            playerOneScoreLbl.setText("" + playerOneScore);
            playerTwoScoreLbl.setText("" + playerTwoScore);
            isGameActive = !isGameActive;
            //navigator.navigateToDialog(event);

            //dialogHandle();
            gamePane.setDisable(true);
            //System.out.println("X is win");
        } else if (secondWinner) {
            playerTwoScore++;
            playerTwoScoreLbl.setText("" + playerTwoScore);
            playerOneScoreLbl.setText("" + playerOneScore);
            isGameActive = !isGameActive;

            //dialogHandle();
            //System.out.println("O is win");
            gamePane.setDisable(true);
        } else {
            if ((isFullGrid())) {
                gamePane.setDisable(true);
                System.out.println("It's a Draw");
                isGameActive = !isGameActive;
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
        Stage dialogStage = new Stage();

        VBox vb = new VBox();
        vb.setPadding(new Insets(10, 50, 50, 50));
        vb.setSpacing(10);

        Label lb = new Label("Do you want to exit game!");
        lb.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        lb.setAlignment(Pos.CENTER);
        vb.getChildren().add(lb);

        Button forfeitBtn = new Button();
        forfeitBtn.setText("Forfeit");
        vb.getChildren().add(forfeitBtn);
        forfeitBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    //stage.close();
                    //stage.hide();
                    //mainPane.setVisible(false);

                    navigator.navigateTo(event, Navigation.MAIN_SCREEN);
                } catch (IOException ex) {
                    Logger.getLogger(MainGridPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button cancelBtn = new Button();
        cancelBtn.setText("Cancel");
        vb.getChildren().add(cancelBtn);
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();
            }
        });

        Scene scene = new Scene(vb);
        dialogStage.setScene(scene);
        dialogStage.show();
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
    }

}
