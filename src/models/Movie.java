package models;

import enums.MovieGenre;

import java.util.ArrayList;

/**
 * Created by cj on 10/12/15.
 */
public class Movie {

    private String title;
    private String director;
    private MovieGenre genre;
    private int rating;
    private int movieID;
    private String coverUrl;
    private ArrayList<String> reviews;

    public Movie (int movieID,String title, String director, MovieGenre genre,String coverUrl){
        this.movieID = movieID;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.coverUrl = coverUrl;

    }

    public int getMovieID() {
        return movieID;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String toString(){
        String info ="Title: " + title +
                        "Director: " + director +
                        "Genre: " + genre +
                        "CoverUrl: " + coverUrl;
        return info;
    }

}
