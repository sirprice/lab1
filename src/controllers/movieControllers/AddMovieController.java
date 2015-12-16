package controllers.movieControllers;

import enums.MovieGenre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Model;
import models.Movie;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 11/12/15.
 */
public class AddMovieController implements Initializable {


    private Stage addStage, primaryStage;
    private Parent add;
    private Model model;
    @FXML private TextField addTitle;
    @FXML private TextField addMovieDirector;
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
        addStage = new Stage();
        addStage.setScene(new Scene(add, 460,260));
        addStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void createMovie(){
        addStage.show();
    }

    public void saveMovie(){

        Movie newMovie;
        /*
        if (addUrl.getText().isEmpty()){ //todo this makes an error when the add fields is empty, try catch
            newMovie = new Movie(addTitle.getText(), addMovieDirector.getText(),addGenre.getValue(), addRating.getValue());
            model.addMovie(newMovie);
            clearTextFields();

        }
        else if (!addUrl.getText().isEmpty()){
            newMovie = new Movie(addTitle.getText(), addMovieDirector.getText(),addGenre.getValue(), addRating.getValue(), addUrl.getText());
            model.addMovie(newMovie);
            clearTextFields();

        }
        */
        //addDirector.toString();
        //System.out.println(addTitle.toString() + "   ");

        addStage.close();
    }

    public void abortEdit(){
        addStage.close();
        clearTextFields();
    }

    private void clearTextFields(){
        addMovieDirector.clear();
        addTitle.clear();
        addUrl.clear();
        addGenre.setValue(null);
        addRating.setValue(null);
    }
}
