package models;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.javadoc.Doc;
import enums.AlbumGenre;
import enums.MovieGenre;
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
        ObservableList<Album> albums = FXCollections.observableArrayList();
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");


        Block<Document> documentBlock = new Block<Document>() {
            AlbumGenre tmpGenre = AlbumGenre.OTHER;
            @Override
            public void apply(Document document) {
                String userName = db.getCollection("User").find(new Document("_id", new ObjectId(document.getString("userID")))).first().getString("name");
                Document dec = (Document) document.get("Artist");

                albums.add(new Album(document.getObjectId("_id").toString(),document.getString("Title")
                        ,dec.getString("Name"),tmpGenre,
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQN9NO4KaEAWBNSF6zHcmlcQmHUThEoTSQNbFK_lA35O8ak-5LvDg",
                        userName));
                System.out.println(document.toJson());
            }

        };

        db.getCollection("Album").find().forEach(documentBlock);
        mongoClient.close();

        return albums;
    }

    @Override
    public ObservableList<Album> getAlbumsBySearch(String searchWord, int item) {
        return null;
    }
    @Override
    public ObservableList<Movie> getAllMovies() {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");

        Block<Document> documentBlock = new Block<Document>() {
            MovieGenre tmpGenre = MovieGenre.OTHER;
            @Override
            public void apply(Document document) {
                String userName = db.getCollection("User").find(new Document("_id", new ObjectId(document.getString("userID")))).first().getString("name");
                Document directorDoc = (Document) document.get("Director");

                movies.add(new Movie(document.getObjectId("_id").toString(),document.getString("Title"),directorDoc.getString("Director"),tmpGenre,
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQN9NO4KaEAWBNSF6zHcmlcQmHUThEoTSQNbFK_lA35O8ak-5LvDg", userName ));


            }
        };

        db.getCollection("Movie").find().forEach(documentBlock);
        mongoClient.close();
        System.out.println(movies.toString());

        return movies;
    }
    @Override
    public ObservableList<Movie> getMoviesBySearch(String searchWord, int item) {
        return null;
    }

    @Override
    public void insertAlbum(String title, String genre, String userID, String artistName) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> artistCollection = db.getCollection("Artist");
        MongoCollection<Document> dbCollection = db.getCollection("Album");
        List<Document> reviews = new ArrayList<>();


        Document artistObj = new Document("Artist",artistName);
        artistCollection.insertOne(artistObj);


        artistObj = artistCollection.find(eq("Artist",artistName)).first();
        String id = artistObj.getObjectId("_id").toString();
        //String id = getArtistByName(artistName);


        Document document = new Document("Title",title)
                .append("Genre",genre)
                .append("userID",userID)
                .append("Artist",new Document("ArtistID",id).append("Name",artistName))
                .append("Review",reviews);


        if (document != null){
            dbCollection.insertOne(document);
        }

        mongoClient.close();

    }

    @Override
    public void insertAlbumOnly(String title, String genre, String userID, String artistID) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> albumCollection = db.getCollection("Album");
        MongoCollection<Document> sbCollection = db.getCollection("Artist");
        List<Document> reviews = new ArrayList<>();

        ObjectId artistOBJID = new ObjectId(artistID);
        Document id = new Document("_id",artistOBJID);
        Document artist = sbCollection.find(id).first();



        System.out.println(artist.toJson());
        Document document = new Document("Title",title)
                .append("Genre",genre)
                .append("userID",userID)
                .append("Artist",new Document("ArtistID",artistID).append("Name",artist.getString("Name")))
                .append("Review",reviews);




        if (document != null){
            albumCollection.insertOne(document);
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
        System.out.println("Dropping album");
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        db.getCollection("Album").findOneAndDelete(new Document("_id", albumID));
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

        mongoClient.close();
        return returnValue;
    }

    @Override
    public void insertMovie(String title, String genre, String userID, String directorName) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        List<Document> reviews = new ArrayList<>();

        Document director = new Document("Director",directorName);
        db.getCollection("Director").insertOne(director);

        director = db.getCollection("Director").find(eq("Director",directorName)).first();
        String id = director.getObjectId("_id").toString();

        Document document = new Document("Title",title)
                .append("Genre",genre)
                .append("userID",userID)
                .append("Director",new Document("DirectorID",id).append("Name",directorName))
                .append("Review",reviews);


        if (document != null){
            db.getCollection("Movie").insertOne(document);
        }

        mongoClient.close();
    }

    @Override
    public void insertMovieOnly(String title, String genre, String userID, String directorID) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        List<Document> reviews = new ArrayList<>();

        Document director = db.getCollection("Director").find(new Document("_id",directorID)).first();
        String id = director.getObjectId("_id").toString();
        String directorName = director.getString("Name");

        Document document = new Document("Title",title)
                .append("Genre",genre)
                .append("userID",userID)
                .append("Director",new Document("DirectorID",id).append("Name",directorName))
                .append("Review",reviews);


        if (document != null){
            db.getCollection("Movie").insertOne(document);
        }

        mongoClient.close();
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
        String returnValue = null;
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> col = db.getCollection("Director");

        Document document = col.find(eq("Name",name)).first();
        System.out.println("försökte finna jesus med namn:  " + name);

        if (document != null){
            returnValue = document.getObjectId("_id").toString();
            System.out.println(returnValue);
        }

        mongoClient.close();
        return returnValue;
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
        MongoCollection<Document> userCollection = db.getCollection("User");



        Document document = new Document("name",username)
                .append("password",password);

        //col.find(eq("name",username)).first();

        if (document != null){
            userCollection.insertOne(document);
        }
        mongoClient.close();
    }

    @Override
    public void insertNewReview(String userID, String mediaID, Date date, String text, int rating, int mediaType) {
        String userName;
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");

        userName = db.getCollection("User").find(new Document("_id",new ObjectId(userID))).first().getString("name");

        if (mediaType == 1){

            Document tmp = new Document("Datum",date);
            tmp.append("userName",userName)
                    .append("userID", userID)
                    .append("text",text)
                    .append("Rating",rating);

            System.out.println("MediaID:    " + mediaID);
            db.getCollection("Album").updateOne(new Document("_id",new ObjectId(mediaID)),new Document("$push",
                    new Document("Review",tmp)));
        }
        if (mediaType == 2){

            Document tmp = new Document("Datum",date);
            tmp.append("userName",userName)
                    .append("userID", userID)
                    .append("text",text)
                    .append("Rating",rating);

            System.out.println("MediaID:    " + mediaID);
            db.getCollection("Movie").updateOne(new Document("_id",new ObjectId(mediaID)),new Document("$push",
                    new Document("Review",tmp)));
        }
    }



    @Override
    public double getAvgRating(String id, int mediaType) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        int sum = 0;
        double average = 0;
        if (mediaType == 1){
            Document album = db.getCollection("Album").find(new Document("_id",new ObjectId(id))).first();
            ArrayList<Document> revDocs = (ArrayList<Document>) album.get("Review");
            if (revDocs.size()>0){
                for (Document d: revDocs){
                    sum += d.getInteger("Rating");
                }
                average = (double) sum/revDocs.size();
            }
        }
        if (mediaType == 2){
            Document album = db.getCollection("Movie").find(new Document("_id",new ObjectId(id))).first();
            ArrayList<Document> revDocs = (ArrayList<Document>) album.get("Review");

            if (revDocs.size()>0){
                for (Document d: revDocs){
                    sum += d.getInteger("Rating");
                }
                average = (double) sum/revDocs.size();
            }
        }

        return average;
    }

    @Override
    public Review checkIfReviewAlreadyExist(String usrID, String mediaID, int mediaType) {
        System.out.println("Nu försöker vi hämta reviews");

        Review review = null;
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");

        if (mediaType == 1){
            Document album = db.getCollection("Album").find(new Document("_id",new ObjectId(mediaID))).first();
            ArrayList<Document> revDocs = (ArrayList<Document>) album.get("Review");
            for (Document d: revDocs){
                if (d.getString("userID").equals(usrID)){
                    review = new Review(d.getDate("Datum"),d.getInteger("Rating"), d.getString("Text"), d.getString("userName"));
                }
            }
        }
        if (mediaType == 2){
            Document album = db.getCollection("Movie").find(new Document("_id",new ObjectId(mediaID))).first();
            ArrayList<Document> revDocs = (ArrayList<Document>) album.get("Review");
            for (Document d: revDocs){
                if (d.getString("userID").equals(usrID)){
                    review = new Review(d.getDate("Datum"),d.getInteger("Rating"), d.getString("Text"), d.getString("userName"));
                }
            }
        }
        return review;
    }

    @Override
    public ArrayList<Review> getAlbumReviews(String albumID) {

        System.out.println("Nu försöker vi hämta reviews");

        ArrayList<Review> reviews = new ArrayList<>();
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> collAlbum = db.getCollection("Album");

        Document album = collAlbum.find(new Document("_id",new ObjectId(albumID))).first();
        System.out.println(album.toJson());

        ArrayList<Document> revDocs = (ArrayList<Document>) album.get("Review");
        for (Document d: revDocs){
            reviews.add(new Review(d.getDate("Datum"),d.getInteger("Rating"), d.getString("Text"), d.getString("userName")));
        }

        return reviews;
    }

    @Override
    public ArrayList<Review> getMovieReviews(String movieID) {
        System.out.println("Nu försöker vi hämta reviews");

        ArrayList<Review> reviews = new ArrayList<>();
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("mediaapp");
        MongoCollection<Document> collAlbum = db.getCollection("Movie");

        Document album = collAlbum.find(new Document("_id",new ObjectId(movieID))).first();
        System.out.println(album.toJson());

        ArrayList<Document> revDocs = (ArrayList<Document>) album.get("Review");
        for (Document d: revDocs){
            reviews.add(new Review(d.getDate("Datum"),d.getInteger("Rating"), d.getString("Text"), d.getString("userName")));
        }

        return reviews;
    }

    @Override
    public void deleteReview(String usrID, String mediaID, int mediaType) {

    }

    @Override
    public void updateReview(String userID, String albumID, Date date, String text, int rating, int mediaType) {

    }

}
