package models;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.javadoc.Doc;
import enums.AlbumGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

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
        MongoCollection<Document> col2 = db.getCollection("User");


        Block<Document> documentBlock = new Block<Document>() {
            AlbumGenre tmpGenre = AlbumGenre.OTHER;
            @Override
            public void apply(Document document) {

                //Document d1 = col1.find(eq("Name",document.getString("Name")));
                String genre = document.getString("Genre");

                for (AlbumGenre a: AlbumGenre.values()){
                    System.out.println(a.toString());
                    if (genre.equals(a.name())){
                        tmpGenre = a;
                        System.out.println(a.toString());
                        break;
                    }
                }

                Document dec = (Document) document.get("Artist");
                Iterable<Document> usr = col2.find(eq("_id",document.getString("userID")));

                String userName = null;
                for (Document d: usr){
                    userName = d.getString("name");
                }




                albums.add(new Album(document.getObjectId("_id").toString(),document.getString("Title")
                        ,dec.getString("Name"),tmpGenre,
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQN9NO4KaEAWBNSF6zHcmlcQmHUThEoTSQNbFK_lA35O8ak-5LvDg",
                        userName));
                System.out.println(document.toJson());
            }

        };


        col.find().forEach(documentBlock);
        mongoClient.close();
        return albums;
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



        //MongoCollection<Document> col = db.getCollection("Album");


        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("Artist");
        MongoCollection<Document> col1 = db.getCollection("Album");
        List<DBObject> reviews = new ArrayList<DBObject>();

        Document document1 = new Document("Artist",artistName);
        if (document1 != null){
            col.insertOne(document1);
        }

        String id = getArtistByName(artistName);

        Document document = new Document("Title",title)
        .append("Genre",genre)
        .append("userID",userID)
                .append("Artist",new Document("ArtistID",id).append("Name",artistName))
                .append("Review",reviews);

        if (document != null){
            col1.insertOne(document);
        }

        mongoClient.close();

    }

    @Override
    public void insertAlbumOnly(String title, String genre, String userID, String artistID) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("Album");
        MongoCollection<Document> col2 = db.getCollection("Artist");
        List<DBObject> reviews = new ArrayList<DBObject>();


        ObjectId artistOBJID = new ObjectId(artistID);
        Document document1 = new Document("_id",artistOBJID);
        Document artist = col2.find(document1).first();

        if (document1 != null){

            System.out.println(artist.toJson());
            Document document = new Document("Title",title)
                    .append("Genre",genre)
                    .append("userID",userID)
                    .append("Artist",new Document("ArtistID",artistID).append("Name",artist.getString("Name")))
                    .append("Review",reviews);

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
        mongoClient.close();
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


        mongoClient.close();
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


        mongoClient.close();
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
        System.out.println();
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> collUser = db.getCollection("User");
        MongoCollection<Document> collAlbum = db.getCollection("Album");
        MongoCollection<Document> collMovie = db.getCollection("Movie");


        List<Document> reviews = new ArrayList<>();
        Document user = (Document) collUser.find(eq("_id",userID));
        String userName = user.getString("Name");
        System.out.println("Nu plockade vi ut username: "+userName+"Från ett media");


        if (mediaType == 1){
            /*
            Document document = collAlbum.find(eq("_id",mediaID)).first();
            reviews = (List<Document>) document.get("Review");
            reviews.add(new Document("User",userName).append("Rating",rating).append("ReviewText",text).append("Date",date));
            */
        }

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
