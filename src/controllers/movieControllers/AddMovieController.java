package controllers.movieControllers;

import enums.MovieGenre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Model;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 11/12/15.
 */
public class AddMovieController implements Initializable {


    private Stage addStage, primaryStage;
    private Parent add;
    private Model model;
    @FXML
    private TextField addTitle;
    @FXML private TextField addDirector;
    @FXML private ChoiceBox<MovieGenre> addGenre;
    @FXML private ChoiceBox<Integer> addRating;
    @FXML private TextField addUrl;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void setChoiceBoxes(){
        addRating.setItems(model.getRatingList());
        addGenre.setItems(model.getMovieGenreList());
    }
    public void setModel(Model model){
        this.model = model;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setParent(Parent add) {
        this.add = add;
    }

    public void createMovie(){

    }

    public void saveMovie(){

    }

}
