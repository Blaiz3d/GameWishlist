package com.wishlist.wish;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Game extends RecursiveTreeObject<Game>
{
    StringProperty title;
    StringProperty appid;

    public Game(String title, String appid)
    {
        this.title = new SimpleStringProperty(title);
        this.appid = new SimpleStringProperty(appid);
    }

    public String getTitle()
    {
        return title.get();
    }

    public String getAppid()
    {
        return appid.get();
    }
}
