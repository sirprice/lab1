package controllers;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 09/12/15.
 */
public class AddAlbumController implements Initializable {


    private Parent add;

    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setParent(Parent parent){
        this.add = parent;
    }
}
