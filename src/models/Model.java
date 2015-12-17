package models;

import controllers.MainController;
import enums.AlbumGenre;
import enums.MovieGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by cj on 07/12/15.
 */
public class Model {

    private ObservableList<Album> newAlbums = FXCollections.observableArrayList();
    private MainController mainController;
    private User user;
    private ObservableList<Album> albums;
    private ObservableList<Movie> movies;
    private ObservableList<AlbumGenre> albumGenreList;
    private ObservableList<MovieGenre> movieGenreList;
    private ObservableList<Integer> ratingList;
    private JDBCDatabase database;
    private SQLQueries queries;

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

    public void createMovie(String title, String genre, int directorID){
        database.insertMovie(queries.insertMovieQuery(title,genre,directorID));
    }

    public void deleteMovie(int movieId){
        database.dropMovie(queries.dropMovieQuery(movieId));
    }

    public void getNewAlbums(){
        Thread thread = new Thread(){
            public void run(){
                albums = database.getAlbums(queries.getAllAlbums);
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                setAlbums(albums);
                                mainController.refreshAlbums();
                            }
                        }
                );
            }
        };thread.start();
    }

    public Album getAlbum(int index) {
        return albums.get(index);
    }

    public void setAlbums(ObservableList<Album> albums) {
        this.albums = albums;
    }

    public ObservableList<Album> getAlbums(){
        return albums;
    }
    public void createAlbum(String title, String genre, int artistID){
        database.insertAlbum(queries.insertAlbumQuery(title,genre,artistID));
    }

    public void deleteAlbum(int albumID){
        database.dropAlbum(queries.dropAlbumQuery(albumID));
    }

    public int getArtistId(String artist){
        return database.getArtistByName(queries.getArtistByName(artist));
    }
    public void createDirector(String name){
        database.insertNewDirector(queries.insertDirector(name));
    }

    public int getDirectorId(String name){
        return database.getDirectorByName(queries.getDirectorByName(name));
    }

    public void createArtist(String name){
        database.insertNewArtist(queries.insertArtist(name));
    }

    public boolean authentcateUser(String username, String password){
        user = database.userAuthentication(queries.authenticateUser(username,password));

        if (user == null){
            System.out.println("User == null  ");
            return false;
        }
        if (user != null){
            System.out.println("User != null  " + user.toString());
            return true;
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setAlbum(int index, Album album) {
        albums.set(index,album);
    }

    public void addAlbum(Album newAlbum){
        albums.add(newAlbum);
    }

    public void getNewMovies() {
        Thread thread = new Thread(){
            public void run(){
                movies = database.getMovies(queries.getAllMovies);
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
    public Movie getMovie(int index){
        return movies.get(index);
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ObservableList<Movie> movies) {
        this.movies = movies;
    }
    public void addMovie(Movie newMovie){
        movies.add(newMovie);
    }

    public ObservableList<AlbumGenre> getAlbumGenreList() {
        return albumGenreList;
    }

    public void setAlbumGenreList(ObservableList<AlbumGenre> albumGenreList) {
        this.albumGenreList = albumGenreList;
    }

    public ObservableList<MovieGenre> getMovieGenreList() {
        return movieGenreList;
    }

    public void setMovieGenreList(ObservableList<MovieGenre> movieGenreList) {
        this.movieGenreList = movieGenreList;
    }

    public ObservableList<Integer> getRatingList() {
        return ratingList;
    }

    public void setRatingList(ObservableList<Integer> ratingList) {
        this.ratingList = ratingList;
    }

    public void setJDBCDatabase(JDBCDatabase database) {
        this.database = database;
    }
}
