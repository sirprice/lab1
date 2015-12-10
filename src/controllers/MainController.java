package controllers;

import enums.AlbumGenre;
import javafx.scene.Parent;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Album;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private AddAlbumController addAlbumController;
    private DeleteController deleteController;
    private EditAlbumController editAlbumController;
    private ShowAlbumController showAlbumController;

    private Parent album, main, edit;
    private Stage primaryStage, editStage;
    private ObservableList <Album> albums = FXCollections.observableArrayList();
    @FXML private Button mainToAlbum, showAlbums, albumDelete;
    @FXML private Button albumToMain;
    @FXML private TableView <Album> albumTable;
    @FXML private TableColumn<Album, String> Title,artist,genre, raiting;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setPrimaryStage(Stage primarystage){
        this.primaryStage = primarystage;
    }

    public void setControllers(AddAlbumController addAlbumController, EditAlbumController editAlbumController, DeleteController deleteController, ShowAlbumController showAlbumController ) {
        this.addAlbumController = addAlbumController;
        this.editAlbumController = editAlbumController;
        editAlbumController.setPrimaryStage(primaryStage);
        this.showAlbumController = showAlbumController;
        showAlbumController.setPrimaryStage(primaryStage);
        this.deleteController = deleteController;
    }
    public void toMenu(){
        primaryStage = (Stage) albumToMain.getScene().getWindow();
        try {
            FXMLLoader mainLoader = new FXMLLoader();
            main = mainLoader.load(getClass().getResource("/fxml/main.fxml"));
        }catch (IOException e){ }

        primaryStage.setScene(new Scene(main, Main.WIDTH, Main.HEIGHT));
    }




    // - - - - - - - - - Albums - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void showAlbums(){
            albumTable.setItems(getAlbums());
    }

    public void showSelectedAlbum(MouseEvent me){
        Album selected = albumTable.getSelectionModel().getSelectedItem();
        if (me.getClickCount() == 2 && !(selected == null)) {
            showAlbumController.showAlbum(selected);
        }
    }

    public void editAlbum(ActionEvent e) {
        Album selected = albumTable.getSelectionModel().getSelectedItem();
        if (!(selected == null)) {
            editAlbumController.editAlbum(selected, e);
        }
    }

    public void deleteAlbum(){
        int index = albumTable.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if (index>=0)
            albums.remove(index);
    }
    public ObservableList<Album> getAlbums(){

        albums.add(new Album("Master Of Puppets", "Metallica", AlbumGenre.METAL, 2));
        albums.add(new Album("Master Of Puppets", "Metallica", AlbumGenre.CLASSIC, 1));
        albums.add(new Album("Master Of Puppets", "Metallica", AlbumGenre.DANCE, 3));
        albums.add(new Album("Master Of Puppets", "Metallica", AlbumGenre.HIPHOP, 4));

        return albums;
    }
}
