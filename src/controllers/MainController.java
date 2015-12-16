package controllers;
import controllers.albumControllers.AddAlbumController;
import controllers.albumControllers.EditAlbumController;
import controllers.albumControllers.ShowAlbumController;
import controllers.movieControllers.AddMovieController;
import controllers.movieControllers.EditMovieController;
import controllers.movieControllers.ShowMovieController;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Album;
import models.Model;
import models.Movie;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private AddAlbumController addAlbumController;
    private DeleteController deleteController;
    private EditAlbumController editAlbumController;
    private ShowAlbumController showAlbumController;
    private AddMovieController addMovieController;
    private EditMovieController editMovieController;
    private ShowMovieController showMovieController;
    private Model model;

    private Parent main;
    private Stage primaryStage;
    private ObservableList <Album> albums;
    @FXML private TableView <Album> albumTable;
    @FXML private TableView <Movie> movieTable;
    //@FXML private TableColumn<Album, String> Title,artist,genre, raiting;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void setControllers(AddAlbumController addAlbumController, EditAlbumController editAlbumController,
                               DeleteController deleteController, ShowAlbumController showAlbumController,
                               AddMovieController addMovieController,EditMovieController editMovieController,
                               ShowMovieController showMovieController){
        this.addAlbumController = addAlbumController;
        addAlbumController.setPrimaryStage(primaryStage);

        this.editAlbumController = editAlbumController;
        editAlbumController.setPrimaryStage(primaryStage);
        editAlbumController.setAlbumTable(albumTable);
        // todo set albumTable


        this.showAlbumController = showAlbumController;
        showAlbumController.setPrimaryStage(primaryStage);

        this.deleteController = deleteController;

        this.addMovieController = addMovieController;
        addMovieController.setPrimaryStage(primaryStage);

        this.editMovieController = editMovieController;
        editMovieController.setPrimaryStage(primaryStage);
        editMovieController.setMovieTable(movieTable);

        this.showMovieController = showMovieController;

    }

    /*public void toMenu(){
        primaryStage.setScene(new Scene(main, Main.WIDTH, Main.HEIGHT));
        primaryStage.show();
    }*/



    // - - - - - - - - - Albums - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void showAlbums(){
        Thread thread = new Thread(){
           public void run(){
               model.getAlbums();
               javafx.application.Platform.runLater(
                       new Runnable() {
                           @Override
                           public void run() {
                               albumTable.setItems(model.getNewAlbums());
                           }
                       }
               );
           }
       };thread.start();
    }

    public void refreshAlbums(){
        //albumTable.setItems(model.getNewAlbums());
        albumTable.refresh();

    }

    public void showSelectedAlbum(MouseEvent me){
        Album selected = albumTable.getSelectionModel().getSelectedItem();
        if (me.getClickCount() == 2 && !(selected == null)) {
            showAlbumController.showAlbum(selected);
        }
    }
    public void addAlbum(){
        addAlbumController.createAlbum();
    }

    public void editAlbum(ActionEvent e) {
        Album selected = albumTable.getSelectionModel().getSelectedItem();
        if (!(selected == null)) {
            editAlbumController.editAlbumItems();
        }
    }

    public void deleteAlbum(){
        Thread thread = new Thread(){
            public void run(){
                deleteController.deleteAlbum(albumTable.getSelectionModel().getSelectedIndex());
                showAlbums();
            }
        };thread.start();
    }
    public ObservableList<Album> getAlbums(){
        albums = model.getNewAlbums();
        return albums;
    }

    // - - - - - - - - - - Movies - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void editMovie(ActionEvent e) {
        Movie selected = movieTable.getSelectionModel().getSelectedItem();
        if (!(selected == null)) {
            editMovieController.editMovieItems();
        }
    }
    public void showSelectedMovie(MouseEvent me){
        Movie selected = movieTable.getSelectionModel().getSelectedItem();
        if (me.getClickCount() == 2 && !(selected == null)) {
            showMovieController.showMovie(selected);
        }
    }

    public void showMovies(){
        Thread thread = new Thread(){
            public void run(){
                model.getMovies();
                javafx.application.Platform.runLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                movieTable.setItems(model.getNewMovies());
                            }
                        }
                );
            }
        };thread.start();

    }
    public void refreshMovies(){
        movieTable.refresh();
    }
    public void addMovie(){
        addMovieController.createMovie();
    }
    public void deleteMovie(){
        Thread thread = new Thread(){
            public void run(){
                deleteController.deleteMovie(movieTable.getSelectionModel().getSelectedIndex());
                showMovies();
            }
        };thread.start();
    }

    public void exitMediaCenter(){
        //todo close connection to database
    }

}

