package controllers.albumControllers;

import enums.AlbumGenre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;
import models.Model;
import models.Movie;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 09/12/15.
 */
public class AddAlbumController implements Initializable {

    private Stage addStage, primaryStage;
    private Parent add;
    private Model model;
    @FXML private TextField addTitle;
    @FXML private TextField addArtist;
    @FXML private ChoiceBox<AlbumGenre> addGenre;
    @FXML private ChoiceBox<Integer> addRating;
    @FXML private TextField addUrl;

    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setModel(Model model){
        this.model = model;
    }
    public void setChoiceBoxes(){
        addRating.setItems(model.getRatingList());
        addGenre.setItems(model.getAlbumGenreList());

    }
    public void setParent(Parent parent){
        this.add = parent;
        addStage = new Stage();
        addStage.setScene(new Scene(add, 460,260));
        addStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void createAlbum(){
        addStage.show();
    }

    public void saveAlbum(){

        Album newAlbum;
        /*
        if (addUrl.getText().isEmpty()){
            System.out.println("Halle");
            newAlbum = new Album(addTitle.getText(), addArtist.getText(), addGenre.getValue(), addRating.getValue());
            model.addAlbum(newAlbum);
        }
        else if(!addUrl.getText().isEmpty()) {
            System.out.println("Balle");
            newAlbum = new Album(addTitle.getText(),addArtist.getText(),addGenre.getValue(), addRating.getValue(), addUrl.getText());
            model.addAlbum(newAlbum);
        }
        */
        addStage.close();
        clearTextFields();

    }

    public void abortEdit(){
        addStage.close();
        clearTextFields();
    }

    private void clearTextFields(){
        addArtist.clear();
        addTitle.clear();
        addUrl.clear();
        addGenre.setValue(null);
        addRating.setValue(null);
    }

}
