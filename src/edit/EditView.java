package edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static utils.Constants.*;

public class EditView {

    private Scene scene;
    private Stage stage = new Stage();
    private EditController controller;
    private OnEditSuccessCloseWindowListener listener;

    public void start(String type, String json) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String filename = "../add/add_" + type.toLowerCase() + "_window.fxml";
            loader.setLocation(getClass().getResource(filename));
            GridPane rootLayout = loader.load();
            stage.setTitle(EDIT_ITEM_WINDOW_TITLE + type);
            scene = new Scene(rootLayout);
            switch (type.toLowerCase()) {
                case MODEL_NAME_CATEGORY:
                    controller = new EditCategoryController();
                    loader.setController(controller);
                    ((EditCategoryController) controller).init(scene, json);
                    break;
                case MODEL_NAME_TAG:
                    controller = new EditTagController();
                    loader.setController(controller);
                    ((EditTagController) controller).init(scene, json);
                    break;
                case MODEL_NAME_USER:
                    controller = new EditUserController();
                    loader.setController(controller);
                    ((EditUserController) controller).init(scene, json);
                    break;
                case MODEL_NAME_BADGE:
                    controller = new EditBadgeController();
                    loader.setController(controller);
                    ((EditBadgeController) controller).init(scene, json);
                    break;
            }
            controller.setView(this);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(String json) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String filename = "edit_window.fxml";
            loader.setLocation(getClass().getResource(filename));
            GridPane rootLayout = loader.load();
            stage.setTitle("Json");
            scene = new Scene(rootLayout);
            controller = loader.getController();
            controller.setView(this);
            controller.setJsonText(json);
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
