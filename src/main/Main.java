package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import login.LoginView;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

import static utils.Constants.MAIN_WINDOW_TITLE;

public class Main extends Application implements OnCloseLoginListener{

    private Stage primaryStage;
    private MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main_window.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        Image icon_16 = new Image(getClass().getResourceAsStream("/res/icon_16.png"));
        Image icon_64 = new Image(getClass().getResourceAsStream("/res/icon_64.png"));
        Image icon_32 = new Image(getClass().getResourceAsStream("/res/icon_32.png"));
        Image icon_128 = new Image(getClass().getResourceAsStream("/res/icon_128.png"));
        Image icon_256 = new Image(getClass().getResourceAsStream("/res/icon_256.png"));
        Image icon_512 = new Image(getClass().getResourceAsStream("/res/icon_512.png"));
        primaryStage.getIcons().add(icon_16);
        primaryStage.getIcons().add(icon_64);
        primaryStage.getIcons().add(icon_32);
        primaryStage.getIcons().add(icon_128);
        primaryStage.getIcons().add(icon_256);
        primaryStage.getIcons().add(icon_512);
        primaryStage.setTitle(MAIN_WINDOW_TITLE);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMaximized(true);
        primaryStage.show();

        LoginView loginView = new LoginView();
        loginView.start();
        loginView.setOnCloseLoginListener(this);
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onCloseLogin(boolean connected) {
        if (connected) {
            mainController.start();
        } else {
            primaryStage.close();
        }
    }
}
