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
