package controllers;

import enums.AlbumGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 09/12/15.
 */
public class EditAlbumController implements Initializable{

    private Stage editStage, primaryStage;
    private Parent edit;
    private AlbumGenre genre;
    private Album selectedAlbum;
    @FXML private TextField editTitle, editArtist, editUrl;
    @FXML private ChoiceBox editGenre, editRating;
    @FXML private ImageView albumCover;
    @FXML private Button editSubmitt;

    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<AlbumGenre> editGenreList = FXCollections.observableArrayList();
        ObservableList<Integer> editRaitingList = FXCollections.observableArrayList(1,2,3,4,5);
        for (AlbumGenre ag: AlbumGenre.values()){
            editGenreList.add(ag);
        }

        editRating.setItems(editRaitingList);
        editGenre.setItems(editGenreList);

    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setParent(Parent parent){
        this.edit = parent;
        editStage = new Stage();
        editStage.setScene(new Scene(edit, 600, 500));
        editStage.initModality(Modality.APPLICATION_MODAL);
        //editStage.initOwner(primaryStage);
    }

    public void editAlbum(Album album, Event e){
        selectedAlbum = album;

        editTitle.setText(album.getTitle());
        editArtist.setText(album.getArtist());
        editGenre.setValue(album.getGenre());
        editRating.setValue(album.getRating());
        editUrl.setText(album.getCoverUrl());
        Image img = new Image(album.getCoverUrl());
        albumCover.setImage(img);


        editStage.show();

    }

    public void saveAlbum(){
        selectedAlbum.setArtist(editArtist.getText());
        selectedAlbum.setTitle(editTitle.getText());
        selectedAlbum.setGenre((AlbumGenre) editGenre.getValue());
        selectedAlbum.setRaiting((Integer) editRating.getValue());
        selectedAlbum.setCoverUrl(editUrl.getText());

    }
}