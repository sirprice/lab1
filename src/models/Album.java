package models;

import enums.AlbumGenre;

import java.util.ArrayList;

/**
 * Created by cj on 07/12/15.
 */
public class Album {
    private String date;
    private String title;

    private int albumID;
    private AlbumGenre genre;
    private int rating;
    private String releaseDate;
    private String artist;
    private int artistID;
    private String noOfSongs;
    private ArrayList<String> reviews;


    private String coverUrl;
    //private Review review;



    public Album(int albumID, String title, String artist, AlbumGenre genre) {
        this.albumID = albumID;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.coverUrl = "http://www.yourwebgraphics.com/gallery/data/thumbnails/392/3D-Women-Question-mark-01.png";
    }

    public Album(int albumID, String title, String artist, AlbumGenre genre, String coverUrl){
        this.albumID = albumID;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.coverUrl = coverUrl;
    }



    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public AlbumGenre getGenre() {
        return genre;
    }
    public void setGenre(AlbumGenre genre) {
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

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getRealeseDate() {
        return releaseDate;
    }
    public void setRealeseDate(String realeseDate) {
        this.releaseDate = releaseDate;
    }

    public String getNoOfSongs() {
        return noOfSongs;
    }
    public void setNoOfSongs(String noOfSongs) {
        this.noOfSongs = noOfSongs;
    }

    public String toString(){
        String info ="Title: "+ title +
                        "Artist: " + artist +
                        "Genre: " + genre +
                        "CoverUrl: " + coverUrl;
        return info;
    }
}
