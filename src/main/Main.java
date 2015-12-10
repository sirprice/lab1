package main;

import controllers.AddAlbumController;
import controllers.DeleteController;
import controllers.EditAlbumController;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    @Override
    public void start(Stage primaryStage) throws Exception{

        DeleteController dCtrl = new DeleteController();

        Parent editAlbum = null;
        FXMLLoader editAlbumLoader = new FXMLLoader();
        editAlbum = editAlbumLoader.load(getClass().getResource("/fxml/editAlbum.fxml").openStream());
        EditAlbumController eACtrl = editAlbumLoader.getController();
        eACtrl.setParent(editAlbum);
        if (eACtrl == null) System.out.println("edit album controllern är null");

        Parent addAlbum = null;
        FXMLLoader addAlbumLoader = new FXMLLoader();
        addAlbum = addAlbumLoader.load(getClass().getResource("/fxml/addAlbum.fxml").openStream());
        AddAlbumController aACtrl = addAlbumLoader.getController();
        aACtrl.setParent(addAlbum);
        if (aACtrl == null) System.out.println("add album controllern är null");

        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        System.out.println(getClass().getResource("/fxml/main.fxml"));
        root = loader.load(getClass().getResource("/fxml/main.fxml").openStream());
        MainController mCtrl = loader.getController();
        if (mCtrl == null) System.out.println("main controllern är null");
        mCtrl.setPrimaryStage(primaryStage);
        mCtrl.setControllers(aACtrl, eACtrl, dCtrl);

        primaryStage.setTitle("Media Center");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
