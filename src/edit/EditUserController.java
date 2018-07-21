package edit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Badge;
import model.Category;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import service.OtherService;
import service.TagService;
import service.UserService;
import utils.Log;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static utils.Constants.*;


public class EditUserController extends EditController {

    private JSONObject oldUser;
    private ArrayList<Badge> badges = null;

    private TextField txf_user_email;
    private TextField txf_user_firstname;
    private TextField txf_user_lastname;
    private TextField txf_user_points;
    private ImageView imv_user;
    private PasswordField pwf_user_password;
    private ComboBox cmb_user_role;
    private Button btn_validate;
    private Button btn_cancel;
    private Button btn_unselect_tags;
    private Button btn_unselect_badges;



    public void init(Scene scene, String json) {
        txf_user_email = (TextField) scene.lookup("#txf_user_email");
        txf_user_firstname = (TextField) scene.lookup("#txf_user_firstname");
        txf_user_lastname = (TextField) scene.lookup("#txf_user_lastname");
        txf_user_points = (TextField) scene.lookup("#txf_user_points");
        txf_tag_search = (TextField) scene.lookup("#txf_tag_search");
        txf_badge_search = (TextField) scene.lookup("#txf_badge_search");
        imv_user = (ImageView) scene.lookup("#imv_user");
        pwf_user_password = (PasswordField) scene.lookup("#pwf_user_password");
        liv_tag = (ListView) scene.lookup("#liv_tag");
        liv_badge = (ListView) scene.lookup("#liv_badge");
        cmb_user_role = (ComboBox) scene.lookup("#cmb_user_role");
        btn_validate = (Button) scene.lookup("#btn_validate");
        btn_cancel = (Button) scene.lookup("#btn_cancel");
        btn_unselect_tags = (Button) scene.lookup("#btn_unselect_tags");
        btn_unselect_badges = (Button) scene.lookup("#btn_unselect_badges");

        oldUser = new JSONObject(json);

        Image image = new Image(BASE_URL + oldUser.getString("thumbnailUrl"));
        imv_user.setImage(image);

        txf_user_email.setText(oldUser.getString(KEY_USER_EMAIL));
        txf_user_firstname.setText(oldUser.getString(KEY_USER_FIRSTNAME));
        txf_user_lastname.setText(oldUser.getString(KEY_USER_LASTNAME));
        txf_user_points.setText(String.valueOf(oldUser.getInt(KEY_USER_POINTS)));

        btn_validate.setOnAction(this::onBtnValidateClick);
        btn_cancel.setOnAction(this::onBtnCancelClick);
        btn_unselect_badges.setOnAction(this::onBtnUnselectBadgesClick);
        btn_unselect_tags.setOnAction(this::onBtnUnselectTagsClick);

        initTagListView(oldUser.getJSONArray(KEY_USER_TAGS).toString());
        initBadgeListView(oldUser.getJSONArray(KEY_USER_BADGES).toString());
        initImv(scene, imv_user);
    }

    private void onBtnValidateClick(ActionEvent event) {
        JSONObject newUser = new JSONObject();
        newUser.put(KEY_GENERIC_ID, oldUser.getString(KEY_GENERIC_ID));
        newUser.put(KEY_USER_EMAIL, txf_user_email.getText());
        if (!pwf_user_password.getText().isEmpty()) {
            newUser.put(KEY_USER_PASSWORD, pwf_user_password.getText());
        }
        int role = cmb_user_role.getValue().equals("User") ? 0 : 1;
        newUser.put(KEY_USER_ROLE, role);
        newUser.put(KEY_USER_FIRSTNAME, txf_user_firstname.getText());
        newUser.put(KEY_USER_LASTNAME, txf_user_lastname.getText());
        newUser.put(KEY_USER_POINTS, txf_user_points.getText());

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

        newUser.put(KEY_USER_TAGS, tagsArray);
        newUser.put(KEY_USER_BADGES, badgesArray);

        editModel(newUser.toString(), MODEL_NAME_USER);

        if (newImage!= null) {
            UserService.setUserImage(newUser.getString(KEY_GENERIC_ID), newImage);
        }
    }

    private void onBtnUnselectBadgesClick(ActionEvent event) {
        liv_badge.getSelectionModel().clearSelection();
    }

    private void onBtnUnselectTagsClick(ActionEvent event) {
        liv_tag.getSelectionModel().clearSelection();
    }
}
