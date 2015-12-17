package models;

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
        String query ="INSERT INTO ALBUM (Title,Genre,artistID) " +
                "VALUES('"+ title +"', '"+ genre + "',"+ artistID +");";
        return query;
    }

    public String dropAlbumQuery(int index){
        String query = "DELETE FROM ALBUM WHERE ALBUM.ID = " + index + ";";
        return query;
    }

    public String dropMovieQuery(int index){
        String query = "DELETE FROM MOVIE WHERE MOVIE.ID = " + index + ";";
        return query;
    }

    public String albumByArtistSearchQuery(String searchWord){
        String query = "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                "From Album,Artist " +
                "Where Artist.Name = " + searchWord + ";";
        return query;
    }

    public String albumByTitleSearchQuery(String searchWord){
        String query = "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                "From Album,Artist " +
                "Where Album.Title = " + searchWord + ";";
        return query;
    }
    public String albumByGenreSearchQuery(String searchWord){
        String query = "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                "From Album,Artist " +
                "Where Album.Genre = " + searchWord + ";";
        return query;
    }
    public String albumByRaitingtSearchQuery(String searchWord){
        String query ="";
        return query;
    }

    public String getArtistByName(String searchWord){
        String query ="SELECT ID FROM Artist WHERE Artist.name = '" + searchWord + "' ;";
        return query;
    }

    public String insertArtist(String name){

        return "INSERT INTO Artist(name) VALUES('" + name + "');";
    }


}
