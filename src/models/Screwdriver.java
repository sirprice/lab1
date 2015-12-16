package models;

import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;

/**
 * Created by Scalman on 13/12/15.
 */
public interface Screwdriver {

    ObservableList<Album> getAlbums(String query);

    ObservableList<Movie> getMovies(String query);

    void insertAlbum();

    void alterAlbum();

    void dropAlbum(String query);

    void getArtist();

    void insertMovie();

    void alterMovie();

    void dropMovie(String query);

    void getDirector();

    void userAuthentication();

    void getReviews();



}
