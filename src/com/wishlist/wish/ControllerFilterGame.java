package com.wishlist.wish;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerFilterGame implements Initializable {

    //FXML Objects
    @FXML
    private JFXTextField textField;
    @FXML
    public JFXTreeTableView treeView;

    @FXML
    void confirm(ActionEvent event)
    {
        String input = textField.getText();

        if (input.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please enter a number", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK)
            {
                alert.close();
            }
        }
        else
        {
            try {
                int months = Integer.parseInt(input);
                if (months < 0)
                {
                    Alert alert = new Alert(Alert.AlertType.NONE, "Please enter a valid positive number", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK)
                    {
                        alert.close();
                    }
                }
                else
                    {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, -months);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                    Date result = cal.getTime();

                    System.out.println(dateFormat.format(result));
                        System.out.println(treeView.getExpandedItemCount());


                    /*treeView.setPredicate(gameTreeItem -> {
                        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/YYYY");
                        // Boolean flag = gameTreeItem.getValue().dateAdded.getValue().toLowerCase().contains(newValue.toLowerCase());
                        Boolean flag = null;
                        try
                        {

                            flag = dateFormat2.parse(gameTreeItem.getValue().dateAdded.getValue()).before(date);
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                            return flag;
                        });*/



                    Stage stage = (Stage) textField.getScene().getWindow();
                    stage.close();
                }
            }
            catch (NumberFormatException n)
            {
                Alert alert = new Alert(Alert.AlertType.NONE, "Please enter a valid number", ButtonType.OK);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            }
            textField.clear();
        }

    }

    /**
     * Closes the window on cancel button
     */
    @FXML
    void cancel(ActionEvent event)
    {
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    public void setTreeView(JFXTreeTableView treeView)
    {
        this.treeView = treeView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
