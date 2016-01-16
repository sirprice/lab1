package models;

import controllers.MainController;
import enums.AlbumGenre;
import enums.MovieGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cj on 07/12/15.
 */
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
    private SQLQueries queries;
    private boolean exists = false;

    public Model(){
        albums = FXCollections.observableArrayList();
        movies = FXCollections.observableArrayList();
        albumGenreList = FXCollections.observableArrayList();
        movieGenreList = FXCollections.observableArrayList();
        ratingList = FXCollections.observableArrayList(1,2,3,4,5);
        queries = new SQLQueries();

        for (AlbumGenre ag: AlbumGenre.values()){
            albumGenreList.add(ag);
        }

        for (MovieGenre mg: MovieGenre.values()){
            movieGenreList.add(mg);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public boolean authentcateUser(String username, String password){

        user = database.userAuthentication(queries.authenticateUser(username,password));

        if (user != null){
            System.out.println("User != null  " + user.toString());
            return true;
        }
        return false;
    }

    public boolean checkForUser(String username){

        user = database.userAuthentication(queries.checkForUser(username));

        if (user != null){
            user = null;
            return true;
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setJDBCDatabase(JDBCDatabase database) {
        this.database = database;
    }


    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //                                              ALBUM
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    public Album getAlbum(int index) {
        return albums.get(index);
    }

    public Album getAlbumById(int id) {
        for (Album a : albums){
            if (a.getAlbumID() == id){
                return a;
            }
        }
        return null;
    }

    public ObservableList<Album> getAlbums(){

        return albums;
    }

    public void getNewAlbums(){
        Thread thread = new Thread(){
            public void run(){
                ObservableList<Album> newAlbums = database.getAlbums(queries.getAllAlbums);
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

    public void setAlbums(ObservableList<Album> albums) {
        this.albums = albums;
    }

    public void createArtist(String name){
        database.insertNewArtist(queries.insertArtist(name));
    }

    public void createAlbum(String title, String genre, int artistID){
        // todo check if thread
        database.insertAlbum(queries.insertAlbumQuery(title,genre,artistID,user.getUserID()));
    }

    public void editAlbum(int albumId,String artist, String genre, String title, String url){

        boolean albumExists = false;
        this.artist = artist;
        System.out.println("album ID : "+albumId);
        for(Album a: getAlbums()){
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
                        createArtist(queries.getArtistByName(artist));
                        artistID = getArtistId(artist);
                        database.alterAlbum(queries.editAlbum(albumId,title,genre,artistID,url));
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
                        database.alterAlbum(queries.editAlbum(albumId,title,genre,artistID,url));
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
        database.dropAlbum(queries.dropAlbumQuery(albumID));
    }

    public int getArtistId(String artist){
        return database.getArtistByName(queries.getArtistByName(artist));
    }

    public void getSearchForAlbums(String searchWord, int item){
        Thread thread = new Thread(){
            public void run(){
                ObservableList<Album> newAlbums = database.getAlbums(queries.searchAlbums(searchWord, item));
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

        Thread thread = new Thread(){
            public void run(){
                getAlbumById(id).setRating(database.getAvgRating(queries.getAlbumRating(id)));
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

    public void addAlbumReview(int usrID, int albumID, Date date, String text, int rating){

        Thread thread = new Thread(){
            public void run(){
                String question = queries.albumAlreadyReviewed(usrID,albumID);
                Review review = database.checkIfReviewAlreadyExist(question);
                if (review == null){
                    String question2 = queries.addReviewAlbum(usrID,albumID,date,text,rating);
                    database.insertNewReview(question2);
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

    public void updateAlbumReview(int usrID, int albumID, Date date, String text, int rating){
        Thread thread = new Thread(){
            public void run(){
                String question = queries.updateAlbumReview(usrID,albumID, date, text, rating);
                database.executeUpdate(question);
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

    public void deleteAlbumReview(int usrID, int albumID){
        Thread thread = new Thread(){
            public void run(){
                String question = queries.deleteAlbumReview(usrID,albumID);
                database.executeUpdate(question);
            }
        };thread.start();
    }

    public ArrayList<Review> getAlbumReviews(int albumID){
        return database.getReviews(queries.getAlbumReviews(albumID));
    }

    public Review getAlbumReview(int usrID, int albumID){
        String question = queries.albumAlreadyReviewed(usrID,albumID);
        Review review = database.checkIfReviewAlreadyExist(question);

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

    public void createMovie(String title, String genre, int directorID){
        database.insertMovie(queries.insertMovieQuery(title,genre,directorID, user.getUserID()));
    }

    public void deleteMovie(int movieId){
        database.dropMovie(queries.dropMovieQuery(movieId));
    }

    public void createDirector(String name){
        database.insertNewDirector(queries.insertDirector(name));
    }

    public int getDirectorId(String name){
        return database.getDirectorByName(queries.getDirectorByName(name));
    }


    public void createUser(String username, String password){
        database.insertNewUser(queries.insertUser(username,password));
    }
    public void editMovie(int movieID,String director, String genre, String title, String url){

        boolean movieExists = false;
        //this.artist = artist;

        for(Movie m: getMovies()){
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
                        createArtist(queries.getDirectorByName(director));
                        directorID = getDirectorId(director);
                        database.alterMovie(queries.editMovie(movieID,title,genre,directorID,url));
                        System.out.println(user.toString() + "editFunktion:");
                        getNewMovies();
                    }else {
                        database.alterAlbum(queries.editMovie(movieID,title,genre,directorID,url));
                        System.out.println(user.toString() + "editFunktion:");
                        getNewMovies();
                    }


                    //albums = database.getAlbums(queries.getAllAlbums);

                }
            };thread.start();
        }
    }

    public void addMovieReview(int usrID, int movieID, Date date, String text, int rating){

        Thread thread = new Thread(){
            public void run(){
                String question = queries.movieAlreadyReviewed(usrID,movieID);
                Review review = database.checkIfReviewAlreadyExist(question);
                if (review == null){
                    String question2 = queries.addReviewMovie(usrID,movieID,date,text,rating);
                    database.insertNewReview(question2);
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

    public void updateMovieReview(int userID, int movieID, Date date, String text, int rating){
        Thread thread = new Thread(){
            public void run(){
                String question = queries.updateMovieReview(userID,movieID, date, text, rating);
                database.executeUpdate(question);
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
        Thread thread = new Thread(){
            public void run(){
                getMovieById(id).setRating(database.getAvgRating(queries.getMovieRating(id)));
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

    public Review getMovieReview(int usrID, int movieID){

        String question = queries.albumAlreadyReviewed(usrID,movieID);
        Review review = database.checkIfReviewAlreadyExist(question);


        return review;
    }
    public ArrayList<Review> getMovieReviews(int movieID){

        return database.getReviews(queries.getMovieReviews(movieID));
    }



    public void deleteMovieReview(int userID, int movieID){
        Thread thread = new Thread(){
            public void run(){
                String question = queries.deleteMovieReview(userID,movieID);
                database.executeUpdate(question);
            }
        };thread.start();
    }

    public void getNewMovies() {
        Thread thread = new Thread(){
            public void run(){
                movies = database.getMovies(queries.getAllMovies);
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
                ObservableList<Movie> newMovies = database.getMovies(queries.searchMovies(searchWord, item));
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
