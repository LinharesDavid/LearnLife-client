package add;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static utils.Constants.ADD_ITEM_WINDOW_TITLE;

public class AddView implements AddController.OnAddSuccessListener{
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

    @Override
    public void onAddSuccess() {
        listener.onAddSuccessCloseWindowListener();
    }

    public void setOnCloseLoginListener(OnAddSuccessCloseWindowListener listener) {
        this.listener = listener;
    }

    public interface OnAddSuccessCloseWindowListener {
        void onAddSuccessCloseWindowListener();
    }
}
