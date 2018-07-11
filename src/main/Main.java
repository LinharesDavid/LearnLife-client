package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginView;
import utils.Log;

import static utils.Constants.*;

public class Main extends Application implements OnCloseLoginListener{

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Log.i("program started");
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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
    public void onCloseLogin() {
        primaryStage.close();
    }
}
