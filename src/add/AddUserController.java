package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Badge;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import service.UserService;
import utils.Log;

import java.awt.image.BufferedImage;

import static utils.Constants.JSON_ENTRY_KEY_ID;
import static utils.Constants.MODEL_NAME_USER;

public class AddUserController extends AddController {


    @FXML
    private ImageView imv_user;


    private BufferedImage bufferedImage = null;

    public void init(Scene scene) {
        txf_user_points.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_user_points.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        initBadgeListView();
        initTagListView();
        initImv(scene, imv_user);
    }

    @FXML
    private void onBtnValidateClick(ActionEvent event) {

        if (pwf_user_password.getText().isEmpty() ||
                txf_user_email.getText().isEmpty() ||
                txf_user_firstname.getText().isEmpty() ||
                txf_user_lastname.getText().isEmpty()) {
            PopupView popupView =new PopupView();
            popupView.start("Erreur", "You must fill\nevery fields", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            int role = cmb_user_role.getValue().equals("User") ? 0 : 1;

            JSONArray badgeArray = new JSONArray();
            for (Object o : liv_badge.getSelectionModel().getSelectedItems()) {
                String name = (String) o;
                for (Badge badge : badges) {
                    if (badge.getName().equals(name)) {
                        badgeArray.put(badge.get_id());
                    }
                }
            }
            JSONArray tagsArray = new JSONArray();
            for (Object o : liv_tag.getSelectionModel().getSelectedItems()) {
                String name = (String) o;
                for (Tag tag : tags)
                    if (tag.getName().equals(name)) {
                        tagsArray.put(tag.get_id());
                    }
            }

            UserService.addUser(
                    txf_user_email.getText(),
                    pwf_user_password.getText(),
                    txf_user_firstname.getText(),
                    txf_user_lastname.getText(),
                    role,
                    tagsArray,
                    badgeArray,
                    response -> {
                        if (newImage != null) {
                            JSONObject userJson = new JSONObject(response);
                            UserService.setUserImage(userJson.getString(JSON_ENTRY_KEY_ID), newImage);
                        }
                        view.onAddSuccess(MODEL_NAME_USER);
                        view.closeWindow();
                    },
                    (errCode, res) -> showErorPopup()

            );
        }

    }
}
