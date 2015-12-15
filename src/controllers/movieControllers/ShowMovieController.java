package controllers.movieControllers;

import enums.AlbumGenre;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;
import models.Movie;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Scalman on 15/12/15.
 */
public class ShowMovieController {

    private Stage showStage, primaryStage;
    private Parent show;
    private AlbumGenre genre;
    @FXML private Label titleLabel, directorLabel, genreLabel, raitingLabel;
    @FXML private ImageView movieCover;

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

    public void showMovie(Movie movie){

        titleLabel.setText(movie.getTitle());
        directorLabel.setText(movie.getDirector());
        genreLabel.setText(movie.getGenre().toString());
        String rating = "" + movie.getRating();
        raitingLabel.setText(rating);

        Image cover = new Image(movie.getCoverUrl());
        movieCover.setImage(cover);
        showStage.show();
    }
}
