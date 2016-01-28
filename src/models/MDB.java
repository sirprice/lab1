package models;


import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by cj on 28/01/16.
 */
public class MDB implements Screwdriver {



    public MDB() {

    }



    @Override
    public ObservableList<Album> getAllAlbums() {

        String returnValue = null;
        ObservableList<Album> albums = FXCollections.observableArrayList();
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("Album");
        MongoCollection<Document> col1 = db.getCollection("Artist");

        Document doc = new Document();


        Block<Document> documentBlock = new Block<Document>() {
            @Override
            public void apply(Document document) {

                albums.add(new Album(document.getObjectId("_id").toString(),document.getString("Title")
                        ,document.getString("Name"),document.getString("Genre"),"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQN9NO4KaEAWBNSF6zHcmlcQmHUThEoTSQNbFK_lA35O8ak-5LvDg",
                        document.getString("userID")));
                System.out.println(document.toJson());
            }

        };

        col.find().forEach(documentBlock);



        /*if (document != null){
            returnValue = document.getObjectId("_id").toString();
            System.out.println(returnValue);
        }*/
        System.out.println("shshshshhshhshshs:"+returnValue);

        return null;
    }

    @Override
    public ObservableList<Album> getAlbumsBySearch(String searchWord, int item) {
        return null;
    }

    @Override
    public ObservableList<Movie> getAllMovies() {
        return null;
    }

    @Override
    public ObservableList<Movie> getMoviesBySearch(String searchWord, int item) {
        return null;
    }

    @Override
    public void insertAlbum(String title, String genre, String userID, String artistName) {

    }

    @Override
    public void insertAlbumOnly(String title, String genre, String userID, String artistID) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("Album");
        MongoCollection<Document> col2 = db.getCollection("Artist");

        ObjectId artistOBJID = new ObjectId(artistID);
        Document document1 = new Document("_id",artistOBJID);
        Document artist = col2.find(document1).first();

        if (document1 != null){

            System.out.println(artist.toJson());
            Document document = new Document("Title",title)
                    .append("Genre",genre)
                    .append("userID",userID)
                    .append("Artist",new Document("ArtistID",artistID).append("Name",artist.getString("Name")));


            if (document != null){
                col.insertOne(document);
            }
        }
        mongoClient.close();

    }

    @Override
    public void alterAlbum(String albumID, String title, String genre, String artistName, String coverURL) {


    }

    @Override
    public void alterAlbumOnly(String albumID, String title, String genre, String artistID, String coverURL) {

    }

    @Override
    public void dropAlbum(String albumID) {

    }

    @Override
    public String getArtistByName(String name) {

        String returnValue = null;
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("Artist");

        Document document = col.find(eq("Name",name)).first();
        System.out.println("försökte finna jesus med namn:  " + name);

        if (document != null){
            returnValue = document.getObjectId("_id").toString();
            System.out.println(returnValue);
        }
        System.out.println("shshshshhshhshshs:"+returnValue);

        return returnValue;
    }

    @Override
    public void insertMovie(String title, String genre, String userID, String directorName) {

    }

    @Override
    public void insertMovieOnly(String title, String genre, String userID, String directorID) {

    }

    @Override
    public void alterMovie(String movieID, String title, String genre, String coverURL, String directorName) {

    }

    @Override
    public void alterMovieOnly(String movieID, String title, String genre, String directorID, String coverURL) {

    }

    @Override
    public void dropMovie(String movieID) {

    }

    @Override
    public String getDirectorByName(String name) {
        return name;
    }

    @Override
    public User userAuthentication(String username, String password) {

        User user = null;
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("User");

        Document document = col.find(and(eq("name",username),eq("password",password))).first();

        if (document != null){
            user = new User(document.getObjectId("_id").toString(),document.getString("name"));
            System.out.println(document.getObjectId("_id").toString());
            System.out.println();
        }


           // Document document = col.find().first();
            //System.out.println(document.toJson());



        return user;
    }

    @Override
    public User getUser(String username) {

        User user = null;
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("User");

        Document document = col.find(eq("name",username)).first();

        if (document != null){
            user = new User(document.getObjectId("_id").toString(),document.getString("name"));
            System.out.println(document.getObjectId("_id").toString());
            System.out.println();
        }


        // Document document = col.find().first();
        //System.out.println(document.toJson());



        return user;
    }

    @Override
    public void insertNewUser(String username, String password) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("User");



        Document document = new Document("name",username)
                .append("password",password);

                //col.find(eq("name",username)).first();

        if (document != null){
            col.insertOne(document);
        }
        mongoClient.close();
    }

    @Override
    public void insertNewReview(String userID, String mediaID, Date date, String text, int rating, int mediaType) {

    }

    @Override
    public double getAvgRating(String id, int mediaType) {
        return 0;
    }

    @Override
    public Review checkIfReviewAlreadyExist(String usrID, String mediaID, int mediaType) {
        return null;
    }

    @Override
    public ArrayList<Review> getAlbumReviews(String albumID) {
        return null;
    }

    @Override
    public ArrayList<Review> getMovieReviews(String movieID) {
        return null;
    }

    @Override
    public void deleteReview(String usrID, String mediaID, int mediaType) {

    }

    @Override
    public void updateReview(String userID, String albumID, Date date, String text, int rating, int mediaType) {

    }

}
