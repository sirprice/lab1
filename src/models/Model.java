/**
 * Created by:
 * Carl-Johan Dahlman, cjda@kth.se
 * Waleed Hassan, waleedh@kth.se
 * on 14/12/15.
 * This class is connected to the database through JDBC Class
 * and works as a model container with all important data structure.
 */

package models;
import controllers.MainController;
import enums.AlbumGenre;
import enums.MovieGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class Model {

    private ObservableList<Album> newAlbums = FXCollections.observableArrayList();
    private MainController mainController;

    private int artistID;
    private int directorID;
    private String artist;
    private User user;
    private ObservableList<Album> albums;
    private ObservableList<Movie> movies;
    private ObservableList<AlbumGenre> albumGenreList;
    private ObservableList<MovieGenre> movieGenreList;
    private ObservableList<Integer> ratingList;
    private JDBCDatabase database;



    public Model(){
        albums = FXCollections.observableArrayList();
        movies = FXCollections.observableArrayList();
        albumGenreList = FXCollections.observableArrayList();
        movieGenreList = FXCollections.observableArrayList();
        ratingList = FXCollections.observableArrayList(1,2,3,4,5);
        database = new JDBCDatabase();
        for (AlbumGenre ag: AlbumGenre.values()){
            albumGenreList.add(ag);
        }

        for (MovieGenre mg: MovieGenre.values()){
            movieGenreList.add(mg);
        }
    }

    /**
     * sets the mainController.
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Checks the user input password/username matches the corresponding in the database.
     * If true login succeed.
     * @param username user input.
     * @param password user input.
     * @return false or true.
     */
    public boolean authenticateUser(String username, String password){

        user = database.userAuthentication(username,password);

        if (user != null){
            System.out.println("User != null  " + user.toString());
            return true;
        }
        return false;
    }
    /**
     * Checks user input if username already exists in the database.
     * If true user registration failed.
     * @param username user input.
     * @return false or true depends if username already exists.
     */
    public boolean checkForUser(String username){

        user = database.getUser(username);

        if (user != null){
            user = null;
            return true;
        }
        return false;
    }

    /**
     * Getter.
     * @return user object.
     */
    public User getUser() {
        return user;
    }


    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //                                              ALBUM
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    /**
     * fetches the album if the id matching the corresponding id in the model.
     * @param id specific id from the the table view.
     * @return album if it is a match else null.
     */
    public Album getAlbumById(int id) {
        for (Album a : albums){
            if (a.getAlbumID() == id){
                return a;
            }
        }
        return null;
    }

    /**
     * Album list getter and setter.
     * @return ObservableList Album.
     */
    public ObservableList<Album> getAlbums(){
        return albums;
    }
    public void setAlbums(ObservableList<Album> albums) {
        this.albums = albums;
    }

    /**
     * Runs on a separate thread and brings all the albums in the database.
     * Thread setts the album list in a run later buffer so that the UIThread updates the view.
     */
    public void getNewAlbums(){
        Thread thread = new Thread(){
            public void run(){
                ObservableList<Album> newAlbums = database.getAllAlbums();
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                setAlbums(newAlbums);
                                mainController.getAllAlbumRatings();
                            }
                        }
                );
            }
        };thread.start();
    }

    /**
     * Creates album if artist does not exists.
     * @param title album title
     * @param genre album genre
     * @param artistName artist name
     */
    public void createAlbum(String title, String genre,String artistName){
        database.insertAlbum(title,genre,user.getUserID(),artistName);
    }

    /**
     *Creates album if artist already exists.
     * @param title album title
     * @param genre album genre
     * @param artistID artist id
     */
    public void createAlbumFromExistingArtist(String title, String genre,int artistID){
        database.insertAlbumOnly(title,genre,user.getUserID(),artistID);
    }


    public void editAlbum(int albumId,String artist, String genre, String title, String url){

        boolean albumExists = false;
        this.artist = artist;


        for(Album a: database.getAllAlbums()){
            if (a.getTitle().toUpperCase().equals(title.toUpperCase())
                    && a.getArtist().toUpperCase().equals(artist.toUpperCase())){
                albumExists = true;
            }
        }

        if(!albumExists){
            Thread thread = new Thread(){
                public void run(){
                    artistID = getArtistId(artist);
                    if (artistID <= 0){

                        //createArtist(artist);
                        //artistID = getArtistId(artist);
                        database.alterAlbum(albumId,title,genre,artist,url);

                        System.out.println(user.toString() + "editFunktion:");
                        javafx.application.Platform.runLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        getNewAlbums();
                                    }
                                }
                        );

                    }else {
                        database.alterAlbumOnly(albumId,title,genre,artistID,url);

                        System.out.println(user.toString() + "editFunktion:");
                        javafx.application.Platform.runLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        getNewAlbums();
                                    }
                                }
                        );
                    }


                    //albums = database.getAlbums(queries.getAllAlbums);

                }
            };thread.start();
        }

    }

    public void deleteAlbum(int albumID){
        Thread thread = new Thread(){
            public void run(){
                database.dropAlbum(albumID);
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                getNewAlbums();
                            }
                        }
                );
            }
        };thread.start();
    }

    public int getArtistId(String artist){
        return database.getArtistByName(artist);
    }

    public void getSearchForAlbums(String searchWord, int item){
        Thread thread = new Thread(){
            public void run(){
                ObservableList<Album> newAlbums = database.getAlbumsBySearch(searchWord, item);
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                setAlbums(newAlbums);
                                mainController.refreshAlbums();
                                mainController.getAllAlbumRatings();
                            }
                        }
                );
            }
        };thread.start();
    }

    public void updateAlbumRating(int id){
        int mediaType = 1;
        Thread thread = new Thread(){
            public void run(){
                getAlbumById(id).setRating(database.getAvgRating(id, mediaType));
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                mainController.refreshAlbums();
                            }
                        }
                );
            }

        };thread.start();

    }

    public void addAlbumReview(int userID, int albumID, java.sql.Date date, String text, int rating){
        int mediaType = 1;
        Thread thread = new Thread(){
            public void run(){


                Review review = database.checkIfReviewAlreadyExist(userID,albumID,mediaType);
                if (review == null){

                    database.insertNewReview(userID,albumID,date,text,rating,mediaType);
                    javafx.application.Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    updateAlbumRating(albumID);
                                }
                            }
                    );
                }
            }
        };thread.start();

    }

    public void updateAlbumReview(int userID, int albumID, java.sql.Date date, String text, int rating){
        int mediaType = 1;
        Thread thread = new Thread(){
            public void run(){
                database.updateReview(userID,albumID, date, text, rating, mediaType);

                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                updateAlbumRating(albumID);
                            }
                        }
                );
            }
        };thread.start();

    }

    public void deleteAlbumReview(int userID, int albumID){
        int mediaType = 1;
        Thread thread = new Thread(){
            public void run(){
                database.deleteReview(userID,albumID,mediaType);
            }
        };thread.start();
    }

    public ArrayList<Review> getAlbumReviews(int albumID){
        return database.getAlbumReviews(albumID);
    }

    public Review getAlbumReview(int userID, int albumID){
        int mediaType = 1;

        Review review = database.checkIfReviewAlreadyExist(userID, albumID, mediaType);

        return review;
    }

    public ObservableList<AlbumGenre> getAlbumGenreList() {
        return albumGenreList;
    }


    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //                                              MOVIE
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


    public Movie getMovieById(int id){
        for (Movie m : movies){
            if (m.getMovieID() == id){
                return m;
            }
        }
        return null;
    }

    public void createMovie(String title, String genre, String directorName){
        database.insertMovie(title,genre, user.getUserID(), directorName);
    }

    public void createMovieFromExistingDirector(String title, String genre, int directorID){
        database.insertMovieOnly(title,genre, user.getUserID(), directorID);
    }


    public void deleteMovie(int movieId){
        Thread thread = new Thread(){
            public void run(){
                database.dropMovie(movieId);
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                getNewMovies();
                            }
                        }
                );
            }

        };thread.start();
    }

    public int getDirectorId(String name){
        return database.getDirectorByName(name);
    }


    public void createUser(String username, String password){
        database.insertNewUser(username,password);
    }
    public void editMovie(int movieID,String director, String genre, String title, String url){

        boolean movieExists = false;
        //this.artist = artist;

        for(Movie m: database.getAllMovies()){
            if (m.getTitle().toUpperCase().equals(title.toUpperCase())
                    && m.getDirector().toUpperCase().equals(director.toUpperCase())){
                movieExists = true;
            }
        }

        if(!movieExists){
            Thread thread = new Thread(){
                public void run(){
                    directorID = getDirectorId(director);
                    if (directorID <= 0){
                        database.alterMovie(movieID,title,genre,url,director);

                    }else {

                        database.alterMovieOnly(movieID,title,genre,directorID,url);
                    }
                    System.out.println(user.toString() + "editFunktion:");
                    getNewMovies();
                }
            };thread.start();
        }
    }

    public void addMovieReview(int userID, int movieID, java.sql.Date date, String text, int rating){
        int mediaType = 2;
        Thread thread = new Thread(){
            public void run(){

                Review review = database.checkIfReviewAlreadyExist(userID,movieID,mediaType);
                if (review == null){
                    database.insertNewReview(userID,movieID,date,text,rating,mediaType);
                    javafx.application.Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    updateMovieRating(movieID);
                                }
                            }
                    );
                }
            }
        };thread.start();

    }

    public void updateMovieReview(int userID, int movieID, java.sql.Date date, String text, int rating){
        int mediaType = 2;
        Thread thread = new Thread(){
            public void run(){

                database.updateReview(userID, movieID, date, text, rating, mediaType);
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                updateMovieRating(movieID);
                            }
                        }
                );
            }
        };thread.start();
    }

    public void updateMovieRating(int id){
        int mediaType = 2;
        Thread thread = new Thread(){
            public void run(){
                getMovieById(id).setRating(database.getAvgRating(id, mediaType));
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                mainController.refreshMovies();
                            }
                        }
                );
            }

        };thread.start();
    }

    public Review getMovieReview(int userID, int movieID){
        int mediaType = 2;

        Review review = database.checkIfReviewAlreadyExist(userID,movieID,mediaType);

        return review;
    }
    public ArrayList<Review> getMovieReviews(int movieID){

        return database.getMovieReviews(movieID);
    }



    public void deleteMovieReview(int userID, int movieID){
        int mediaType = 2;
        Thread thread = new Thread(){
            public void run(){
                database.deleteReview(userID,movieID,mediaType);
            }
        };thread.start();
    }


    public void getNewMovies() {
        Thread thread = new Thread(){
            public void run(){
                movies = database.getAllMovies();
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                setMovies(movies);
                                mainController.getAllMovieRatings();
                            }
                        }
                );
            }
        };thread.start();
    }

    public Movie getMovie(int index){
        return movies.get(index);
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ObservableList<Movie> movies) {
        this.movies = movies;
    }
    public ObservableList<MovieGenre> getMovieGenreList() {
        return movieGenreList;
    }

    public void getSearchForMovies(String searchWord, int item){
        Thread thread = new Thread(){
            public void run(){
                ObservableList<Movie> newMovies = database.getMoviesBySearch(searchWord, item);
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                setMovies(newMovies);
                                mainController.refreshMovies();
                                mainController.getAllMovieRatings();
                            }
                        }
                );
            }
        };thread.start();

    }

    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //                                              SKIT
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    public void setMovieGenreList(ObservableList<MovieGenre> movieGenreList) {
        this.movieGenreList = movieGenreList;
    }
    public void setAlbumGenreList(ObservableList<AlbumGenre> albumGenreList) {
        this.albumGenreList = albumGenreList;
    }

    public ObservableList<Integer> getRatingList() {
        return ratingList;
    }

    public void setRatingList(ObservableList<Integer> ratingList) {
        this.ratingList = ratingList;
    }


    public void setAlbum(int index, Album album) {
        albums.set(index,album);
    }

    public void addAlbum(Album newAlbum){
        albums.add(newAlbum);
    }
    public void addMovie(Movie newMovie){
        movies.add(newMovie);
    }
}
