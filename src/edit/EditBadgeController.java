package edit;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;
import service.BadgeService;

import static utils.Constants.*;

public class EditBadgeController extends BaseEditController {

    private JSONObject oldBadge;

    private TextField txf_badge_name;
    private TextArea txa_badge_description;
    private TextField txf_badge_points;
    private ImageView imv_badge;

    private Button btn_validate;
    private Button btn_cancel;

    public void init(Scene scene, String json) {
        txf_badge_name = (TextField) scene.lookup("#txf_badge_name");
        txa_badge_description = (TextArea) scene.lookup("#txa_badge_description");
        txf_badge_points = (TextField) scene.lookup("#txf_badge_points");
        btn_validate = (Button) scene.lookup("#btn_validate");
        btn_cancel = (Button) scene.lookup("#btn_cancel");
        imv_badge = (ImageView) scene.lookup("#imv_badge");

        oldBadge = new JSONObject(json);

        Image image = new Image(BASE_URL + oldBadge.getString(KEY_BADGE_IMAGE));
        imv_badge.setImage(image);

        setTxfNumbersOnly(txf_badge_points);
        initImv(scene, imv_badge);

        txf_badge_name.setText(oldBadge.getString(KEY_BADGE_NAME));
        txa_badge_description.setText(oldBadge.getString(KEY_BADGE_DESCRIPTION));
        txf_badge_points.setText(String.valueOf(oldBadge.getInt(KEY_BADGE_ACHIEVEMENT_POINTS)));

        btn_validate.setOnAction(this::onBtnValidateClick);
        btn_cancel.setOnAction(this::onBtnCancelClick);
    }

    private void onBtnValidateClick(ActionEvent event) {
        JSONObject newBadge = new JSONObject();
        newBadge.put(KEY_GENERIC_ID, oldBadge.getString(KEY_GENERIC_ID));
        newBadge.put(KEY_BADGE_NAME, txf_badge_name.getText());
        newBadge.put(KEY_BADGE_DESCRIPTION, txa_badge_description.getText());
        newBadge.put(KEY_BADGE_ACHIEVEMENT_POINTS, txf_badge_points.getText());

        editModel(newBadge.toString(), MODEL_NAME_BADGE);

        if (newImage != null) {
            BadgeService.setBadgeImage(oldBadge.getString(KEY_GENERIC_ID), newImage);
        }
    }
}
