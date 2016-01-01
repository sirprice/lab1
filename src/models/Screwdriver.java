package models;

import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by CJ & Scalman on 13/12/15.
 */
public interface Screwdriver {

    ObservableList<Album> getAlbums(String query);

    ObservableList<Movie> getMovies(String query);

    void insertAlbum(String query);

    void alterAlbum(String query);

    void dropAlbum(String query);

    int getArtistByName(String query);

    void insertNewArtist(String query);

    void insertMovie(String query);

    void alterMovie(String query);

    void dropMovie(String query);

    int getDirectorByName(String query);

    void insertNewDirector(String query);

    User userAuthentication(String query);

    void insertNewUser(String query);

    void insertNewReview(String query);

    boolean checkIfReviewAlreadyExist(String query);

    void getReviews();



}
