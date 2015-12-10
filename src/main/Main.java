package main;
import models.*;
import controllers.*;
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

        InitFxml initFxml = new InitFxml();
        initFxml.initializLoadersAndControllers(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
