package models;

import enums.AlbumGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by cj on 14/12/15.
 */
public class JDBCDatabase implements Screwdriver {

    private String database;
    private String server;
    private String username;
    private String password;
    private Connection connection;
    private ObservableList<Album> albums;
    private ObservableList<Movie> movies;


    public JDBCDatabase (String server){
        this.server = server;

        movies = FXCollections.observableArrayList();
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
    public ObservableList<Album> getAlbums(String query) {

        ObservableList<Album> albums;
        albums = getAlbumsQuery(query);
        for (Album a : albums){
            System.out.println("2" + a.toString());
        }
        return albums;
    }


    @Override
    public void insertAlbum() {

    }

    @Override
    public void alterAlbum() {

    }

    @Override
    public void dropAlbum() {

    }

    @Override
    public void getArtist() {

    }

    @Override
    public void insertMovie() {

    }

    @Override
    public void alterMovie() {

    }

    @Override
    public void dropMovie() {

    }

    @Override
    public void getDirector() {

    }

    @Override
    public void userAuthentication() {

    }

    @Override
    public void getReviews() {

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
                System.out.println("1" + tmp.toString());
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
}
