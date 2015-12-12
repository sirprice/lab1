package main;

import controllers.*;
import controllers.albumControllers.AddAlbumController;
import controllers.albumControllers.EditAlbumController;
import controllers.albumControllers.ShowAlbumController;
import controllers.movieControllers.AddMovieController;
import controllers.movieControllers.EditMovieController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Model;

import java.io.IOException;

/**
 * Created by Scalman on 10/12/15.
 */
public class InitFxml {

        public void initializLoadersAndControllers(Stage primaryStage) throws IOException {

            Model model = new Model();
            DeleteController dCtrl = new DeleteController();
            dCtrl.setModel(model);


            //- - - - - - - - - - - MOVIES - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            Parent addMovie = null;
            FXMLLoader addMovieLoader = new FXMLLoader();
            addMovie = addMovieLoader.load(getClass().getResource("/fxml/movieFxml/addMovie.fxml").openStream());
            AddMovieController aMCtrl = addMovieLoader.getController();
            if (aMCtrl == null) System.out.println("add movie controllern är null");
            aMCtrl.setParent(addMovie);
            aMCtrl.setModel(model);
            aMCtrl.setChoiceBoxes();

            Parent editMovie = null;
            FXMLLoader editMovieLoader = new FXMLLoader();
            editMovie = editMovieLoader.load(getClass().getResource("/fxml/movieFxml/editMovie.fxml").openStream());
            EditMovieController eMCtrl = editMovieLoader.getController();
            if (eMCtrl == null) System.out.println("edit movie controllern är null");
            eMCtrl.setParent(editMovie);
            eMCtrl.setModel(model);
            eMCtrl.setChoiceBoxes();


            //- - - - - - - - - - - ALBUMS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            Parent editAlbum = null;
            FXMLLoader editAlbumLoader = new FXMLLoader();
            editAlbum = editAlbumLoader.load(getClass().getResource("/fxml/albumFxml/editRecord.fxml").openStream());
            EditAlbumController eACtrl = editAlbumLoader.getController();
            if (eACtrl == null) System.out.println("edit album controllern är null");
            eACtrl.setParent(editAlbum);
            eACtrl.setModel(model);
            eACtrl.setChoiceBoxes();

            Parent showAlbum = null;
            FXMLLoader showAlbumLoader = new FXMLLoader();
            showAlbum = showAlbumLoader.load(getClass().getResource("/fxml/albumFxml/showAlbum.fxml").openStream());
            ShowAlbumController sACtrl = showAlbumLoader.getController();
            if (sACtrl == null) System.out.println("edit album controllern är null");
            sACtrl.setParent(showAlbum);


            Parent addAlbum = null;
            FXMLLoader addAlbumLoader = new FXMLLoader();
            addAlbum = addAlbumLoader.load(getClass().getResource("/fxml/albumFxml/addAlbum.fxml").openStream());
            AddAlbumController aACtrl = addAlbumLoader.getController();
            if (aACtrl == null) System.out.println("add album controllern är null");
            aACtrl.setParent(addAlbum);
            aACtrl.setModel(model);
            aACtrl.setChoiceBoxes();


            //- - - - - - - - - - - -Main Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            Parent root = null;
            FXMLLoader loader = new FXMLLoader();
            System.out.println(getClass().getResource("/fxml/main.fxml"));
            root = loader.load(getClass().getResource("/fxml/main.fxml").openStream());
            MainController mCtrl = loader.getController();
            if (mCtrl == null) System.out.println("main controllern är null");
            mCtrl.setPrimaryStage(primaryStage);
            mCtrl.setModel(model);
            mCtrl.setControllers(aACtrl, eACtrl, dCtrl, sACtrl, aMCtrl,eMCtrl);
            mCtrl.showAlbums();
            mCtrl.showMovies();

            //- - - - - - - - - - - -Login Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

            primaryStage.setTitle("Media Center");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        }
}


