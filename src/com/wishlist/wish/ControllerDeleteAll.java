package com.wishlist.wish;

import com.jfoenix.controls.JFXButton;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static com.wishlist.wish.DatabaseMyGames.addToMyGames;
import static com.wishlist.wish.DatabaseMyGames.checkWishlistIfExists;
import static com.wishlist.wish.DatabaseMyGames.getGamesMy;

public class ControllerDeleteAll implements Initializable {

    //FXML Objects
    @FXML
    private JFXButton button;
    @FXML
    private JFXTreeTableView treeView;

    /**
     * Adds a game to the wishlist
     */
    //FXML Actions
    @FXML
    void confirm(ActionEvent event)
    {
        int count = treeView.getExpandedItemCount();

        for (int i = 0; i < count; i++)
        {
            treeView.getTreeItem(0).getParent().getChildren().remove(0);
            getGamesMy().remove(0);
        }
        close();
    }

    /**
     * Closes the window on cancel button
     */
    @FXML
    void cancel(ActionEvent event)
    {
        close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTreeView(JFXTreeTableView treeView)
    {
        this.treeView = treeView;
    }

    public void close()
    {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
