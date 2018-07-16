package edit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.json.JSONObject;
import popup.PopupView;
import service.OtherService;
import utils.Log;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;

public class EditController {

    public TextArea txaJsonEdit;
    private String prettyJson;
    private String type;
    private EditView view;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        if (txaJsonEdit.getText().equals(prettyJson)) {
            view.closeWindow();
        } else {
            editModel(txaJsonEdit.getText());
        }
    }

    @FXML
    private void onBtnCancelClick(ActionEvent event) {
        view.closeWindow();
    }

    public void setView(EditView view) {
        this.view = view;
    }

    public void setJsonText(String jsonText, String type) {
        this.type = type.toLowerCase();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonText);
        String prettyJsonString = gson.toJson(je);
        prettyJson = prettyJsonString;
        txaJsonEdit.setText(prettyJsonString);
    }

    private void editModel(String json) {
        OtherService.editModel(
                json,
                type,
                response -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca a changé", "OK");
                    popupView.addOnBtnOkListener(e -> {
                        view.onSuccess();
                        view.closeWindow();
                    });
                },
                (errCode, res) -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca marche pas", "OK");
                    popupView.addOnBtnOkListener(null);
                }
        );
    }
}
