package controllers.albumControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;
import models.Model;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Scalman on 01/01/16.
 */
public class ReviewAlbumController implements Initializable{

    private Model model;
    private Stage primaryStage, reviewStage;
    private Parent review;
    private ToggleGroup group;
    private int rating;
    private Album album;
    private Date date;
    private String text;

    @FXML private TextArea textReview;
    @FXML private RadioButton one,two,three,four,five;
    @FXML private Label title;
    @FXML private ImageView imgView;
    @FXML private Label empty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = new ToggleGroup();
        one.setToggleGroup(group);
        two.setToggleGroup(group);
        three.setToggleGroup(group);
        four.setToggleGroup(group);
        five.setToggleGroup(group);
    }

    public void setModel(Model model){
        this.model = model;
    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void reviewAlbumStage(){
        reviewStage.show();
    }

    public void setAlbumTable(Album selectedAlbum){
        this.album = selectedAlbum;
        title.setText(this.album.getTitle());

        //System.out.println("picture : " + selectedMovie.getCoverUrl());
        Image img = new Image(selectedAlbum.getCoverUrl());
        imgView.setImage(img);
    }

    public void setParent(Parent parent){
        this.review = parent;
        reviewStage = new Stage();
        reviewStage.setScene(new Scene(review, 600, 400));
        reviewStage.initModality(Modality.APPLICATION_MODAL);
        //editStage.initOwner(primaryStage);
    }

    public void radioButton(){
        rating = 0;

        if(one.isArmed()){
            rating = Integer.parseInt(one.getText());
        }
        if(two.isArmed()){
            rating = Integer.parseInt(two.getText());
        }
        if(three.isArmed()){
            rating = Integer.parseInt(three.getText());
        }
        if(four.isArmed()){
            rating = Integer.parseInt(four.getText());
        }
        if(five.isArmed()){
            rating = Integer.parseInt(five.getText());
        }
    }

    public void submitReview(){

        date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        if (textReview.getText().length() <= 0 && rating == 0){
            empty.setText("Please make some action");
            return;
        }
        if (textReview.getText().length() > 0 && rating > 0) {

            model.addAlbumReview(model.getUser().getUserID(),album.getAlbumID(),sqlDate,textReview.getText(),rating);
            reviewStage.close();
        }else {
            empty.setText("Please make both options");
        }
    }

    public int getRating() {
        return rating;
    }

}
