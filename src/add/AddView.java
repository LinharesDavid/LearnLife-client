package add;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Badge;
import model.Tag;

import static utils.Constants.*;

public class AddView {
    private Scene scene;
    private Stage stage = new Stage();
    private AddController controller;
    private OnAddSuccessCloseWindowListener listener;

    public void start(String type) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String filename = "add_" + type.toLowerCase() + "_window.fxml";

            loader.setLocation(getClass().getResource(filename));
            GridPane rootLayout = loader.load();
            controller = loader.getController();
            controller.setView(this);
            if (type.toLowerCase().equals(MODEL_NAME_USER)) {
                controller.initForUser();
            }
            if (type.toLowerCase().equals(Tag.class.getSimpleName().toLowerCase())) {
                controller.initForTags();
            }
            if (type.toLowerCase().equals(Badge.class.getSimpleName().toLowerCase())) {
                controller.setTxf_badge_pointsNumbersOnly();
            }
            stage.setTitle(ADD_ITEM_WINDOW_TITLE + type);
            scene = new Scene(rootLayout);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindow() {
        controller = null;
        stage.close();
    }

    public void onAddSuccess(String type) {
        listener.onAddSuccessCloseWindowListener(type);
    }

    public void setOnCloseAddWindowListener(OnAddSuccessCloseWindowListener listener) {
        this.listener = listener;
    }

    public interface OnAddSuccessCloseWindowListener {
        void onAddSuccessCloseWindowListener(String type);
    }
}
