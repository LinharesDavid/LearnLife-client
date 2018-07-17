package add;

import com.google.gson.JsonObject;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Badge;
import model.Category;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import service.BadgeService;
import service.CategoryService;
import service.TagService;
import service.UserService;
import utils.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;

import static utils.Constants.*;

public class AddController {

    public PasswordField pwf_user_password;
    public TextField txf_user_email;
    public TextField txf_user_firstname;
    public TextField txf_user_lastname;
    public ComboBox cmb_user_role;

    public TextField txf_tag_name;
    public TextField txf_tag_search;
    public ListView liv_tag;
    public ComboBox cmb_tag_category;
    public TextField txf_category_name;
    public TextField txf_badge_name;
    public TextArea txa_badge_description;
    public TextField txf_badge_points;
    public TextField txf_user_points;
    public ListView liv_user_badge;
    public TextField txf_badge_search;
    public ImageView imv_user_profile;

    private AddView view;

    private ArrayList<Category> categories = null;
    private ArrayList<Tag> tags = null;
    private ArrayList<Badge> badges = null;
    private BufferedImage bufferedImage = null;

    public void initForUser(Stage stage) {
        try {
            FileInputStream fileInputStream = new FileInputStream("res/default_image.jpg");
            Image image = new Image(fileInputStream);
            imv_user_profile.setImage(image);
            txf_user_points.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    txf_user_points.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
            initBadgeListView();
            initTagListView();
            imv_user_profile.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                FileChooser.ExtensionFilter imageFilter
                        = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(imageFilter);
                File newImage = fc.showOpenDialog(stage);
                try {
                    FileInputStream inputStream = new FileInputStream(newImage);
                    Image img = new Image(inputStream);
                    imv_user_profile.setImage(img);

                    FileInputStream input = new FileInputStream(newImage);


                    bufferedImage = ImageIO.read(input);


                    File output = new File("c:/temp/image.bmp");


                    ImageIO.write(bufferedImage, "bmp", output);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
        } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    }

    public void initForTags() {
        initCategoryCmb();
        initTagListView();
    }

    private void initTagListView() {
        if (tags == null) {
            TagService.getAllTags(this::parseTags, null);
        } else {
            ObservableList<String> listViewData = FXCollections.observableArrayList();
            for (Tag tag : tags) {
                listViewData.add(tag.getName());
            }
            FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

            setFilterTxtField(filteredData, txf_tag_search.textProperty(), txf_tag_search.getText());
            liv_tag.setItems(filteredData);
            liv_tag.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    private void initBadgeListView() {
        if (badges == null) {
            BadgeService.getAllBadges(this::parseBadges, null);
        } else {
            ObservableList<String> listViewData = FXCollections.observableArrayList();
            for (Badge badge : badges) {
                listViewData.add(badge.getName());
            }
            FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

            setFilterTxtField(filteredData, txf_badge_search.textProperty(), txf_badge_search.getText());
            liv_user_badge.setItems(filteredData);
            liv_user_badge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    private void setFilterTxtField(FilteredList<String> filteredData, StringProperty stringProperty, String text) {
        stringProperty.addListener(obs -> {
            String filter = text;
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });
    }

    private void initCategoryCmb() {
        if (categories == null) {
            CategoryService.getAllCategories(this::parseCategories, null);
        } else {
            ObservableList<String> comboBoxData = FXCollections.observableArrayList();
            for (Category category : categories) {
                comboBoxData.add(category.getName());
            }
            cmb_tag_category.setItems(comboBoxData);
        }
    }

    @FXML
    private void onBtnValidateUserClick(ActionEvent event){
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
            for (Object o : liv_user_badge.getSelectionModel().getSelectedItems()) {
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

            addUser(txf_user_email.getText(),
                    pwf_user_password.getText(),
                    txf_user_firstname.getText(),
                    txf_user_lastname.getText(),
                    role,
                    tagsArray,
                    badgeArray);
        }
    }

    @FXML
    private void onBtnValidateTagClick(ActionEvent event) {
        String newTagName = txf_tag_name.getText();
        if (newTagName.isEmpty() || cmb_tag_category.getSelectionModel().getSelectedItem() == null) {
            PopupView popupView = new PopupView();
            popupView.start("Erreur", "You must selet\na categorie and fill\nthe name field", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            JSONArray tagsArray = new JSONArray();
            for (Object o : liv_tag.getSelectionModel().getSelectedItems()) {
                String name = (String) o;
                for (Tag tag : tags)
                    if (tag.getName().equals(name))
                        tagsArray.put(tag.get_id());
            }
            String selectedCategory = cmb_tag_category.getSelectionModel().getSelectedItem().toString();
            String selectedCategoryId = "";
            for (Category category : categories) {
                if (selectedCategory.equals(category.getName())) {
                    selectedCategoryId = category.get_id();
                }
            }

            addTag(newTagName, tagsArray, selectedCategoryId);
        }
    }

    @FXML
    private void onBtnUnselectAllClick(ActionEvent event) {
        liv_tag.getSelectionModel().clearSelection();
    }

    @FXML
    private void onBtnValidateCategoryClick(ActionEvent event) {
        String name = txf_category_name.getText();
        if (name.isEmpty()) {
            PopupView popupView = new PopupView();
            popupView.start("Erreur", "You must fill\nevery fields", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            CategoryService.addCategory(
                    name,
                    response -> {
                        view.onAddSuccess(MODEL_NAME_CATEGORY);
                        view.closeWindow();
                    },
                    (errCode, res) -> {
                        PopupView popupView = new PopupView();
                        popupView.start("Error", "WOULA ca marche pas", "OK");
                        popupView.addOnBtnOkListener(null);
                    }
            );
        }
    }

    @FXML
    private void onBtnValidateChallengeClick(ActionEvent event) {
        if (txf_badge_name.getText().isEmpty() ||
                txa_badge_description.getText().isEmpty() ||
                txf_badge_points.getText().isEmpty()) {
            PopupView popupView = new PopupView();
            popupView.start("Erreur", "You must fill\nevery fields", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            BadgeService.addBadge(
                    txf_badge_name.getText(),
                    txa_badge_description.getText(),
                    "image",
                    Integer.parseInt(txf_badge_points.getText()),
                    response -> {
                        view.onAddSuccess(MODEL_NAME_BADGE);
                        view.closeWindow();
                    },
                    (errCode, res) -> {
                        PopupView popupView = new PopupView();
                        popupView.start("Error", "WOULA ca marche pas", "OK");
                        popupView.addOnBtnOkListener(null);
                    }
            );
        }
    }

    @FXML
    private void onBtnCancelClick(ActionEvent event) {
        view.closeWindow();
    }

    public void setView(AddView view) {
        this.view = view;
    }

    public void setTxf_badge_pointsNumbersOnly() {
        txf_badge_points.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_badge_points.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void parseCategories(String res) {
        categories = new ArrayList<>();
        JSONArray jsonArrayCat = new JSONArray(res);
        for (int i = 0; i < jsonArrayCat.length(); i++) {
            JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);
            categories.add(new Category(jsonObjectCat.getString(JSON_ENTRY_KEY_ID),
                    jsonObjectCat.getString(JSON_ENTRY_KEY_CATEGORY_NAME)));
        }
        initCategoryCmb();
    }

    private void parseTags(String response) {
        tags = new ArrayList<>();
        JSONArray jsonArrayCat = new JSONArray(response);
        for (int i = 0; i < jsonArrayCat.length(); i++) {
            JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);
            Tag tag = new Tag();
            tag.set_id(jsonObjectCat.getString(JSON_ENTRY_KEY_ID));
            tag.setName(jsonObjectCat.getString(JSON_ENTRY_KEY_TAG_NAME));

            tags.add(tag);
        }
        initTagListView();
    }

    private void parseBadges(String res) {
        badges = new ArrayList<>();
        JSONArray jsonArrayBadges = new JSONArray(res);
        for (int i = 0; i < jsonArrayBadges.length(); i++) {
            JSONObject jsonObjectBadge = jsonArrayBadges.getJSONObject(i);
            Badge badge = new Badge();
            badge.setName(jsonObjectBadge.getString(JSON_ENTRY_KEY_BADGE_NAME));
            badge.set_id(jsonObjectBadge.getString(JSON_ENTRY_KEY_ID));
            badges.add(badge);
        }
    }

    private void addUser(String email, String pwd, String firstname, String lastname, int role, JSONArray tags, JSONArray badge){
        UserService.addUser(
                email,
                pwd,
                firstname,
                lastname,
                role,
                tags,
                badge,
                response -> {
                    if (bufferedImage != null)
                        addUserPicture(response);
                    view.onAddSuccess(MODEL_NAME_USER);
                    view.closeWindow();
                },
                (errCode, res) -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca marche pas", "OK");
                    popupView.addOnBtnOkListener(null);
                }

        );
    }

    private void addUserPicture(String response){
        JSONObject jsonObject = new JSONObject(response);
        String userId = jsonObject.getString(JSON_ENTRY_KEY_ID);

        UserService.setUserImage(userId, bufferedImage);
    }

    private void addTag(String name, JSONArray tags, String category) {
        TagService.addTag(
                name,
                tags,
                category,
                response -> {
                    view.onAddSuccess(MODEL_NAME_TAG);
                    view.closeWindow();
                },
                (errCode, res) -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca marche pas", "OK");
                    popupView.addOnBtnOkListener(null);
                }
        );
    }

    private void getAllCategory() {
        CategoryService.getAllCategories(this::parseCategories, null);

    }

    private void getAllTag() {
        TagService.getAllTags(this::parseTags, null);
    }
}
