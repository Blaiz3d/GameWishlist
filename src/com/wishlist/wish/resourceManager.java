/**
 * Class for resource loading
 * Work in progress
 */
package com.wishlist.wish;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class resourceManager {

    /**
     * Loads the custom font
     * @throws IOException
     * @throws FontFormatException
     */
    public static void loadFont() throws IOException, FontFormatException
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/com/wishlist/resources/font/Abel-Regular.ttf")));
        System.out.println(">> Loaded custom font");
    }
}
