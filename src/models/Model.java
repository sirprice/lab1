package models;

import enums.AlbumGenre;
import enums.MovieGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by cj on 07/12/15.
 */
public class Model {

    private ObservableList<Album> albums;
    private ObservableList<Movie> movies;
    private ObservableList<AlbumGenre> albumGenreList;
    private ObservableList<MovieGenre> movieGenreList;
    private ObservableList<Integer> ratingList;



    public Model(){
        albums = FXCollections.observableArrayList();
        movies = FXCollections.observableArrayList();
        albumGenreList = FXCollections.observableArrayList();
        movieGenreList = FXCollections.observableArrayList();
        ratingList = FXCollections.observableArrayList(1,2,3,4,5);

        for (AlbumGenre ag: AlbumGenre.values()){
            albumGenreList.add(ag);
        }

        for (MovieGenre mg: MovieGenre.values()){
            movieGenreList.add(mg);
        }
    }

    public ObservableList<Album> getAlbums() {
        return albums;
    }
    public Album getAlbum(int index) {
        return albums.get(index);
    }

    public void setAlbums(ObservableList<Album> albums) {
        this.albums = albums;
    }
    public void addAlbum(Album newAlbum){
        albums.add(newAlbum);
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ObservableList<Movie> movies) {
        this.movies = movies;
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
}
