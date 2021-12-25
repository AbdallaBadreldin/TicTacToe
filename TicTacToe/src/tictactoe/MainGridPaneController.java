/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

    GameSession gameSession = new GameSession();
    Label label = new Label();

    private boolean playerTurn = true;

    private boolean firstWinner = false;
    private boolean secondWinner = false;
    private boolean isXSymbol = true;

    private boolean winner = false;

    @FXML
    private GridPane GridPane;

    @FXML
    public void handelGridPane(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
         labels = new ArrayList<>(Arrays.asList(label1,label2,label3,label4,label5,label6,label7,label8,label9));
         labels.forEach(label->{
             setupLabel(label);
             label.setFocusTraversable(false);
             label.setPrefSize(90, 90);
             label.setAlignment(Pos.CENTER);
             label.setFont(Font.font("Arial", FontWeight.BOLD, 40));
         });

        labels=new Label[]{label1,label2,label3,label4,label5,label6,label7,label8,label9};
        for(int i=0;i<labels.length;i++)
        {
            labels[i]=new Label();
            labels[i].setFocusTraversable(false);
            
           
        }*/

    }


    /*
    public void  setupLabel(Label label)
    {
        label.setOnMouseClicked(mouseEvent->{
         //setPlayerSymbol(label);
         
     });
        
    }
    
    
    public void setPlayerSymbol(Label label){
        if(label.getText()=="") {
            if(playerTurn)
            { label.setText("X");
              label.setTextFill(Color.GREEN);
              playerTurn=false;
            }
            else
            {
                label.setText("O");
              label.setTextFill(Color.RED);
              playerTurn =true;
            }
   
        }  
        
    }
     */
    @FXML
    private void handleLabels(MouseEvent mouseEvent) {
        ((Label) mouseEvent.getSource()).setDisable(true);
        gameSession.addMove(returnMove((Label) mouseEvent.getSource()));
        ((Label) mouseEvent.getSource()).setText(returnSymbol());
        checkState();

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
        gamePane.getChildren().add(line);

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

    private void dialogHandle() {
        Dialog dialog = new Dialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setHeight(100);
        dialog.setWidth(100);

        dialog.setContentText("Yaaaaay you are winner\n"
                + "Do you want to play again");

        ButtonType rematchButtonType = new ButtonType("Rematch", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(rematchButtonType, exitButtonType);
        dialogPane.lookupButton(exitButtonType).setVisible(true);

        Button rematchButton = (Button) dialog.getDialogPane().lookupButton(rematchButtonType);
        rematchButton.setAlignment(Pos.CENTER);
        Button exitButton = (Button) dialog.getDialogPane().lookupButton(exitButtonType);
        rematchButton.setAlignment(Pos.CENTER);
        dialog.showAndWait();
    }

    private void checkState() {

        checkRows();
        checkColumns();
        checkDiagonal();
        if (firstWinner) {
            dialogHandle();
            gamePane.setDisable(true);
            System.out.println("X is win");
        } else if (secondWinner) {
            dialogHandle();
            System.out.println("O is win");
            gamePane.setDisable(true);
        } else {
            if ((isFullGrid())) {
                gamePane.setDisable(true);
                System.out.println("It's a Draw");
            }
        }
    }

    @FXML
    private void handelGrid00(MouseEvent event) {

    }

    @FXML
    private void handleGrid10(MouseEvent event) {
    }

    @FXML
    private void handelGrid20(MouseEvent event) {
    }

    @FXML
    private void handelGrid01(MouseEvent event) {
    }

    @FXML
    private void handelGrid11(MouseEvent event) {
    }

    @FXML
    private void handelGrid21(MouseEvent event) {
    }

    @FXML
    private void handelGrid02(MouseEvent event) {
    }

    @FXML
    private void handelGrid12(MouseEvent event) {
    }

    @FXML
    private void handelGrid22(MouseEvent event) {
    }

}
