package models;

import enums.MovieGenre;

/**
 * Created by cj on 10/12/15.
 */
public class Movie {

    private String title;
    private String director;
    private MovieGenre genre;
    private int rating;
    private String coverUrl;

    public Movie (String title, String director, MovieGenre genre, int raiting){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = raiting;
        this.coverUrl = "http://www.yourwebgraphics.com/gallery/data/thumbnails/392/3D-Women-Question-mark-01.png";

    }
    public Movie (String title, String director, MovieGenre genre, int raiting, String coverUrl){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = raiting;
        this.coverUrl = coverUrl;
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
}
