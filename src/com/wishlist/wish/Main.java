/**
 * WishList main class
 * @Author Mantas Visockis
 */

package com.wishlist.wish;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.wishlist.wish.DatabaseAllGames.downloadGameDatabase;
import static com.wishlist.wish.DatabaseAllGames.writeToLocalDatabase;
import static com.wishlist.wish.DatabaseMyGames.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("wishlist.fxml"));
        primaryStage.setTitle("Wishlist");
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add("Viper.css");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void stop() throws FileNotFoundException, UnsupportedEncodingException {
        writeToFile();
    }

    public static void main(String[] args) throws IOException, FontFormatException
    {
        downloadGameDatabase();
        writeToLocalDatabase();
        readFromFile();
        //loadFont();

        launch(args);
    }
}
//Todos
//todo: Add ability to add custom games (not from the list)
//todo: Create About dialog information
//todo: Add ability to delete all games from wish list
//todo: Filter wish listed games based on date added
//todo: Add an icon to the app window
//todo: Colour code games based on date added
//todo: Do some css styling
//todo: Add links to quickly open main game sites (Steam, origin, etc.);
//todo: Add a search bar for the wish listed games
//todo: Add a custom font

//Bugfixes
//todo: Remove context menu from the headers of TreeTableViews

//Future todos
//todo: Add game images alongside the titles

//Potential todos
//todo: Find a way to get release dates for games
//todo: Find a way to get ratings for games
//todo: Find a way to get tags for games
//todo: Convert to a web based app, utilize an actual database
//todo: Remove some entries from the game list (non-game service apps with no real downloads, certain DLCS, packs, etc.)