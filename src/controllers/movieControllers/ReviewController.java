package controllers.movieControllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Model;
import models.Movie;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Scalman on 23/12/15.
 */
public class ReviewController implements Initializable{

    private Model model;
    private Stage primaryStage, reviewStage;
    private TableView<Movie> movieTable;
    private Parent review;
    private ToggleGroup group;
    private int rating;
    private Movie movie;
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
    public void reviewMovieStage(){
        reviewStage.show();
    }
    public void setMovieTable(Movie selectedMovie){
        this.movie = selectedMovie;
        title.setText(this.movie.getTitle());

        //System.out.println("picture : " + selectedMovie.getCoverUrl());
        Image img = new Image(selectedMovie.getCoverUrl());
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
        //System.out.println(rating);

        //System.out.println(date);
    }

    public void submitReview(){

        date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        if (textReview.getText().length() <= 0 && rating == 0){
            empty.setText("Please make some action");
            return;
        }

        if (textReview.getText().length() > 0 && rating > 0) {

            model.addMovieReview(model.getUser().getUserID(),movie.getMovieID(),sqlDate,textReview.getText(),rating);
            reviewStage.close();

        }else {
            empty.setText("Please make both options");
        }
    }

    public int getRating() {
        return rating;
    }
}