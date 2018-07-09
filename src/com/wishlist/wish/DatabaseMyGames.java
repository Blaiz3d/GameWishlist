package com.wishlist.wish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class DatabaseMyGames
{
    private static ObservableList<MyGame> game = FXCollections.observableArrayList();

    public static void addToMyGames(String title, String appid, String currentDate)
    {
        game.add(new MyGame(title, appid, currentDate));
    }

    public static void removeGame(MyGame row )
    {
        for (int i = 0; i < game.size(); i++)
        {
            if (game.get(i) == row)
            {
                game.remove(i);
            }
        }
    }

    public static void writeToFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter("wishlist.txt", "UTF-8");
        for (int i = 0; i < game.size(); i++)
        {
            writer.print(game.get(i).getTitle() + "," + game.get(i).getAppid() + "," + game.get(i).getDateAdded() + "\n");
        }
        System.out.println(">> Saved to file");
        writer.close();
    }

    public static void readFromFile()
    {
        File file = new File("wishlist.txt");
        try
        {
            Scanner input = new Scanner(file);
            String line;
            String[] lineArray;
            String title;
            String appid;
            String dateAdded;

            int gameCount = 0;

            while (input.hasNextLine())
            {
                line = input.nextLine();
                lineArray = line.split(",");
                title = lineArray[0];
                appid = lineArray[1];
                dateAdded = lineArray[2];

                game.add(new MyGame(title, appid, dateAdded));
                gameCount++;
            }

            System.out.println(">> Read the wishlist to a List");
            System.out.println("    >> Total games added: " + gameCount);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(">> Wishlist file not found");
        }
    }

    public static ObservableList<MyGame> getGamesMy()
    {
        return game;
    }

    //todo: write and load from file
}
