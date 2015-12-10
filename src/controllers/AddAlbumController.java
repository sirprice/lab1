package controllers;

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
        addGenre.setItems(model.getAlbumGenreList());
        addRating.setItems(model.getRatingList());
    }
    public void setParent(Parent parent){
        this.add = parent;
        addStage = new Stage();
        addStage.setScene(new Scene(add, 460,260));
        addStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void addAlbum(){


        addStage.show();
    }


    public void createAlbum(){
        Album newAlbum = null;

        if (addUrl.getText().isEmpty()) {
            System.out.println("Halle");
            newAlbum = new Album(addTitle.getText(), addArtist.getText(), addGenre.getValue(), addRating.getValue());
            model.addAlbum(newAlbum);
        }
        else if(!addUrl.getText().isEmpty()) {
            System.out.println("Balle");
            newAlbum = new Album(addTitle.getText(),addArtist.getText(),addGenre.getValue(), addRating.getValue(), addUrl.getText());
            model.addAlbum(newAlbum);
        }
        //else if(addTitle.getText() == null || addArtist.getText() == null || ){

        //}
    }
}
