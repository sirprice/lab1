package controllers;

import javafx.collections.ObservableList;
import models.Album;

/**
 * Created by cj on 09/12/15.
 */
public class DeleteController {

    public void deleteAlbum(ObservableList<Album> albums, int index){
        if (index>=0)
            albums.remove(index);
    }

    public void deleteMovie(){

    }

}
