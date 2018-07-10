/**
 * Class for wish listed game
 */

package com.wishlist.wish;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class MyGame extends RecursiveTreeObject<MyGame>
{
    StringProperty title;
    StringProperty appid;
    StringProperty dateAdded;
    StringProperty isSteam;

    public MyGame(String title, String appid, String dateAdded, String isSteam)
    {
        this.title = new SimpleStringProperty(title);
        this.appid = new SimpleStringProperty(appid);
        this.dateAdded = new SimpleStringProperty(dateAdded);
        this.isSteam = new SimpleStringProperty(isSteam);
    }

    public String getTitle()
    {
        return title.get();
    }

    public String getAppid()
    {
        return appid.get();
    }

    public String getDateAdded()
    {
        return dateAdded.get();
    }

    public String getIsSteam()
    {
        return isSteam.get();
    }
}
