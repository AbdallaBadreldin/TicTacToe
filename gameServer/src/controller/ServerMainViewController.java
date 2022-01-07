/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Chart;
import model.ClientHandler;

import model.Server;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class ServerMainViewController implements Initializable {

    @FXML
    private Button ipBtn;
    @FXML
    private Pane paneLabel;
    @FXML
    private Label currentLabel;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Button activateBtn;
    @FXML
    private Button listOnlinebtn;
    @FXML
    private Button chartbtn;

    static ServerSocket serverSocket;
    static Thread socketAccpetListener;
    public static Vector<ClientHandler> clients;

    Server server;
    ResultSet chartData;
    public static boolean serverState;
    private boolean flageStartThrea = false;
    private boolean flageStartCharThread = true;
    private boolean onlineOrOfflineFlag = true;
    private boolean showingChart = false;
    private Thread updateListThread;
    private Thread chartThread;
    private int countOnline = 0;
    private int countOffline = 0;
    private Chart chart;
    private Stage thisStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverState = false;
        server = Server.getServer();
        disableBtn();

        updateListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true && serverState) {
                    Platform.runLater(() -> {
                        if (onlineOrOfflineFlag) {
                            listPlayers(true);
                        } else {
                            listPlayers(false);
                        }
                    });
                    try {
                        Thread.sleep(2000);

                    } catch (InterruptedException ex) {
                        emptyList();
                    }
                }
            }
        });

        chartThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true && showingChart) {
                    if (!Chart.getFlag()) {
                        ObservableList<PieChart.Data> pieChartData;
                        pieChartData
                                = FXCollections.observableArrayList(
                                        new PieChart.Data("Offline", countOffline),
                                        new PieChart.Data("Online", countOnline));

                        chart.setChartData(pieChartData);
                        Platform.runLater(() -> {
                            try {
                                chart.start(thisStage);
                            } catch (Exception ex) {
                                Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {

                        }

                    } else {
                        flageStartCharThread = false;
                        chartThread.suspend();
                    }

                }
            }
        });
    }

    @FXML
    private void getIPTapped(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Get IP");
        alert.setHeaderText(null);
        try {
            alert.setHeaderText("Server IP is :" + Inet4Address.getLocalHost().getHostAddress());
            ButtonType Yes = new ButtonType("OK");
            alert.getDialogPane().getButtonTypes().add(Yes);

            DialogPane dialogPane = alert.getDialogPane();

        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        alert.showAndWait();
    }

    @FXML
    private void toggleServer(ActionEvent event) {   
        serverState = !serverState;
        if (serverState) {
            try {
                System.out.println("toggle");
                server.enableConnections();

                enableBtn();    // enable list online and offline btn; 
                activateBtn.setText("Deactivate");
                currentLabel.setText("Status : On");

                if (Platform.isFxApplicationThread()) {
                    if (!flageStartThrea) {
                        updateListThread.start();
                    } else {
                        updateListThread.resume();
                    }
                }

            } catch (SQLException e) {
                System.out.println("Connection Issues, Try again later");
                serverState = !serverState;
            }
        } else { // state is true needed to be deactivate
            try {
                activateBtn.setText("Activate");
                currentLabel.setText("Status : OFF");
                updateListThread.suspend();
                flageStartThrea = true;
                onlineOrOfflineFlag = true;
            } finally {
                server.disableConnections();
                emptyList();
                disableBtn();
                server.stopServer();
            }
        }
    }

    @FXML
    private void toggleList(ActionEvent event) {
        if (onlineOrOfflineFlag) {
            System.out.println("list off");
            scrollpane.setContent(null);
            onlineOrOfflineFlag = false;
            listPlayers(true);
            listOnlinebtn.setText("List Online Players");

        } else {
            System.out.println("list on");
            scrollpane.setContent(null);
            onlineOrOfflineFlag = true;
            listPlayers(false);
            listOnlinebtn.setText("List Offline Players");

        }
    }

    @FXML
    private void chartHandle(ActionEvent event) {

        countOffline = server.databaseInstance.getCountOfOfflineUsers();
        showingChart = true;
        chart.setFlag(false);
        chart = Chart.getChartObj();
        thisStage = (Stage) activateBtn.getScene().getWindow();

        if (Platform.isFxApplicationThread() && showingChart) {
            if (flageStartCharThread) {
                chartThread.start();
            } else {

                chartThread.resume();
            }

        }
    }

    private void enableBtn() {
        listOnlinebtn.setDisable(false);
        chartbtn.setDisable(false);
        ipBtn.setDisable(false);
    }

    private void disableBtn() {
        listOnlinebtn.setDisable(true);
        chartbtn.setDisable(true);
        ipBtn.setDisable(true);
    }

    private void emptyList() {
        scrollpane.setContent(null);
    }

    private synchronized void listPlayers(boolean state) {

        server.databaseInstance.updateResultSet();
        scrollpane.setContent(null);
        try {
            Button button;
            VBox vbox = new VBox();
            HBox hbox;

            countOnline = 0;

            while (server.databaseInstance.getResultSet().next()) {
                if (server.databaseInstance.getResultSet().getString("status").equals(1)) {
                    //System.out.println("platform check action action");

                    ImageView view, view2;
                    view2 = null;
                    // avatar view
                    view = new ImageView(new Image(this.getClass().getResourceAsStream("/resources/avatar.png")));
                    view.setFitHeight(30);
                    view.setPreserveRatio(true);

                    // active icon view
                    if (state) {

                        view2 = new ImageView(new Image(this.getClass().getResourceAsStream("/resources/active.png")));
                        countOnline++;
                    } else {

                        view2 = new ImageView(new Image(this.getClass().getResourceAsStream("/resources/inactive.png")));
                        countOffline++;
                    }

                    view2.setFitHeight(20);
                    view2.setPreserveRatio(true);

                    button = new Button("" + server.databaseInstance.getResultSet().getString("userName"), view);
                    button.setAlignment(Pos.BOTTOM_LEFT);

                    hbox = new HBox(button, view2);
                    HBox.setMargin(view2, new Insets(10, 0, 0, 5)); // top right bottom left
                    button.getStyleClass().add("button1");
                    vbox.getChildren().add(hbox);

                    scrollpane.setContent(vbox);

                }
            }
            server.databaseInstance.getResultSet().beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
