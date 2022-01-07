/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Mahmoud
 */
public class ReusableDialog {

    public ReusableDialog() {
    }

    boolean check = false;

    public Boolean alert(String s) {
        ButtonType Yes = new ButtonType("Yes");
        ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("Alert ASk");
        a.getDialogPane().getButtonTypes().addAll(Yes, No);
        a.setHeaderText(s);
        a.showAndWait();

        if (a.getResult() == Yes) {
            check = true;

            System.out.println("alertyes");

        } else if (a.getResult() == No) {
            check = false;
            System.out.println("alertNo");
        }
        return check;
    }

    public void serverIssueAlert(String message) {

        ButtonType yes = new ButtonType("Yes");
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("Alert Issue");
        a.getDialogPane().getButtonTypes().add(yes);
        a.setHeaderText(message);
        a.showAndWait();
    }

    public boolean alert(String message, String title) {

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle(title);
        a.getDialogPane().getButtonTypes().addAll(yes, no);
        a.setHeaderText(message);
        a.showAndWait();
        if (a.getResult() == yes) {
            return true;
        }
        return false;
    }

    public boolean askPlayAgain(String s) {

        ButtonType Yes = new ButtonType("Play Again");
        ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("Alert ASk");
        a.getDialogPane().getButtonTypes().addAll(Yes, No);
        a.setHeaderText(s);
        if (a.getResult() == Yes) {
            return true;
        } else {
            return false;
        }
    }

    public void inValidIp(String s) {
        ButtonType Ok = new ButtonType("Ok");
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("Alert Ask");
        a.getDialogPane().getButtonTypes().addAll(Ok);
        a.setHeaderText(s);
        a.showAndWait();

    }

    public void showErrorDialog(String msg, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();

    }

}
