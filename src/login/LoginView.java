package login;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import login.LoginController;
import popup.PopupView;

public class LoginView implements LoginController.ConnexionListener{

    private Stage stage = new Stage();
    private LoginController controller;

    public void start() {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login_window.fxml"));
            GridPane rootLayout = loader.load();
            controller = loader.getController();
            controller.setListener(this);
            scene = new Scene(rootLayout, 300, 200);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnexionSuccess() {
        stage.close();
    }

    @Override
    public void onConnexionFailed(String err) {
        PopupView popupView = new PopupView();
        popupView.start("Error", err, "OK");
        popupView.addOnBtnOkListener(event -> {
            System.out.println("clicked bitch suce ma cite sous la pluie stp");
        });
    }
}
