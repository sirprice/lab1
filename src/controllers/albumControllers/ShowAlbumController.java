package controllers.albumControllers;

import enums.AlbumGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 10/12/15.
 */
public class ShowAlbumController implements Initializable {

    private Stage showStage, primaryStage;
    private Parent show;
    private AlbumGenre genre;
    @FXML private Label titleLabel, artistLabel, genreLabel, raitingLabel;
    @FXML private ImageView albumCover;

    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;

    }

    public void setParent(Parent parent){
        this.show = parent;
        showStage = new Stage();
        showStage.setScene(new Scene(show, 460, 260));
        showStage.initModality(Modality.APPLICATION_MODAL);
        //editStage.initOwner(primaryStage);
    }

    public void showAlbum(Album album){

        titleLabel.setText(album.getTitle());
        artistLabel.setText(album.getArtist());
        genreLabel.setText(album.getGenre().toString());
        String rating = "" + album.getRating();
        raitingLabel.setText(rating);


        Image cover = new Image(album.getCoverUrl());
        albumCover.setImage(cover);

        showStage.show();
    }
}
