package controllers;

import javafx.collections.ObservableList;
import models.Album;
import models.Model;

/**
 * Created by cj on 09/12/15.
 */
public class DeleteController {
    private Model model;

    public void setModel(Model model){
        this.model = model;
    }

    public void deleteAlbum(int index){
        if (index>=0){
            model.getAlbums().remove(index);
        }
        //todo you should change in the model not album
    }
    public void deleteMovie(int index){
        if (index>=0){
            model.getMovies().remove(index);
        }
    }
}
