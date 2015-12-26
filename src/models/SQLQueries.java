package models;

import java.util.Date;

/**
 * Created by cj on 16/12/15.
 */
public class SQLQueries {

    public final String getAllAlbums = "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                                        "From Album,Artist " +
                                        "Where Album.ArtistID = Artist.ID;";

    public final String getAllMovies = "Select Movie.ID, Movie.Title, Director.Name, Movie.Genre, Movie.CoverUrl " +
            "From Movie,Director " +
            "Where Movie.DirectorID = Director.ID;";

    public String insertAlbumQuery(String title, String genre, int artistID){
        return "INSERT INTO ALBUM (Title,Genre,artistID) " +
                "VALUES('"+ title +"', '"+ genre + "',"+ artistID +");";

    }

    public String dropAlbumQuery(int index){
        return "DELETE FROM ALBUM WHERE ALBUM.ID = " + index + ";";

    }

    public String insertMovieQuery(String title, String genre, int directorID){
        return "INSERT INTO Movie (Title,Genre,DirectorID) " +
                "VALUES('"+ title +"', '"+ genre + "',"+ directorID +");";

    }

    public String dropMovieQuery(int index){
        return "DELETE FROM MOVIE WHERE MOVIE.ID = " + index + ";";

    }

    public String albumByArtistSearchQuery(String searchWord){
        return "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                "From Album,Artist " +
                "Where Artist.Name = " + searchWord + ";";

    }

    public String albumByTitleSearchQuery(String searchWord){
        return "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                "From Album,Artist " +
                "Where Album.Title = " + searchWord + ";";

    }
    public String albumByGenreSearchQuery(String searchWord){
        return "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                "From Album,Artist " +
                "Where Album.Genre = " + searchWord + ";";

    }
    public String albumByRaitingtSearchQuery(String searchWord){
        return "";

    }

    public String getArtistByName(String searchWord){
        return "SELECT ID FROM Artist WHERE Artist.name = '" + searchWord + "' ;";

    }
    public String getDirectorByName(String searchWord){
        return "SELECT ID FROM Director WHERE Director.name = '" + searchWord + "' ;";

    }

    public String insertDirector(String name){

        return "INSERT INTO Director(name) VALUES('" + name + "');";
    }

    public String insertArtist(String name){

        return "INSERT INTO Artist(name) VALUES('" + name + "');";
    }

    public String authenticateUser(String username, String password){
        return  "SELECT ID,Username FROM USER WHERE Username = '"+username+"' AND Password='"+password+"';";

    }

    public String checkForUser(String username){
        return "SELECT ID,Username FROM USER WHERE Username ='"+username+"';";
    }

    public String insertUser(String username, String password){
        return "INSERT INTO User(username,password) VALUES('" + username + "','" + password + "');";
    }

    public String editAlbum(int albumID,String title, String genre, int artistID, String coverURL){

        return  "UPDATE Alubum SET Title = '" + title + "', Genre = '" + genre + "', " +
                "ArtistID = '" + artistID +"', CoverURL = '" + coverURL + "' WHERE id = '" + artistID + "';";
    }
    public String editMovie(int movieID,String title, String genre, int directorID, String coverURL){

        return  "UPDATE Movie SET Title = '" + title + "', Genre = '" + genre + "', " +
                "DirectorID = '" + directorID +"', CoverURL = '" + coverURL + "' WHERE id = '" + directorID + "';";
    }

    public String addReview(int userID, int movieID, Date date, String text, int rating){
        return "INSERT INTO MovieReview(UserID,MovieID,RevDate,Review,Rating) VALUES(" + userID + "," + movieID +",'" + date
                + "','" + text + "'," + rating + ");";
    }

}
