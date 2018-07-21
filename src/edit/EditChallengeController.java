package edit;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Badge;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ChallengeService;

import static utils.Constants.*;

public class EditChallengeController extends BaseEditController {

    private JSONObject oldChallenge;

    private TextField txf_challenge_name;
    private TextArea txa_challenge_details;
    private TextField txf_challenge_points;
    private TextField txf_challenge_duration;
    private Button btn_validate;
    private Button btn_cancel;
    private ComboBox cmb_verified;
    private ImageView imv_picture;

    public void init(Scene scene, String json) {
        txf_challenge_name = (TextField) scene.lookup("#txf_challenge_name");
        txa_challenge_details = (TextArea) scene.lookup("#txa_challenge_details");
        txf_challenge_points = (TextField) scene.lookup("#txf_challenge_points");
        txf_challenge_duration = (TextField) scene.lookup("#txf_challenge_duration");
        txf_tag_search = (TextField) scene.lookup("#txf_tag_search");
        txf_badge_search = (TextField) scene.lookup("#txf_badge_search");
        btn_validate = (Button) scene.lookup("#btn_validate");
        btn_cancel = (Button) scene.lookup("#btn_cancel");
        liv_tag = (ListView) scene.lookup("#liv_tag");
        cmb_verified = (ComboBox) scene.lookup("#cmb_verified");
        imv_picture = (ImageView) scene.lookup("#imv_picture");
        liv_badge = (ListView) scene.lookup("#liv_badge");

        oldChallenge = new JSONObject(json);

        Image image = new Image(BASE_URL + oldChallenge.getString(KEY_CHALLENGE_IMAGE));
        imv_picture.setImage(image);

        initImv(scene, imv_picture);

        txf_challenge_name.setText(oldChallenge.getString(KEY_CHALLENGE_NAME));
        txa_challenge_details.setText(oldChallenge.getString(KEY_CHALLENGE_DETAILS));
        txf_challenge_points.setText(String.valueOf(oldChallenge.getInt(KEY_CHALLENGE_POINTS_GIVEN)));
        txf_challenge_duration.setText(String.valueOf(oldChallenge.getInt(KEY_CHALLENGE_DURATION)));
        int verified = oldChallenge.getInt(KEY_CHALLENGE_VERIFIED);
        switch (verified) {
            case 0:
                cmb_verified.getSelectionModel().selectFirst();
                break;
            case 1:
                cmb_verified.getSelectionModel().select(1);
                break;
            default:
                cmb_verified.getSelectionModel().selectFirst();

        }

        initTagListView(oldChallenge.getJSONArray(KEY_CHALLENGE_TAGS).toString());
        initBadgeListView(oldChallenge.getString(KEY_CHALLENGE_BADGE));
        liv_badge.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        btn_cancel.setOnAction(this::onBtnCancelClick);
        btn_validate.setOnAction(this::onBtnValidateClick);

    }

    private void onBtnValidateClick(ActionEvent event) {
        JSONObject newChallenge = new JSONObject();
        newChallenge.put(KEY_GENERIC_ID, oldChallenge.getString(KEY_GENERIC_ID));
        String chalName = txf_challenge_name.getText();
        String details = txa_challenge_details.getText();
        int points = Integer.parseInt(txf_challenge_points.getText());
        int duration = Integer.parseInt(txf_challenge_duration.getText());
        int verified = cmb_verified.getSelectionModel().getSelectedIndex();

        JSONArray tagsArray = new JSONArray();
        for (Object o : liv_tag.getSelectionModel().getSelectedItems()) {
            String name = (String) o;
            for (Tag tag : tags)
                if (tag.getName().equals(name))
                    tagsArray.put(tag.get_id());
        }

        JSONArray badgesArray = new JSONArray();
        for (Object o : liv_badge.getSelectionModel().getSelectedItems()) {
            String name = (String) o;
            for (Badge badge : badges)
                if (badge.getName().equals(name))
                    badgesArray.put(badge.get_id());
        }
        String badge = badgesArray.getString(0);

        newChallenge.put(KEY_CHALLENGE_NAME, chalName);
        newChallenge.put(KEY_CHALLENGE_DETAILS, details);
        newChallenge.put(KEY_CHALLENGE_POINTS_GIVEN, points);
        newChallenge.put(KEY_CHALLENGE_DURATION, duration);
        newChallenge.put(KEY_CHALLENGE_VERIFIED, verified);
        newChallenge.put(KEY_CHALLENGE_TAGS, tagsArray);
        newChallenge.put(KEY_CHALLENGE_BADGE, badge);

        editModel(newChallenge.toString(), MODEL_NAME_CHALLENGE);

        if (newImage != null) {
            ChallengeService.setChallengeImage(oldChallenge.getString(KEY_GENERIC_ID), newImage);
        }

    }
}
