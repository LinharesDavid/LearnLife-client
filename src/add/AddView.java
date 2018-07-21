package add;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static utils.Constants.*;

public class AddView {
    private Scene scene;
    private Stage stage = new Stage();
    private BaseAddController controller;
    private OnAddSuccessCloseWindowListener listener;

    public void start(String type) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String filename = "add_" + type.toLowerCase() + "_window.fxml";

            loader.setLocation(getClass().getResource(filename));
            GridPane rootLayout = loader.load();
            controller = loader.getController();
            scene = new Scene(rootLayout);
            switch (type.toLowerCase()) {
                case MODEL_NAME_USER:
                    ((AddUserController) controller).init(scene);
                    break;
                case MODEL_NAME_TAG:
                    ((AddTagController) controller).init(scene);
                    break;
                case MODEL_NAME_BADGE:
                    ((AddBadgeController) controller).init(scene);
                    break;
                case MODEL_NAME_CATEGORY:
                    ((AddCategoryController) controller).init(scene);
                case MODEL_NAME_CHALLENGE:
                    ((AddChallengeController) controller).init(scene);
            }
            controller.setView(this);
            stage.setTitle(ADD_ITEM_WINDOW_TITLE + type);
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
