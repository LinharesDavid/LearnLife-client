package edit;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import static utils.Constants.*;

public class EditBadgeController extends EditController {

    private JSONObject oldBadge;

    private TextField txf_badge_name;
    private TextArea txa_badge_description;
    private TextField txf_badge_points;

    private Button btn_validate;
    private Button btn_cancel;

    public void init(Scene scene, String json) {
        txf_badge_name = (TextField) scene.lookup("#txf_badge_name");
        txa_badge_description = (TextArea) scene.lookup("#txa_badge_description");
        txf_badge_points = (TextField) scene.lookup("#txf_badge_points");
        btn_validate = (Button) scene.lookup("#btn_validate");
        btn_cancel = (Button) scene.lookup("#btn_cancel");

        oldBadge = new JSONObject(json);

        txf_badge_points.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_badge_points.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txf_badge_name.setText(oldBadge.getString(JSON_ENTRY_KEY_BADGE_NAME));
        txa_badge_description.setText(oldBadge.getString(JSON_ENTRY_KEY_BADGE_DESCRIPTION));
        txf_badge_points.setText(String.valueOf(oldBadge.getInt(JSON_ENTRY_KEY_BADGE_ACHIEVEMENT_POINTS)));

        btn_validate.setOnAction(this::onBtnValidateClick);
        btn_cancel.setOnAction(this::onBtnCancelClick);
    }

    private void onBtnValidateClick(ActionEvent event) {
        JSONObject newBadge = new JSONObject();
        newBadge.put(JSON_ENTRY_KEY_ID, oldBadge.getString(JSON_ENTRY_KEY_ID));
        newBadge.put(JSON_ENTRY_KEY_BADGE_NAME, txf_badge_name.getText());
        newBadge.put(JSON_ENTRY_KEY_BADGE_DESCRIPTION, txa_badge_description.getText());
        newBadge.put(JSON_ENTRY_KEY_BADGE_ACHIEVEMENT_POINTS, txf_badge_points.getText());

        editModel(newBadge.toString(), MODEL_NAME_BADGE);
    }
}
