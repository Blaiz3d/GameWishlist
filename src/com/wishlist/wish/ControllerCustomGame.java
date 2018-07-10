package com.wishlist.wish;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static com.wishlist.wish.DatabaseMyGames.*;

public class ControllerCustomGame implements Initializable {

    //FXML Objects
    @FXML
    private JFXTextField customTitle;

    /**
     * Adds a game to the wishlist
     */
    //FXML Actions
    @FXML
    void confirm(ActionEvent event)
    {
        String title = customTitle.getText();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        boolean isSteam = false;
        boolean gameExists = checkWishlistIfExists(title);

        if (title.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please enter a game title", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK)
            {
                alert.close();
            }
        }
        else
        {
            if (!gameExists)
            {
                addToMyGames(title, null, currentDate, isSteam);
            } else
                {
                Alert alert = new Alert(Alert.AlertType.NONE, "Game already in the wish list", ButtonType.OK);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK)
                {
                    alert.close();
                }
            }
        }

        customTitle.clear();
    }

    /**
     * Closes the window
     */
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) customTitle.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
