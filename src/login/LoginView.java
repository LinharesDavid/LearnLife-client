package login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.OnCloseLoginListener;
import popup.PopupView;

import static utils.Constants.*;

public class LoginView implements LoginController.ConnexionListener{

    private Stage stage = new Stage();
    private LoginController controller;
    private OnCloseLoginListener onCloseLoginListener;

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
            stage.setMaximized(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnCloseRequest(this::onCloseWindow);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onCloseWindow(WindowEvent event){
        onCloseLoginListener.onCloseLogin(false);
    }

    @Override
    public void onConnexionSuccess() {
        onCloseLoginListener.onCloseLogin(true);
        stage.close();
    }

    @Override
    public void onConnexionFailed(String err) {
        PopupView popupView = new PopupView();
        popupView.start(ERR, err, OK);
        popupView.addOnBtnOkListener(event -> {
        });
    }

    public void setOnCloseLoginListener(OnCloseLoginListener listener) {
        onCloseLoginListener = listener;
    }
}
