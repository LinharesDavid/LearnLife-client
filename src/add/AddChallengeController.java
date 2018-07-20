package add;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import popup.PopupView;

public class AddChallengeController extends AddController {


    public TextField txf_challenge_name;
    public TextArea txa_challenge_details;
    public TextField txf_challenge_points;
    public TextField txf_challenge_duration;
    public ComboBox cmb_verified;
    public ImageView imv_picture;

    public void init(Scene scene) {
        initBadgeListView();
        initTagListView();
        setTxfNumbersOnly(txf_challenge_duration);
    }

    public void onBtnValidateClick(ActionEvent event) {
        String name = txf_challenge_name.getText();
        String details = txa_challenge_details.getText();
        String duration = txf_challenge_duration.getText();
        int verified = cmb_verified.getSelectionModel().getSelectedItem().toString().equals("Verified") ? 1 : 0;
        JSONArray tagsArray = getTagsArray();

        if (name.isEmpty()
            || details.isEmpty()
            || duration.isEmpty()
            || tagsArray.toList().isEmpty()) {

            PopupView popupView = new PopupView();
            popupView.start("Error", "You must\nfill everything", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            JSONArray badgesArray = getBadgesArray();

            //TODO addChallenge
        }






    }
}
