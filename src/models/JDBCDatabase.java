package models;

import enums.AlbumGenre;
import enums.MovieGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.*;
import java.util.Date;


/**
 * Created by cj on 14/12/15.
 */
public class JDBCDatabase implements Screwdriver {

    private String database;
    private String server;
    //private Connection connection;
    private User user;
    private ObservableList<Album> albums;
    private ObservableList<Movie> movies;
    private String username = "mediaapp";
    private String password = "password";
    private SQLQueries queries;


    public JDBCDatabase (){
        this.server = "jdbc:mysql://localhost:3306/media?UseClientEnc=UTF8";
        queries = new SQLQueries();
    }

    @Override
    public void insertNewArtist(String name) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.insertArtist());
            stmt.setString(1,name);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public void insertNewReview(int userID, int mediaID, java.sql.Date date, String text, int rating, int mediaType) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();

            stmt = mediaType == 1 ? connection.prepareStatement(queries.addReviewAlbum()): connection.prepareStatement(queries.addReviewMovie());

            stmt.setInt(1,userID);
            stmt.setInt(2,mediaID);
            stmt.setDate(3,date);
            stmt.setString(4,text);
            stmt.setInt(5,rating);

            stmt.executeUpdate();
        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public Review checkIfReviewAlreadyExist(int usrID, int mediaID, int mediaType) {
        PreparedStatement stmt = null;
        Connection connection = null;
        Review review = null;
        try {
            connection = setupTheDatabaseConnectionSomehow();
            if (mediaType == 1){
                stmt = connection.prepareStatement(queries.albumAlreadyReviewed());
                stmt.setInt(1,usrID);
                stmt.setInt(2,mediaID);
            }


            if (mediaType == 2){
                stmt = connection.prepareStatement(queries.movieAlreadyReviewed());
                stmt.setInt(1,usrID);
                stmt.setInt(2,mediaID);
            }

            ResultSet rs = stmt.executeQuery();
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
    public int getArtistByName(String name) {
        PreparedStatement stmt = null;
        int id = -1;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.getArtistByName());
            stmt.setString(1,name);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ID");
                System.out.println(id);
            }
            //return artistId;
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                stmt.close();
                connection.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
        }
        return id;
    }

    @Override
    public double getAvgRating(int id, int mediaType) {

        double avg = 0;
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();


            stmt = mediaType == 1 ? connection.prepareStatement(queries.getAlbumRating()) : connection.prepareStatement(queries.getMovieRating());

            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                avg = rs.getDouble("avg(Rating)");
                System.out.println(avg);
            }

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {

            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }

        return avg;
    }

    @Override
    public ObservableList<Album> getAllAlbums() {
        ObservableList<Album> albums = null;
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.getAllAlbums);
            albums = getAlbumsQuery(stmt);

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
        return albums;
    }

    @Override
    public ObservableList<Album> getAlbumsBySearch(String searchWord,int item) {
        ObservableList<Album> albums = null;
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.searchAlbums(item));
            stmt.setString(1,"%" + searchWord + "%");
            albums = getAlbumsQuery(stmt);

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
        return albums;
    }

    @Override
    public ObservableList<Movie> getAllMovies() {
        ObservableList<Movie> movies = null;
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.getAllMovies);

            movies = getMoviesQuery(stmt);

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
        return movies;
    }

    @Override
    public ObservableList<Movie> getMoviesBySearch(String searchWord, int item) {
        ObservableList<Movie> movies = null;
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.searchMovies(item));

            System.out.println(item);
            if (item == 1 || item == 2){
                stmt.setString(1,"%" + searchWord + "%");
            }
            if (item == 3){
                double rating = Double.parseDouble(searchWord);
                int maxRating = (int) rating + 1;
                stmt.setDouble(1,rating);
                stmt.setInt(2,maxRating);
            }

            movies = getMoviesQuery(stmt);

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }

        return movies;
    }

    @Override
    public void insertAlbum(String title, String genre, int artistID, int userID) {

        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.insertAlbumQuery());
            stmt.setString(1,title);
            stmt.setString(2,genre);
            stmt.setInt(3,artistID);
            stmt.setInt(4,userID);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }

    }

    @Override
    public void insertNewUser(String username, String password) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.insertUser());
            stmt.setString(1,username);
            stmt.setString(2,password);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public void alterAlbum(int albumID,String title, String genre, int artistID, String coverURL) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.editAlbum());
            stmt.setString(1,title);
            stmt.setString(2,genre);
            stmt.setInt(3,artistID);
            stmt.setString(4,coverURL);
            stmt.setInt(5,albumID);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public void dropAlbum(int albumID) {

        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.dropAlbumQuery());
            stmt.setInt(1,albumID);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public void insertMovie(String title, String genre, int directorID, int userID) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.insertMovieQuery());
            stmt.setString(1,title);
            stmt.setString(2,genre);
            stmt.setInt(3,directorID);
            stmt.setInt(4,userID);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public void alterMovie(int movieID,String title, String genre, int directorID, String coverURL) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.editMovie());
            stmt.setString(1,title);
            stmt.setString(2,genre);
            stmt.setInt(3,directorID);
            stmt.setString(4,coverURL);
            stmt.setInt(5,movieID);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }
    @Override
    public void dropMovie(int movieID) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.dropMovieQuery());
            stmt.setInt(1,movieID);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public int getDirectorByName(String name) {
        PreparedStatement stmt = null;
        int id = -1;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.getDirectorByName());
            stmt.setString(1,name);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ID");
                System.out.println(id);
            }
            //return artistId;
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                stmt.close();
                connection.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
        }
        return id;
    }

    @Override
    public void insertNewDirector(String name) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.insertDirector());
            stmt.setString(1,name);
            stmt.executeUpdate();

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public User getUser(String username) {
        PreparedStatement stmt = null;
        User user = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.getUser());
            stmt.setString(1,username);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("Username"));
                System.out.println("1 " + user.toString());
            }

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
        return user;
    }

    @Override
    public User userAuthentication(String username,String password) {
        PreparedStatement stmt = null;
        User user = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.authenticateUser());
            stmt.setString(1,username);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("Username"));
                System.out.println("1 " + user.toString());
            }

        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
        return user;
    }

    @Override
    public void deleteReview(int userID, int mediaID, int mediaType) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = mediaType == 1 ? connection.prepareStatement(queries.deleteAlbumReview()) : connection.prepareStatement(queries.deleteMovieReview());
            stmt.setInt(1,userID);
            stmt.setInt(2,mediaID);

            stmt.executeUpdate();
        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public void updateReview(int userID, int albumID, java.sql.Date date, String text, int rating, int mediaType) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();

            stmt = mediaType == 1 ? connection.prepareStatement(queries.updateAlbumReview()) : connection.prepareStatement(queries.updateMovieReview());

            stmt.setDate(1,date);
            stmt.setString(2,text);
            stmt.setInt(3,rating);
            stmt.setInt(4,userID);
            stmt.setInt(5,albumID);

            stmt.executeUpdate();
        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {
            try{
                connection.close();
                stmt.close();
                System.out.println("Connection closed");
            }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }

        }
    }

    @Override
    public ArrayList<Review> getAlbumReviews(int albumID) {
        PreparedStatement stmt = null;
        ArrayList<Review> reviews = new ArrayList<>();
        Connection connection = null;

        try {
            connection = setupTheDatabaseConnectionSomehow();
            stmt = connection.prepareStatement(queries.getAlbumReviews());
            stmt.setInt(1,albumID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Review tmp = new Review(rs.getDate("RevDate"),rs.getInt("Rating"),rs.getString("Review"), rs.getInt("UserID"),rs.getString("Username"));
                reviews.add(tmp);
            }

        }catch (Exception e){ javax.swing.JOptionPane.showMessageDialog(null,
                "Database error, " + e.toString()); }

        finally {
            if (stmt != null) {
                try{
                    stmt.close();
                    connection.close();
                    System.out.println("Connection closed");
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }

        return reviews;
    }


    @Override
    public ArrayList<Review> getMovieReviews(int movieID) {
        PreparedStatement stmt = null;
        ArrayList<Review> reviews = new ArrayList<>();
        Connection connection = null;

        try {

            connection = setupTheDatabaseConnectionSomehow();
            // Execute the SQL statement
            stmt = connection.prepareStatement(queries.getMovieReviews());
            stmt.setInt(1,movieID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Review tmp = new Review(rs.getDate("RevDate"),rs.getInt("Rating"),rs.getString("Review"), rs.getInt("UserID"),rs.getString("Username"));
                System.out.println(" 1" + tmp.toString());
                reviews.add(tmp);

            }

        }catch (Exception e){ javax.swing.JOptionPane.showMessageDialog(null,
                "Database error, " + e.toString()); }

        finally {
            if (stmt != null) {
                try{
                    stmt.close();
                    connection.close();
                    System.out.println("Connection closed");
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }

        return reviews;
    }


    private ObservableList<Album> getAlbumsQuery(PreparedStatement stmt) {
        ObservableList<Album> albums = FXCollections.observableArrayList();
        AlbumGenre tmpGenre = AlbumGenre.OTHER;
        Connection connection = null;

        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                for (AlbumGenre ag : AlbumGenre.values()){
                    if (ag.toString().toUpperCase().equals(rs.getString("Genre").toUpperCase())){
                        tmpGenre = ag;
                    }
                }
                Album tmp = new Album(rs.getInt("ID"), rs.getString("Title"),rs.getString("Name"),tmpGenre,rs.getString("CoverUrl"), rs.getString("Username"));
                System.out.println(" 1" + tmp.toString());
                albums.add(tmp);

            }
        }
        catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database error, " + e.toString());
        }finally {

            if (stmt != null) {
                try{
                    stmt.close();
                }catch (java.sql.SQLException sqlE){ System.out.println(sqlE.getMessage()); }
            }
        }

        return albums;
    }

    private ObservableList<Movie> getMoviesQuery(PreparedStatement stmt) {

        ObservableList<Movie> movies = FXCollections.observableArrayList();
        MovieGenre tmpGenre = MovieGenre.OTHER;


        try {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                for (MovieGenre ag : MovieGenre.values()){
                    if (ag.toString().toUpperCase().equals(rs.getString("Genre").toUpperCase())){
                        tmpGenre = ag;
                    }
                }
                Movie tmp = new Movie(rs.getInt("ID"), rs.getString("Title"),rs.getString("Name"),tmpGenre,rs.getString("CoverUrl"), rs.getString("Username"));
                System.out.println("1 " + tmp.toString());
                movies.add(tmp);

            }
        }catch(Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Database error, " + e.toString());
        }finally {

            try {
                if (stmt != null){
                    stmt.close();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }

        return movies;
    }



    private Connection setupTheDatabaseConnectionSomehow(){

        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(server, username, password);
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
