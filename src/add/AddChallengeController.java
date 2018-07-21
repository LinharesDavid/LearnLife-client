package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import service.ChallengeService;

import static utils.Constants.*;

public class AddChallengeController extends BaseAddController {


    @FXML private TextField txf_challenge_name;
    @FXML private TextArea txa_challenge_details;
    @FXML private TextField txf_challenge_points;
    @FXML private TextField txf_challenge_duration;
    @FXML private ComboBox cmb_verified;
    @FXML private ImageView imv_picture;
    @FXML private ListView liv_tag;
    @FXML private ListView liv_badge;

    public void init(Scene scene) {
        initBadgeListView();
        initTagListView();
        setTxfNumbersOnly(txf_challenge_duration);
        initImv(scene, imv_picture);
    }

    public void onBtnValidateClick(ActionEvent event) {
        String name = txf_challenge_name.getText();
        String details = txa_challenge_details.getText();
        String durationStr = txf_challenge_duration.getText();
        int verified = cmb_verified.getSelectionModel().getSelectedItem().toString().equals("Verified") ? 1 : 0;
        JSONArray tagsArray = getTagsArray(liv_tag);

        int points = 0;
        String pointsStr = txf_challenge_points.getText();
        if (!pointsStr.isEmpty()) {
            points = Integer.parseInt(pointsStr);
        }

        if (name.isEmpty()
            || details.isEmpty()
            || durationStr.isEmpty()
            || tagsArray.toList().isEmpty()) {

            PopupView popupView = new PopupView();
            popupView.start("Error", "You must\nfill everything", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            int duration = Integer.parseInt(durationStr);
            JSONArray badgesArray = getBadgesArray(liv_badge);
            ChallengeService.addChallenge(
                    name,
                    details,
                    points,
                    duration,
                    tagsArray,
                    badgesArray,
                    verified,
                    response -> {
                        if (newImage != null) {
                            JSONObject challengeJson = new JSONObject(response);
                            ChallengeService.setChallengeImage(challengeJson.getString(KEY_GENERIC_ID), newImage);
                        }
                        view.onAddSuccess(MODEL_NAME_CHALLENGE);
                        view.closeWindow();
                    },
                    (errCode, res) -> showErorPopup());
        }
    }
}
