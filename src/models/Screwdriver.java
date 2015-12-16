package models;

import javafx.collections.ObservableList;

/**
 * Created by Scalman on 13/12/15.
 */
public interface Screwdriver {

    ObservableList<Album> getAlbums(String query);

    ObservableList<Movie> getMovies(String query);

    void insertAlbum();

    void alterAlbum();

    void dropAlbum();

    void getArtist();

    void insertMovie();

    void alterMovie();

    void dropMovie();

    void getDirector();

    void userAuthentication();

    void getReviews();



}
