package models;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by cj on 14/12/15.
 */
public class JDBCDatabase implements Screwdriver {

    private String database;
    private String server;
    private String username;
    private String password;
    private Connection connection;


    public JDBCDatabase (String server){
        this.server = server;
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
}
