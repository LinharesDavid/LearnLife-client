package edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static utils.Constants.ADD_ITEM_WINDOW_TITLE;

public class EditView {

    private Scene scene;
    private Stage stage = new Stage();
    private EditController controller;
    private OnEditSuccessCloseWindowListener listener;

    public void start(String type, String json) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String filename = "edit_window.fxml";
            loader.setLocation(getClass().getResource(filename));
            GridPane rootLayout = loader.load();
            controller = loader.getController();
            controller.setView(this);
            controller.setJsonText(json, type);
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

    public void onSuccess() {
        listener.onEditSuccessCloseWindow();
    }

    public void setOnCloseEditWindowListener(OnEditSuccessCloseWindowListener listener) {
        this.listener = listener;
    }

    public interface OnEditSuccessCloseWindowListener {
        void onEditSuccessCloseWindow();
    }
}
