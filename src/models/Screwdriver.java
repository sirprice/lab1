/**
 * Created by:
 * Carl-Johan Dahlman, cjda@kth.se
 * Waleed Hassan, waleedh@kth.se
 * on 14/12/15.
 */
package models;

import javafx.collections.ObservableList;
import java.util.ArrayList;



public interface Screwdriver {

    ObservableList<Album> getAllAlbums();

    ObservableList<Album> getAlbumsBySearch(String searchWord, int item);

    ObservableList<Movie> getAllMovies();

    ObservableList<Movie> getMoviesBySearch(String searchWord, int item);


    void insertAlbum(String title, String genre, String userID, String artistName);

    void insertAlbumOnly(String title, String genre, String userID, String artistID);

    void alterAlbum(String albumID,String title, String genre, String artistName, String coverURL);

    void alterAlbumOnly(String albumID,String title, String genre, String artistID, String coverURL);

    void dropAlbum(String albumID);

    int getArtistByName(String name);

    void insertMovie(String title, String genre, String userID, String directorName);

    void insertMovieOnly(String title, String genre, String userID, String directorID);

    void alterMovie(String movieID,String title, String genre, String coverURL, String directorName);

    void alterMovieOnly(String movieID,String title, String genre, String directorID, String coverURL);

    void dropMovie(String movieID);

    int getDirectorByName(String name);

    User userAuthentication(String username,String password);

    User getUser(String username);

    void insertNewUser(String username, String password);

    void insertNewReview(String userID, String mediaID, java.sql.Date date, String text, int rating, int mediaType);

    double getAvgRating(String id, int mediaType);

    Review checkIfReviewAlreadyExist(String usrID, String mediaID, int mediaType);

    ArrayList<Review> getAlbumReviews(String albumID);

    ArrayList<Review> getMovieReviews(String movieID);

    void deleteReview(String usrID, String mediaID, int mediaType);

    void updateReview(String userID, String albumID, java.sql.Date date, String text, int rating, int mediaType);

}
