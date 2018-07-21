package edit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.Category;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import service.CategoryService;
import service.TagService;

import java.util.ArrayList;

import static utils.Constants.*;

public class EditTagController extends BaseEditController {

    private JSONObject oldTag;

    private ListView liv_tag;
    private TextField txf_tag_name;
    private TextField txf_tag_search;
    private ComboBox cmb_tag_category;
    private Button btn_validate;
    private Button btn_cancel;

    public void init(Scene scene, String json) {
        txf_tag_name = (TextField) scene.lookup("#txf_tag_name");
        txf_tag_search = (TextField) scene.lookup("#txf_tag_search");
        cmb_tag_category = (ComboBox) scene.lookup("#cmb_tag_category");
        btn_validate = (Button) scene.lookup("#btn_validate");
        btn_cancel = (Button) scene.lookup("#btn_cancel");
        liv_tag = (ListView) scene.lookup("#liv_tag");

        oldTag = new JSONObject(json);

        initTagListView();

        CategoryService.getAllCategories(
                this::parseCategories,
                null
        );


        txf_tag_name.setText(oldTag.getString(KEY_TAG_NAME));
        btn_validate.setOnAction(this::onBtnValidateClick);
        btn_cancel.setOnAction(this::onBtnCancelClick);
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
            String oldtags = oldTag.getJSONArray(KEY_TAG_TAG_ASSOSCIATED).toString();
            for (Tag tag : tags) {
                if (oldtags.contains(tag.get_id()))
                    liv_tag.getSelectionModel().select(tag.getName());
            }
        }
    }

    private void parseTags(String response) {
        tags = TagService.parseTags(response);
        initTagListView();
    }

    private void parseCategories(String response) {
        categories = new ArrayList<>();
        JSONArray jsonArrayCat = new JSONArray(response);
        for (int i = 0; i < jsonArrayCat.length(); i++) {
            JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);
            categories.add(new Category(jsonObjectCat.getString(KEY_GENERIC_ID),
                    jsonObjectCat.getString(KEY_CATEGORY_NAME)));
        }
        ObservableList<String> comboBoxData = FXCollections.observableArrayList();
        for (Category category : categories) {
            comboBoxData.add(category.getName());
        }
        cmb_tag_category.setItems(comboBoxData);

        for (Category category : categories) {
            if(category.get_id().equals(oldTag.getString(KEY_TAG_CATEGORY)))
                cmb_tag_category.getSelectionModel().select(category.getName());
        }
    }

    private void onBtnValidateClick(ActionEvent event){
        JSONObject newTag = new JSONObject();
        newTag.put(KEY_GENERIC_ID, oldTag.getString(KEY_GENERIC_ID));
        newTag.put(KEY_TAG_NAME, txf_tag_name.getText());

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

        newTag.put(KEY_TAG_TAG_ASSOSCIATED, tagsArray);
        newTag.put(KEY_TAG_CATEGORY, selectedCategoryId);

        editModel(newTag.toString(), MODEL_NAME_TAG);
    }

}
