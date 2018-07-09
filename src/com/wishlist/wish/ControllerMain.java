/**
 * Manages the GUI element functionality
 */

package com.wishlist.wish;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static com.wishlist.wish.DatabaseAllGames.getGamesAll;
import static com.wishlist.wish.DatabaseMyGames.*;

public class ControllerMain implements Initializable{

    //FXML Objects
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private JFXTreeTableView<MyGame> treeView;
    @FXML
    private JFXTreeTableView<Game> treeViewAll;
    @FXML
    private JFXTextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        /**
         * Creates and loads the columns in the JFXTreeTableView
         */
        // ------------------------------------ TreeView columns -----------------------------------------------------
        // Title column
        JFXTreeTableColumn<MyGame, String> title = new JFXTreeTableColumn<>("Title");
        title.setCellValueFactory(param -> param.getValue().getValue().title);

        // Date added column
        JFXTreeTableColumn<MyGame, String> dateAdded = new JFXTreeTableColumn<>("Date Added");
        dateAdded.setCellValueFactory(param -> param.getValue().getValue().dateAdded);
        // -----------------------------------------------------------------------------------------------------------
        final TreeItem<MyGame> root = new RecursiveTreeItem<>(getGamesMy(), RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(title, dateAdded);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        // ------------------------------------ TreeViewAll columns --------------------------------------------------
        // Title column
        JFXTreeTableColumn<Game, String> titleAll = new JFXTreeTableColumn<>("Title");
        titleAll.setCellValueFactory(param -> param.getValue().getValue().title);
        // -----------------------------------------------------------------------------------------------------------
        final TreeItem<Game> rootAll = new RecursiveTreeItem<>(getGamesAll(), RecursiveTreeObject::getChildren);

        treeViewAll.getColumns().setAll(titleAll);
        treeViewAll.setRoot(rootAll);
        treeViewAll.setShowRoot(false);
        // -----------------------------------------------------------------------------------------------------------

        /**
         * Filters all games by the text typed in the search field
         */
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                treeViewAll.setPredicate(gameTreeItem -> {
                    Boolean flag = gameTreeItem.getValue().title.getValue().toLowerCase().contains(newValue.toLowerCase());
                    return flag;
                }));

        /**
         * Dynamically adjusts the width of tabs to take up full width of the window
         */
        tabPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setTabMinWidth(tabPane.getWidth() / 2.02124);
            tabPane.setTabMinHeight(50);
            tabPane.setTabMaxWidth(tabPane.getWidth() / 2.02124);
            tabPane.setTabMaxHeight(60);
        });

        /**
         * Dynamically adjusts the width of columns in TableTreeView to take up full width of the window (1 column)
         */
        treeView.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            TreeTableColumn<MyGame, ?> col1 = treeView.getColumns().get(treeView.getColumns().size()-1);
            TreeTableColumn<MyGame, ?> col2 = treeView.getColumns().get(treeView.getColumns().size()-2);
            col1.setPrefWidth(treeView.getWidth() / 4.4 );
            col2.setPrefWidth(treeView.getWidth() / 1.3 );
        });

        /**
         * Dynamically adjusts the width of columns in TableTreeViewAll to take up full width of the window (2 columns)
         */
        treeViewAll.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            TreeTableColumn<Game, ?> col = treeViewAll.getColumns().get(treeViewAll.getColumns().size()-1);
            col.setPrefWidth(treeViewAll.getWidth() / 1.02 );
        });

        /**
         * Creates the context menu for games in the treeView
         */
        treeView.setRowFactory((TreeTableView<MyGame> ttv) ->
        {
            ContextMenu context = new ContextMenu();
            MenuItem item1 = new MenuItem("Look up online");
            MenuItem item2 = new MenuItem("Remove from wishlist");
            context.getItems().addAll(item1, item2);

            TreeTableRow<MyGame> row = new TreeTableRow<MyGame>()
            {
                @Override
                public void updateItem(MyGame item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (empty)
                    {
                        setContextMenu(null);
                    }
                    else
                    {
                        setContextMenu(context);
                    }
                }
            };

            /**
             * Opens the steamstore page for the selected app in the treeView
             */
            item1.setOnAction(event -> {
                String appid = treeView.getSelectionModel().getSelectedItem().getValue().getAppid();
                try {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI("https://store.steampowered.com/app/" + appid));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });

            /**
             * Removes a game from the wishlist
             */
            item2.setOnAction(event -> {
                MyGame gameToDelete = treeView.getSelectionModel().getSelectedItem().getValue();
                TreeItem<MyGame> rowToDelete = treeView.getSelectionModel().getSelectedItem();
                rowToDelete.getParent().getChildren().remove(rowToDelete);
                treeView.getSelectionModel().clearSelection();
                removeGame(gameToDelete);
            });
            return row;
        });

        /**
         * Creates the context menu for games in the treeViewAll
         */
        treeViewAll.setRowFactory(ttv ->
        {
            ContextMenu context = new ContextMenu();
            MenuItem item1 = new MenuItem("Add to wishlist");
            MenuItem item2 = new MenuItem("Look up online");
            context.getItems().addAll(item1, item2);

            TreeTableRow<Game> row = new TreeTableRow<Game>()
            {
                @Override
                public void updateItem(Game item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (empty)
                    {
                        setContextMenu(null);
                    }
                    else
                    {
                        setContextMenu(context);
                    }
                }
            };

            /**
             * Checks if the game is in the wish list and adds it if it's not
             */
            item1.setOnAction(event -> {
                String titleSelected = treeViewAll.getSelectionModel().getSelectedItem().getValue().getTitle();
                String appid = treeViewAll.getSelectionModel().getSelectedItem().getValue().getAppid();

                boolean gameExists = checkWishlist(appid);

                if (!gameExists)
                {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                    Date date = new Date();
                    String currentDate = dateFormat.format(date);

                    addToMyGames(titleSelected, appid, currentDate);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.NONE, "Game already in the wish list", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                }
            });

            /**
             * Opens the steamstore page for the selected app in the treeViewAll
             */
            item2.setOnAction(event -> {
                String appid = treeViewAll.getSelectionModel().getSelectedItem().getValue().getAppid();
                try {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI("https://store.steampowered.com/app/" + appid));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
            return row;
        });
    }
}
//todo: Check if game is already in wishlist before adding
//todo: Remove context menu from the headers of TreeTableViews