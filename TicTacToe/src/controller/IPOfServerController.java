package controller;

import com.jfoenix.controls.JFXButton;
import helpers.IPValidation;
import helpers.Navigation;
import helpers.ReusableDialog;
import helpers.SocketSingleton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.GameOnlineMode;
import model.PlayerMove;
import model.PlayerMoveOnlineMode;
import model.RequestGameOnlineMode;

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

    boolean checkip = false;

    static Thread listener;
    static Socket socket;
    static ObjectInputStream objInputStream;
    static ObjectOutputStream objOutputStream;

    private GameOnlineMode game;

    private final static Navigation nav = new Navigation();
    //private GameClient client;
    @FXML
    private JFXButton imgBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onConnectBtnClick(MouseEvent event) {
        try {
            changeSceneToOnlineGame(event);
        } catch (IOException ex) {
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onBackImageClick(MouseEvent event) {
        try {
            nav.navigateTo(event, Navigation.MAIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean connection(String s) {

        if (IPValidation.isValidIPAddress(s)) {

            return true;

        } else {
            serverIpTextField.setPromptText("Please Enter Valid Ip");
            serverIpTextField.selectAll();
            checkip = false;
            return false;
        }
    }

    public void changeSceneToOnlineGame(MouseEvent event) throws IOException {
        System.out.println(checkip);

        Boolean isCancled = false;
        socket = SocketSingleton.getInstanceOf(serverIpTextField.getText());
        objInputStream = SocketSingleton.getObjectInputStream();
        objOutputStream = SocketSingleton.getObjectOutputStream();

        //System.out.println("you entered ip =" + serverIpTextField.getText());
        if (!isCancled) {
            boolean conn = connection(serverIpTextField.getText());
            if (conn) {
                checkip = true;
                nav.navigateTo(event, Navigation.LOGIN_SCREEN);
            } else {
                serverIpTextField.setPromptText("You entered a wrong ip please try again");
                serverIpTextField.selectAll();

            }

        }
    }

    public static synchronized void allReadObjectsAndWrite(ActionEvent event) {
        ReusableDialog dialog = new ReusableDialog();
        listener = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (IPOfServerController.objInputStream != null) {
                        try {
                            Object objToRead = objInputStream.readObject();
                            if (objToRead instanceof String) {
                                String objectToCheck = (String) objToRead;
                                if (objectToCheck.equals("Already registered")) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.showErrorDialog((String) objToRead, "Registration failed");
                                        }
                                    });
                                } else {
                                    try {
                                        nav.navigateTo(event, Navigation.PLAYER_ONLINE);
                                    } catch (IOException ex) {
                                        Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                                System.out.println(objToRead);
                            } else {

                            }
                        } catch (ClassNotFoundException | IOException ex) {
                            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }

            }
        };

        listener.setDaemon(true);
        listener.start();
    }

    private void connectFunction(ActionEvent event) {

        try {
            socket = new Socket(serverIpTextField.getText(), 5006);

            objOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objInputStream = new ObjectInputStream(socket.getInputStream());
            ((Button) event.getSource()).setDisable(true);

            listener = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Read from server Thread");
                        if (objInputStream != null) {
                            try {
                                Object obj = objInputStream.readObject();
                                System.out.println("Object Recieved");
                                if (obj instanceof PlayerMove) {
                                    PlayerMoveOnlineMode move = (PlayerMoveOnlineMode) obj;
                                    game.addMove(move);
                                    //updateUI(move);
                                } else if (obj instanceof RequestGameOnlineMode) {
                                    //acceptRequest.setDisable(false);
                                    //declineRequest.setDisable(false);
                                    //gameRequest = (RequestGame) obj;
                                } else if (obj instanceof GameOnlineMode) {
                                    game = (GameOnlineMode) obj;
                                } else if (obj instanceof String) {
                                    System.out.println((String) obj);

                                }

                            } catch (IOException | ClassNotFoundException ex) {

                                showErrorDialog("Server disconnected");
                                closeConnection();
                                ex.printStackTrace();
                            }
                        } else {
                            System.out.println("ObjectStream is null");
                        }
                    }
                }
            };
            listener.start();

        } catch (IOException ex) {
            showErrorDialog("Cannot connect to the server!\nPlease check your connection.");
        }
    }

    private void sendRequesResultToServer(RequestGameOnlineMode request) {
        try {
            objOutputStream.writeObject(request);
            objOutputStream.flush();
        } catch (IOException ex) {
            showErrorDialog("Server disconneted");
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showErrorDialog(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
            }
        });
    }

    private void closeConnection() {
        try {
            listener.stop();
            objInputStream.close();
            objOutputStream.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(IPOfServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    private String returnSymbol() {
        //  String symbol;
        if (isXSymbol == true) {
            symbol = "X";
            // symbol.setFont(Font.font(18));
        } else {
            symbol = "O";
        }
        isXSymbol = !isXSymbol;
        return symbol;
    }

    private void disableAllButtons(boolean b) {
        btn00.setDisable(b);
        btn01.setDisable(b);
        btn02.setDisable(b);
        btn10.setDisable(b);
        btn11.setDisable(b);
        btn12.setDisable(b);
        btn20.setDisable(b);
        btn21.setDisable(b);
        btn22.setDisable(b);
    }

    private void checkRows() {
        if (btn00.getText().equals(btn01.getText()) && btn01.getText().equals(btn02.getText()) && !btn00.getText().equals("")) {
            drawLine(btn00, btn02);
            colorBackgroundWinnerButtons(btn00, btn01, btn02);
            //paneWalid.setDisable(true);
            if (btn00.getText().equals("X")) {
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;
            //  paneWalid.setDisable(true);
        } else if (btn10.getText().equals(btn11.getText())
                && btn11.getText().equals(btn12.getText())
                && !btn10.getText().equals("")) {
            drawLine(btn10, btn12);
            colorBackgroundWinnerButtons(btn10, btn11, btn12);
            //paneWalid.setDisable(true);
            if (btn10.getText().equals("X")) {
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        } else if (btn20.getText().equals(btn21.getText())
                && btn21.getText().equals(btn22.getText())
                && !btn22.getText().equals("")) {
            drawLine(btn20, btn22);
            colorBackgroundWinnerButtons(btn20, btn21, btn22);
            //paneWalid.setDisable(true);
            if (btn22.getText().equals("X")) {
                System.out.println("x is winning");

                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                System.out.println("o is winning");
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        }

    }

    private void checkColumns() {
        if (btn00.getText().equals(btn10.getText())
                && btn10.getText().equals(btn20.getText())
                && !btn00.getText().equals("")) {
            drawLine(btn00, btn20);
            colorBackgroundWinnerButtons(btn00, btn10, btn20);

            if (btn00.getText().equals("X")) {
                System.out.println("x is winning");
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                System.out.println("o is winning");
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        } else if (btn01.getText().equals(btn11.getText())
                && btn11.getText().equals(btn21.getText())
                && !btn01.getText().equals("")) {
            drawLine(btn01, btn21);
            colorBackgroundWinnerButtons(btn01, btn11, btn21);

            if (btn01.getText().equals("X")) {
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        } else if (btn02.getText().equals(btn12.getText())
                && btn12.getText().equals(btn22.getText())
                && !btn02.getText().equals("")) {
            drawLine(btn02, btn22);
            colorBackgroundWinnerButtons(btn02, btn12, btn22);

            if (btn02.getText().equals("X")) {
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        }

    }

    private void checkDiagonal() {
        if (btn00.getText().equals(btn11.getText())
                && btn11.getText().equals(btn22.getText())
                && !btn00.getText().equals("")) {
            drawLine(btn00, btn22);
            colorBackgroundWinnerButtons(btn00, btn11, btn22);

            if (btn00.getText().equals("X")) {
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                System.out.println("o is winning");
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        } else if (btn02.getText().equals(btn11.getText())
                && btn11.getText().equals(btn20.getText())
                && !btn02.getText().equals("")) {
            drawLine(btn02, btn20);
            colorBackgroundWinnerButtons(btn02, btn11, btn20);

            if (btn02.getText().equals("X")) {
                System.out.println("x is winning");
                firstPlayerWinner = true;
                firstPlayerScore += 10;
            } else {
                System.out.println("o is winning");
                secondPlayerWinner = true;
                secondPlayerScore += 10;
            }
            winner = true;

        }

    }

    private void drawLine(Button b1, Button b2) {
        Bounds bound1 = b1.localToScene(b1.getBoundsInLocal());
        Bounds bound2 = b2.localToScene(b2.getBoundsInLocal());
        double x1, y1, x2, y2;
        x1 = (bound1.getMinX() + bound1.getMaxX()) / 2;
        y1 = (bound1.getMinY() + bound1.getMaxY()) / 2;
        x2 = (bound2.getMinX() + bound2.getMaxX()) / 2;
        y2 = (bound2.getMinY() + bound2.getMaxY()) / 2;
        Line line = new Line(x1, y1, x2, y2);
        pane.getChildren().add(line);

    }
    
       private void checkState() throws BackingStoreException {
        checkRows();
        checkColumns();
        checkDiagonal();
        if (firstPlayerWinner) {
            isGameEnd = true;
            System.out.println("X is win");

        } else if (secondPlayerWinner) {
            System.out.println("O is win");

            isGameEnd = true;
        } else {
            if ((isFullGrid())) {
                isGameEnd = true;
                System.out.println("It's a Draw");
                //replayAgain("Draw");
            }
        }
    }

    private boolean isFullGrid() {
        return !btn00.getText().equals("")
                && !btn01.getText().equals("")
                && !btn02.getText().equals("")
                && !btn10.getText().equals("")
                && !btn11.getText().equals("")
                && !btn12.getText().equals("")
                && !btn20.getText().equals("")
                && !btn21.getText().equals("")
                && !btn22.getText().equals("");
    }
    
    private void colorBackgroundWinnerButtons(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-background-color: yellow;");
        b2.setStyle("-fx-background-color: yellow;");
        b3.setStyle("-fx-background-color: yellow;");
    }

    private PlayerMove returnMove(Button btn) {
        PlayerMove move = null;
        if (btn == btn00) {
            move = new PlayerMove(0, 0);
        } else if (btn == btn01) {
            move = new PlayerMove(0, 1);
        } else if (btn == btn02) {
            move = new PlayerMove(0, 2);
        } else if (btn == btn10) {
            move = new PlayerMove(1, 0);
        } else if (btn == btn11) {
            move = new PlayerMove(1, 1);
        } else if (btn == btn12) {
            move = new PlayerMove(1, 2);
        } else if (btn == btn20) {
            move = new PlayerMove(2, 0);
        } else if (btn == btn21) {
            move = new PlayerMove(2, 1);
        } else if (btn == btn22) {
            move = new PlayerMove(2, 2);
        }
        return move;
    }

    private void updateUI(PlayerMove move) {

        if (move.isIsX()) {
            symbol = "X";
        } else {
            symbol = "O";
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (move.getX() == 0 && move.getY() == 0) {
                    btn00.setText(symbol);

                    btn00.setDisable(true);
                } else if (move.getX() == 0 && move.getY() == 1) {
                    btn01.setText(symbol);
                    btn01.setDisable(true);

                } else if (move.getX() == 0 && move.getY() == 2) {
                    btn02.setText(symbol);
                    btn02.setDisable(true);

                } else if (move.getX() == 1 && move.getY() == 0) {
                    btn10.setText(symbol);
                    btn10.setDisable(true);

                } else if (move.getX() == 1 && move.getY() == 1) {
                    btn11.setText(symbol);
                    btn11.setDisable(true);

                } else if (move.getX() == 1 && move.getY() == 2) {
                    btn12.setText(symbol);
                    btn12.setDisable(true);

                } else if (move.getX() == 2 && move.getY() == 0) {
                    btn20.setText(symbol);
                    btn20.setDisable(true);

                } else if (move.getX() == 2 && move.getY() == 1) {
                    btn21.setText(symbol);
                    btn21.setDisable(true);

                } else if (move.getX() == 2 && move.getY() == 2) {
                    btn22.setText(symbol);
                    btn22.setDisable(true);
                }
            }

        });
        System.out.println("Symbol = " + symbol);

    } */
}
