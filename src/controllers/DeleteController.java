package controllers;

import javafx.collections.ObservableList;
import models.Album;
import models.Model;
import models.Movie;

/**
 * Created by cj on 09/12/15.
 */
public class DeleteController {
    private Model model;

    public void setModel(Model model){
        this.model = model;
    }

    public void deleteAlbum(Album selectedAlbum){
        if (selectedAlbum.getAlbumID() >=0){
            model.deleteAlbum(selectedAlbum.getAlbumID());
        }
    }
    public void deleteMovie(Movie selectedMovie){
        if (selectedMovie.getMovieID() >=0){

            model.deleteMovie(selectedMovie.getMovieID());
        }
    }
}
