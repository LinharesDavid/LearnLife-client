package popup;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class PopupView {

    private Scene scene;
    private Stage stage = new Stage();

    public void start(String title, String message, String btnOk) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("popup_window.fxml"));
            GridPane rootLayout = loader.load();
            PopupController controller = loader.getController();
            controller.setText(message, btnOk);
            stage.setTitle(title);
            scene = new Scene(rootLayout, 200, 200);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOnBtnOkListener(EventHandler event) {
        Button btnOk = (Button) scene.lookup("#btn_ok");
        btnOk.setOnAction(event);
        btnOk.setOnAction(e -> stage.close());
    }

    public void start(String title, String message, String btnOk, String btnNok) {

    }
}
