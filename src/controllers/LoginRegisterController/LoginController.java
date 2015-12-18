package controllers.LoginRegisterController;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
    private MainController mainController;
    private RegisterController registerController;

    @FXML private PasswordField password;
    @FXML private TextField userName;
    @FXML private Label text;
    //@FXML private MenuItem registerId;

    public void setModel(Model model){
        this.model = model;
    }
    public void setPrimaryStage(Stage primaryStage,Parent root){
        this.primaryStage = primaryStage;
        this.root = root;
    }

    public void setMainRegisterController(MainController mainController,RegisterController registerController) {
        this.mainController = mainController;
        this.registerController = registerController;
    }

    public void setParent(Parent parent){
        this.add = parent;
        loginStage = new Stage();
        loginStage.setScene(new Scene(add, 600,400));
    }

    public void setLoginScene(){
        loginStage.show();
    }
    public void checkLogin(){

        System.out.println(password.getText().toString() + " " + userName.getText().toString());




        if (password.getText().isEmpty() || userName.getText().isEmpty()){
            text.setText("wrong password or username!");
        }else {
            Thread thread = new Thread() {
                public void run() {
                    if (model.authentcateUser(userName.getText(),password.getText())) {
                        javafx.application.Platform.runLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        loginStage.close();
                                        mainController.toMenue();
                                    }
                                }
                        );
                    }
                    else{
                        javafx.application.Platform.runLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        text.setText("wrong password or username!");
                                    }
                                }
                        );
                    }
                }
            };
            thread.start();
        }
    }

    public void registerNewUser(){
        userName.clear();
        password.clear();
        registerController.setRegisterScene();
    }

}
