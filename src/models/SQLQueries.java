package models;

/**
 * Created by cj on 16/12/15.
 */
public class SQLQueries {

    public final String getAllAlbums = "Select Album.ID, Album.Title, Artist.Name, Album.Genre, Album.CoverUrl " +
                                        "From Album,Artist " +
                                        "Where Album.ArtistID = Artist.ID;";

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
}
