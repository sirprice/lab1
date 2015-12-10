package models;

import enums.AlbumGenre;

/**
 * Created by cj on 07/12/15.
 */
public class Album {
    private String date;
    private String title;

    public AlbumGenre getGenre() {
        return genre;
    }

    public void setGenre(AlbumGenre genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRaiting(int rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNoOfSongs() {
        return noOfSongs;
    }

    public void setNoOfSongs(String noOfSongs) {
        this.noOfSongs = noOfSongs;
    }

    private AlbumGenre genre;
    private int rating;
    private String releaseDate;
    private String artist;
    private String noOfSongs;
    //private Review review;



    public Album(String title, String artist, AlbumGenre genre, int rating) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.artist = artist;
        // something
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRealeseDate() {
        return releaseDate;
    }

    public void setRealeseDate(String realeseDate) {
        this.releaseDate = releaseDate;
    }

}
