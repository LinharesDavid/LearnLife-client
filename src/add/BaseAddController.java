package add;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import popup.PopupView;
import service.BadgeService;
import service.CategoryService;
import service.TagService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static utils.Constants.*;

public class BaseAddController {

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
    File newImage = null;

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
            categories.add(new Category(jsonObjectCat.getString(KEY_GENERIC_ID),
                    jsonObjectCat.getString(KEY_CATEGORY_NAME)));
        }
        initCategoryCmb();
    }

    JSONArray getBadgesArray(ListView listView) {
        JSONArray badgeArray = new JSONArray();
        for (Object o : listView.getSelectionModel().getSelectedItems()) {
            String name = (String) o;
            for (Badge badge : badges) {
                if (badge.getName().equals(name)) {
                    badgeArray.put(badge.get_id());
                }
            }
        }
        return badgeArray;
    }

    JSONArray getTagsArray(ListView listView) {
        JSONArray tagsArray = new JSONArray();
        for (Object o : listView.getSelectionModel().getSelectedItems()) {
            String name = (String) o;
            for (Tag tag : tags) {
                if (tag.getName().equals(name)) {
                    tagsArray.put(tag.get_id());
                }
            }
        }
        return tagsArray;
    }

    void initImv(Scene scene, ImageView imageView) {
        imageView.setImage(new Image("res/default-image.jpg"));
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            FileChooser.ExtensionFilter imageFilter
                    = new FileChooser.ExtensionFilter(FILE_CHOOSER_TITLE, "*.jpg", "*.png");

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(imageFilter);
            newImage = fc.showOpenDialog(scene.getWindow());
            try {
                FileInputStream inputStream = new FileInputStream(newImage);
                Image img = new Image(inputStream);
                imageView.setImage(img);


            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    void showErorPopup() {
        PopupView popupView = new PopupView();
        popupView.start(ERR, ERR_CONTENT_DIDNT_CHANGED, OK);
        popupView.addOnBtnOkListener(null);
    }

    void initTagListView() {
        if (tags == null) {
            TagService.getAllTags(response -> tags = TagService.parseTags(response), null);
        }
        ObservableList<String> listViewData = FXCollections.observableArrayList();
        for (Tag tag : tags) {
            listViewData.add(tag.getName());
        }
        FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

        txf_tag_search.textProperty().addListener(obs -> {
            String filter = txf_tag_search.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });

        liv_tag.setItems(filteredData);
        liv_tag.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    void initBadgeListView() {
        if (badges == null) {
            BadgeService.getAllBadges(response -> badges = BadgeService.parseBadges(response), null);
        }
        ObservableList<String> listViewData = FXCollections.observableArrayList();
        for (Badge badge : badges) {
            listViewData.add(badge.getName());
        }
        FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

        txf_badge_search.textProperty().addListener(obs -> {
            String filter = txf_badge_search.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });

        liv_badge.setItems(filteredData);
        liv_badge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
}
