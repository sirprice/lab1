package controllers.albumControllers;

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
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by cj And Scalman on 09/12/15.
 * Makes changes in the database by sending requests to the database.
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
    @FXML private ChoiceBox editGenre;
    @FXML private ImageView albumCover;
    @FXML private Button editSubmit;

    public void initialize(URL location, ResourceBundle resources) {


    }
    /**
     * Initialize the primary stage.
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    /**
     * Initialize the model so this scene makes changes on the same data.
     * @param model
     */
    public void setModel(Model model){
        this.model = model;
    }
    /**
     * Initialize the choice boxes.
     */
    public void setChoiceBoxes(){
        editGenre.setItems(model.getAlbumGenreList());
    }
    /**
     * Initialize the the scene and sets prepare the stage before show time.
     * @param parent
     */
    public void setParent(Parent parent){
        this.edit = parent;
        editStage = new Stage();
        editStage.setScene(new Scene(edit, 460, 260));
        editStage.initModality(Modality.APPLICATION_MODAL);
        //editStage.initOwner(primaryStage);
    }

    /**
     * Takes the selected albums data and fill the edit album text fields scene with it.
     */
    public void editAlbumItems(Album selectedAlbum){              //shows album text and window
        this.selectedAlbum = selectedAlbum;


        editTitle.setText(selectedAlbum.getTitle());
        editArtist.setText(selectedAlbum.getArtist());
        editGenre.setValue(selectedAlbum.getGenre());
        editUrl.setText(selectedAlbum.getCoverUrl());

        Image img = new Image(selectedAlbum.getCoverUrl()); // try catch
        albumCover.setImage(img);

        editStage.show();
    }

    /**
     * Sending a request to the database and changes the requested data.
     */
    public void saveAlbum(){

        model.editAlbum(selectedAlbum.getAlbumID(), editArtist.getText(), editGenre.getValue().toString(),editTitle.getText(),editUrl.getText());
        //albumTable.refresh();

        editStage.close();

    }
    /**
     * Cancel mode.
     */
    public void abortEdit(){
        editStage.close();
    }

    public void setAlbumTable(TableView<Album> albumTable){
        this.albumTable = albumTable;
    }

}