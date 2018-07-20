package add;

import edit.EditController;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Badge;
import model.Category;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import service.BadgeService;
import service.CategoryService;
import service.TagService;

import java.util.ArrayList;

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
    public TextField txf_user_points;
    public ListView liv_badge;
    public TextField txf_badge_search;

    AddView view;

    ArrayList<Category> categories = null;
    ArrayList<Tag> tags = null;
    ArrayList<Badge> badges = null;

    void initTagListView() {
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

    void initBadgeListView() {
        if (badges == null) {
            BadgeService.getAllBadges(this::parseBadges, null);
        } else {
            ObservableList<String> listViewData = FXCollections.observableArrayList();
            for (Badge badge : badges) {
                listViewData.add(badge.getName());
            }
            FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

            setFilterTxtField(filteredData, txf_badge_search.textProperty(), txf_badge_search.getText());
            liv_badge.setItems(filteredData);
            liv_badge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    private void setFilterTxtField(FilteredList<String> filteredData, StringProperty stringProperty, String text) {
        EditController.setTextFieldFilterForListView(filteredData, stringProperty, text);
    }

    void initCategoryCmb() {
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
    private void onBtnUnselectAllClick(ActionEvent event) {
        liv_tag.getSelectionModel().clearSelection();
    }

    @FXML
    void onBtnCancelClick(ActionEvent event) {
        view.closeWindow();
    }

    public void setView(AddView view) {
        this.view = view;
    }

    void setTxfNumbersOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
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
        tags = TagService.parseTags(response);
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
        initBadgeListView();
    }

    JSONArray getBadgesArray() {
        JSONArray badgeArray = new JSONArray();
        for (Object o : liv_badge.getSelectionModel().getSelectedItems()) {
            String name = (String) o;
            for (Badge badge : badges) {
                if (badge.getName().equals(name)) {
                    badgeArray.put(badge.get_id());
                }
            }
        }
        return badgeArray;
    }

    JSONArray getTagsArray() {
        JSONArray tagsArray = new JSONArray();
        for (Object o : liv_tag.getSelectionModel().getSelectedItems()) {
            String name = (String) o;
            for (Tag tag : tags) {
                if (tag.getName().equals(name)) {
                    tagsArray.put(tag.get_id());
                }
            }
        }
        return tagsArray;
    }
}
