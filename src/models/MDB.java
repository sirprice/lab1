package models;

import javafx.collections.ObservableList;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by cj on 28/01/16.
 */
public class MDB implements Screwdriver {


    public MDB(){

    }


    @Override
    public ObservableList<Album> getAllAlbums() {
        return null;
    }

    @Override
    public ObservableList<Album> getAlbumsBySearch(String searchWord, int item) {
        return null;
    }

    @Override
    public ObservableList<Movie> getAllMovies() {
        return null;
    }

    @Override
    public ObservableList<Movie> getMoviesBySearch(String searchWord, int item) {
        return null;
    }

    @Override
    public void insertAlbum(String title, String genre, String userID, String artistName) {

    }

    @Override
    public void insertAlbumOnly(String title, String genre, String userID, String artistID) {

    }

    @Override
    public void alterAlbum(String albumID, String title, String genre, String artistName, String coverURL) {

    }

    @Override
    public void alterAlbumOnly(String albumID, String title, String genre, String artistID, String coverURL) {

    }

    @Override
    public void dropAlbum(String albumID) {

    }

    @Override
    public String getArtistByName(String name) {
        return name;
    }

    @Override
    public void insertMovie(String title, String genre, String userID, String directorName) {

    }

    @Override
    public void insertMovieOnly(String title, String genre, String userID, String directorID) {

    }

    @Override
    public void alterMovie(String movieID, String title, String genre, String coverURL, String directorName) {

    }

    @Override
    public void alterMovieOnly(String movieID, String title, String genre, String directorID, String coverURL) {

    }

    @Override
    public void dropMovie(String movieID) {

    }

    @Override
    public String getDirectorByName(String name) {
        return name;
    }

    @Override
    public User userAuthentication(String username, String password) {

        DBCollection coll = db.getCollection("User");
        DBObject myDoc = coll.findOne();
        System.out.println(myDoc);
        return null;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void insertNewUser(String username, String password) {

    }

    @Override
    public void insertNewReview(String userID, String mediaID, Date date, String text, int rating, int mediaType) {

    }

    @Override
    public double getAvgRating(String id, int mediaType) {
        return 0;
    }

    @Override
    public Review checkIfReviewAlreadyExist(String usrID, String mediaID, int mediaType) {
        return null;
    }

    @Override
    public ArrayList<Review> getAlbumReviews(String albumID) {
        return null;
    }

    @Override
    public ArrayList<Review> getMovieReviews(String movieID) {
        return null;
    }

    @Override
    public void deleteReview(String usrID, String mediaID, int mediaType) {

    }

    @Override
    public void updateReview(String userID, String albumID, Date date, String text, int rating, int mediaType) {

    }
}
