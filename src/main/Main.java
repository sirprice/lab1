package main;

import javafx.application.Application;
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
