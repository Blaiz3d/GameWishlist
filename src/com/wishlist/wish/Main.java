/**
 * WishList main class
 * @Author Mantas Visockis
 */

package com.wishlist.wish;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.wishlist.wish.DatabaseAllGames.*;
import static com.wishlist.wish.DatabaseMyGames.*;
import static com.wishlist.wish.resourceManager.loadFont;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/wishlist.fxml"));
        //Image icon = new Image("com/wishlist/resources/images/favicon.ico");
        primaryStage.getIcons().add(new Image("com/wishlist/resources/images/favicon.png"));
        primaryStage.setTitle("Wishlist");
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add("com/wishlist/resources/css/Viper.css");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                writeToFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });
    }

    public void stop() throws FileNotFoundException, UnsupportedEncodingException {
        writeToFile();
        System.exit(0);
    }

    public static void main(String[] args) throws IOException, FontFormatException
    {
        //loadFont();
        downloadGameDatabase();
        writeToLocalDatabase();
        readFromFile();

        launch(args);
    }
}
//Todos
//todo: Add a custom font

//Bugfixes
//todo: Remove context menu from the headers of TreeTableViews
//todo: Find a way to unfocus context menus when not hovering over context menu

//Future todos
//todo: Add game images alongside the titles
//todo: Filter wish listed games based on date added

//Potential todos
//todo: Colour code games based on date added
//todo: Find a way to get release dates for games
//todo: Find a way to get ratings for games
//todo: Find a way to get tags for games
//todo: Convert to a web based app, utilize an actual database
//todo: Remove some entries from the game list (non-game service apps with no real downloads, certain DLCS, packs, etc.)