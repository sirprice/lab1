package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Model;

/**
 * Created by cj on 09/12/15.
 */
public class LoginController {

    private Stage loginStage,primaryStage;
    private Parent add,root;
    private Model model;

    @FXML private PasswordField password;
    @FXML private TextField userName;
    @FXML private Label text;

    public void setModel(Model model){
        this.model = model;
    }
    public void setPrimaryStage(Stage primaryStage,Parent root){
        this.primaryStage = primaryStage;
        this.root = root;
    }

    public void setParent(Parent parent){
        this.add = parent;
        loginStage = new Stage();
        loginStage.setScene(new Scene(add, 600,400));
        loginStage.show();
    }

    public void checkLogin(){

        System.out.println(password.getText().toString() + " " + userName.getText().toString());
        System.out.printf("jhiuhihuih");
        if (password.getText().isEmpty() || userName.getText().isEmpty()){

            text.setText("wrong password or username!");
        }else {
            primaryStage.setTitle("Media Center");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
            loginStage.close();
        }
    }
}
