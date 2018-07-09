/**
 * Stores, downloads and manages all of the games (not on the wish list)
 */

package com.wishlist.wish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;


public class DatabaseAllGames
{
    private static ObservableList<Game> game = FXCollections.observableArrayList();

    /**
     * Downloads all steam service appids and titles from the steam server
     * @throws IOException
     */
    public static void downloadGameDatabase() throws IOException
    {
        URL website = new URL("http://api.steampowered.com/ISteamApps/GetAppList/v0001/");
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream("database.json");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        System.out.println(">> Downloaded steam game list");
    }

    /**
     * Loads the downloaded json file to Game objects
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void writeToLocalDatabase() throws FileNotFoundException, UnsupportedEncodingException
    {
        File file = new File("database.json");
        Scanner input = new Scanner(file);

        String line;
        String appid;

        int linesRead = 0;
        int gameCount = 0;

        String title;

        while (input.hasNextLine())
        {
            line = input.nextLine();
            linesRead++;

            if (line.contains("appid")) {
                appid = line.replaceAll("[^\\d.]", "");
                line = input.nextLine();
                linesRead++;

                if (line.contains("\"name\":")) {
                    title = line;
                    title = title.replace("\"name\":", "");
                    title = title.replace("\t", "");
                    title = title.substring(2);
                    title = title.substring(0, title.length() - 1);

                    game.add(new Game(title, appid));
                    gameCount++;
                }
            }
        }
        input.close();

        System.out.println(">> Read the database to a List");
        System.out.println("    >> Total read lines: " + linesRead);
        System.out.println("    >> Total games added: " + gameCount);
    }

    /**
     * @return the list of all loaded games
     */
    public static ObservableList<Game> getGamesAll()
    {
        return game;
    }
}
