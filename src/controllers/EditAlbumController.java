package controllers;

import enums.AlbumGenre;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;
import models.Model;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by cj on 09/12/15.
 */
public class EditAlbumController implements Initializable{

    private Stage editStage, primaryStage;
    private Parent edit;
    private AlbumGenre genre;
    private Album selectedAlbum;
    private Model model;
    private int index;
    private TableView<Album> albumTable;
    @FXML private TextField editTitle, editArtist, editUrl;
    @FXML private ChoiceBox editGenre, editRating;
    @FXML private ImageView albumCover;
    @FXML private Button editSubmit;

    public void initialize(URL location, ResourceBundle resources) {


    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void setChoiceBoxes(){
        editRating.setItems(model.getRatingList());
        editGenre.setItems(model.getAlbumGenreList());
    }

    public void setParent(Parent parent){
        this.edit = parent;
        editStage = new Stage();
        editStage.setScene(new Scene(edit, 460, 260));
        editStage.initModality(Modality.APPLICATION_MODAL);
        //editStage.initOwner(primaryStage);
    }

    public void editAlbumItems(int index){              //shows album text and window

        this.index = index;

        editTitle.setText(model.getAlbum(index).getTitle());
        editArtist.setText(model.getAlbum(index).getArtist());
        editGenre.setValue(model.getAlbum(index).getGenre());
        editRating.setValue(model.getAlbum(index).getRating());
        editUrl.setText(model.getAlbum(index).getCoverUrl());

        Image img = new Image(model.getAlbum(index).getCoverUrl());
        albumCover.setImage(img);

        editStage.show();


    }

    public void saveAlbum(){

        model.getAlbum(index).setArtist(editArtist.getText());
        model.getAlbum(index).setTitle(editTitle.getText());
        model.getAlbum(index).setGenre((AlbumGenre) editGenre.getValue());
        model.getAlbum(index).setRating((Integer) editRating.getValue());
        model.getAlbum(index).setCoverUrl(editUrl.getText());


        editStage.close();

    }
}