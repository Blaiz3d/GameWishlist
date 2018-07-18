/**
 * Manages the GUI element functionality
 */

package com.wishlist.wish;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.SimpleStyleableStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
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
    @FXML
    private JFXTextField searchFieldMy;
    @FXML
    private JFXButton button1;
    @FXML
    private JFXButton button2;
    @FXML
    private Menu openSite;

    //FXML Methods
    @FXML
    void addCustomGame(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/customGame.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("com/wishlist/resources/images/favicon.png"));
        stage.setTitle("Add a custom game");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/wishlist/resources/css/Viper.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void filterMyGames(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        FXMLLoader fxmlLoader2 = new FXMLLoader();

        fxmlLoader1.setLocation(getClass().getResource("../resources/fxml/wishlist.fxml"));
        Parent root1 = fxmlLoader1.load();
        ControllerMain controllerMain = fxmlLoader1.getController();

        fxmlLoader2.setLocation(getClass().getResource("../resources/fxml/filterGame.fxml"));
        Parent root2 = fxmlLoader2.load();
        ControllerFilterGame controllerFilter = fxmlLoader2.getController();
        //Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/filterGame.fxml"));
        //fxmlLoader.load();

        //ControllerFilterGame filter = fxmlLoader.getController();
       // filter.setTreeView(treeView);


        Stage stage = new Stage();
        stage.getIcons().add(new Image("com/wishlist/resources/images/favicon.png"));
        stage.setTitle("Games older than");
        Scene scene = new Scene(root2);


        controllerFilter.setController(controllerMain);

        scene.getStylesheets().add("com/wishlist/resources/css/Viper.css");
        stage.setScene(scene);

        stage.show();
        //stage.showAndWait();
        //filterOldGames();
    }

    @FXML
    void openAbout(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/about.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("com/wishlist/resources/images/favicon.png"));
        stage.setTitle("About");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/wishlist/resources/css/Viper.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteAll(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../resources/fxml/deleteAll.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("com/wishlist/resources/images/favicon.png"));
        stage.setTitle("About");
        Scene scene = new Scene(root);

        ControllerDeleteAll controller = fxmlLoader.getController();
        controller.setTreeView(treeView);

        scene.getStylesheets().add("com/wishlist/resources/css/Viper.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openSteam(ActionEvent event) throws URISyntaxException, IOException
    {
        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://store.steampowered.com"));
    }

    @FXML
    void openOrigin(ActionEvent event) throws URISyntaxException, IOException
    {
        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.origin.com"));
    }

    @FXML
    void openGog(ActionEvent event) throws URISyntaxException, IOException
    {
        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.gog.com/"));
    }

    /**
     * Manages the enabling/disabling of the filter button
     */
    public void enableFilterButton(boolean empty)
    {
        if (!empty)
        {
            button2.setDisable(false);
        }
        else
        {
            button2.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        enableFilterButton(checkWishlistIfAny());
        openSite.setStyle("-fx-text-fill: white");
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
         * Filters wish listed games by the text typed in the search field
         */
        searchFieldMy.textProperty().addListener((observable, oldValue, newValue) ->
                treeView.setPredicate(gameTreeItem -> {
                    Boolean flag = gameTreeItem.getValue().title.getValue().replace("'", "").toLowerCase().contains(newValue.toLowerCase());
                    return flag;
                }));



        /**
         * Filters all games by the text typed in the search field
         */
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                treeViewAll.setPredicate(gameTreeItem -> {
                    Boolean flag = gameTreeItem.getValue().title.getValue().replace("'", "").toLowerCase().contains(newValue.toLowerCase());
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
            col2.setPrefWidth(treeView.getWidth() / 1.32 );
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
                String isSteam = treeView.getSelectionModel().getSelectedItem().getValue().getIsSteam();
                try {
                    if (isSteam.equals("y"))
                    {
                        String appid = treeView.getSelectionModel().getSelectedItem().getValue().getAppid();
                        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://store.steampowered.com/app/" + appid));
                    }
                    else
                    {
                        String titleToSearch = treeView.getSelectionModel().getSelectedItem().getValue().getTitle();
                        titleToSearch = titleToSearch.replaceAll(" ", "+");
                        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.google.com/search?q=" + titleToSearch));
                    }
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
                enableFilterButton(checkWishlistIfAny());

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

                boolean gameExists = checkWishlistIfExists(titleSelected);

                if (!gameExists)
                {
                    String appid = treeViewAll.getSelectionModel().getSelectedItem().getValue().getAppid();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                    Date date = new Date();
                    String currentDate = dateFormat.format(date);
                    boolean isSteam = true;

                    addToMyGames(titleSelected, appid, currentDate, isSteam);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.NONE, "Game already in the wish list", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                }
                enableFilterButton(checkWishlistIfAny());
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



    public void filterOldGames(Date date)
    {
        /*treeView.setPredicate(gameTreeItem -> {
            DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            Boolean flag = null;
            try
            {
                flag = dateFormat2.parse(gameTreeItem.getValue().dateAdded.getValue()).equals(date);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            flag = gameTreeItem.getValue().title.getValue().toLowerCase().contains("counter");
            return flag;
        });*/
    }
}