/**
 * Class for resource loading
 * Work in progress
 */
package com.wishlist.wish;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class resourceManager {

    /**
     * Loads the custom font
     * @throws IOException
     * @throws FontFormatException
     */
    public static void loadFont() throws IOException, FontFormatException
    {
        Font.loadFont(resourceManager.class.getResource("src/com/wishlist/resources/font/Abel-Regular.ttf").toExternalForm(), 10);

        System.out.println(">> Loaded custom font");
    }
}
