package models;

import enums.AlbumGenre;
import enums.MovieGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.nashorn.internal.runtime.RewriteException;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by cj on 14/12/15.
 */
public class JDBCDatabase implements Screwdriver {

    private String database;
    private String server;
    private String username;
    private String password;
    private Connection connection;
    private User user;
    private ObservableList<Album> albums;
    private ObservableList<Movie> movies;


    public JDBCDatabase (String server){
        this.server = server;
        //movies = FXCollections.observableArrayList();
    }

    public void connect(String username, String password){
        this.username = username;
        this.password = password;


        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(server, username, password);
            System.out.println("Connected!");
        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }
    }

    @Override
    public void insertNewArtist(String query) {
        executeUpdate(query);
    }

    @Override
    public void insertNewReview(String query) {
        executeUpdate(query);
    }

    @Override
    public Review checkIfReviewAlreadyExist(String query) {
        Statement stmt = null;
        Review review = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                review = new Review(rs.getDate("RevDate"), rs.getInt("Rating"), rs.getString("Review"));
                System.out.println(review);
            }

        }catch (java.sql.SQLException sql){
            System.out.println(sql.getMessage());
        }
        return review;
    }

    @Override
    public int getArtistByName(String query) {
        Statement stmt = null;
        int artistId = -1;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                artistId = rs.getInt("ID");
                System.out.println(artistId);
            }
            //return artistId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artistId;
    }

    @Override
    public ObservableList<Album> getAlbums(String query) {

        ObservableList<Album> albums;
        albums = getAlbumsQuery(query);

        return albums;
    }

    @Override
    public ObservableList<Movie> getMovies(String query) {
        ObservableList<Movie> movies;
        movies = getMoviesQuery(query);

        return movies;
    }

    @Override
    public void insertAlbum(String query) {
        executeUpdate(query);
    }

    @Override
    public void insertNewUser(String query) {
        executeUpdate(query);
    }

    @Override
    public void alterAlbum(String query) {
        executeUpdate(query);
    }

    @Override
    public void dropAlbum(String query) {
        executeUpdate(query);
    }

    @Override
    public void insertMovie(String query) {
        executeUpdate(query);
    }

    @Override
    public void alterMovie(String query) { executeUpdate(query);}
    @Override
    public void dropMovie(String query) {
        executeUpdate(query);
    }

    @Override
    public int getDirectorByName(String query) {
        Statement stmt = null;
        int id = -1;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("ID");
                System.out.println(id);
            }
            //return artistId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void insertNewDirector(String query) {
        executeUpdate(query);
    }

    @Override
    public User userAuthentication(String query) {
        return getUserQuery(query);
    }

    @Override
    public ArrayList<Review> getReviews(String query) {
        Statement stmt = null;
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            // Execute the SQL statement
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Review tmp = new Review(rs.getDate("RevDate"),rs.getInt("Rating"),rs.getString("Review"), rs.getInt("UserID"),rs.getString("Username") , rs.getInt("AlbumID"));
                System.out.println(" 1" + tmp.toString());
                reviews.add(tmp);

            }
            //return reviews;

        }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        finally {
            if (stmt != null) {
                try{
                    stmt.close();
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }

        return reviews;
    }

    public void executeUpdate(String query){

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);

        }catch (java.sql.SQLException sql){
            System.out.println(sql.getMessage());
        }

    }

    private ObservableList<Album> getAlbumsQuery(String query) {
        Statement stmt = null;
        ObservableList<Album> albums = FXCollections.observableArrayList();
        AlbumGenre tmpGenre = AlbumGenre.OTHER;
        try {
            // Execute the SQL statement
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                for (AlbumGenre ag : AlbumGenre.values()){
                    if (ag.toString().toUpperCase().equals(rs.getString("Genre").toUpperCase())){
                        tmpGenre = ag;
                    }
                }
                Album tmp = new Album(rs.getInt("ID"), rs.getString("Title"),rs.getString("Name"),tmpGenre,rs.getString("CoverUrl"));
                System.out.println(" 1" + tmp.toString());
                albums.add(tmp);

            }
            return albums;

        }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        finally {
            if (stmt != null) {
                try{
                    stmt.close();
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }
        return albums;
    }

    private ObservableList<Movie> getMoviesQuery(String query) {
        Statement stmt = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        MovieGenre tmpGenre = MovieGenre.OTHER;
        try {
            // Execute the SQL statement
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                for (MovieGenre ag : MovieGenre.values()){
                    if (ag.toString().toUpperCase().equals(rs.getString("Genre").toUpperCase())){
                        tmpGenre = ag;
                    }
                }
                Movie tmp = new Movie(rs.getInt("ID"), rs.getString("Title"),rs.getString("Name"),tmpGenre,rs.getString("CoverUrl"));
                System.out.println("1 " + tmp.toString());
                movies.add(tmp);

            }
            return movies;

        }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        finally {
            if (stmt != null) {
                try{
                    stmt.close();
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }
        return movies;
    }

    private User getUserQuery(String query) {
        Statement stmt = null;
        User user = null;

        try {
            // Execute the SQL statement
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("Username"));
                System.out.println("1 " + user.toString());
            }
            return user;

        }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        finally {
            if (stmt != null) {
                try{
                    stmt.close();
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }
        return user;
    }
}
