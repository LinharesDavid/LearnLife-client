package add;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Category;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import service.CategoryService;
import service.TagService;
import service.UserService;
import utils.Log;
import utils.request.builder.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static utils.Constants.*;

public class AddController {

    public PasswordField pwf_user_password;
    public TextField txf_user_email;
    public TextField txf_user_firstname;
    public TextField txf_user_lastname;
    public ComboBox cmb_user_role;

    public TextField txf_tag_name;
    public TextField txf_tag_associated;
    public ListView liv_tag_associated;
    public ComboBox cmb_tag_category;

    private AddView view;

    private ArrayList<Category> categories = null;
    private ArrayList<Tag> tags = null;

    @FXML
    private void onBtnValidateUserClick(ActionEvent event){
        if (pwf_user_password.getText().isEmpty() || txf_user_email.getText().isEmpty() || txf_user_firstname.getText().isEmpty() || txf_user_lastname.getText().isEmpty()) {
            PopupView popupView =new PopupView();
            popupView.start("Erreur", "Tous les champs doivent\netre remplis", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            int role = cmb_user_role.getValue().equals("User") ? 0 : 1;
            addUser(txf_user_email.getText(), pwf_user_password.getText(), txf_user_firstname.getText(), txf_user_lastname.getText(), role);
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
            for (Object o : liv_tag_associated.getSelectionModel().getSelectedItems()) {
                String name = (String) o;
                for (Tag tag : tags) {
                    if (tag.getName().equals(name)) {
                        tagsArray.put(tag.get_id());
                    }
                }
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
        liv_tag_associated.getSelectionModel().clearSelection();
    }

    public void setUpTagLayout() {
        if (tags == null || categories == null) {
            getAllCategory();
        } else {
            ObservableList<String> listViewData = FXCollections.observableArrayList();
            for (Tag tag : tags) {
                listViewData.add(tag.getName());
            }
            FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

            txf_tag_associated.textProperty().addListener(obs->{
                String filter = txf_tag_associated.getText();
                if(filter == null || filter.length() == 0) {
                    filteredData.setPredicate(s -> true);
                }
                else {
                    filteredData.setPredicate(s -> s.contains(filter));
                }
            });
            liv_tag_associated.setItems(filteredData);
            liv_tag_associated.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            ObservableList<String> comboBoxData = FXCollections.observableArrayList();
            for (Category category : categories) {
                comboBoxData.add(category.getName());
            }
            cmb_tag_category.setItems(comboBoxData);
        }
    }

    @FXML
    private void onBtnCancelClick(ActionEvent event) {
        view.closeWindow();
    }

    public void setView(AddView view) {
        this.view = view;
    }

    private void parseCategories(String res) {
        categories = new ArrayList<>();
        JSONArray jsonArrayCat = new JSONArray(res);
        for (int i = 0; i < jsonArrayCat.length(); i++) {
            JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);
            categories.add(new Category(jsonObjectCat.getString(JSON_ENTRY_KEY_ID),
                    jsonObjectCat.getString(JSON_ENTRY_KEY_CATEGORY_NAME)));
        }
        getAllTag();
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
        setUpTagLayout();
    }

    private void addUser(String email, String pwd, String firstname, String lastname, int role){
        UserService.addUser(
                email,
                pwd,
                firstname,
                lastname,
                role,
                response -> {
                    view.onAddSuccess(MODEL_NAME_USER);
                    view.closeWindow();
                },
                (errCode, res) -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca marche pas", "OK");
                    popupView.addOnBtnOkListener(null);
                }

        );
//        RequestBuilder.builder()
//                .setUrl(BASE_URL + EXTENDED_URL_USERS)
//                .setRequestMethod("POST")
//                .addRequestBodyParameter(BODY_PARAMETER_EMAIL, email)
//                .addRequestBodyParameter(BODY_PARAMETER_PASSWORD, pwd)
//                .addRequestBodyParameter(BODY_PARAMETER_FIRSTNAME, firstname)
//                .addRequestBodyParameter(BODY_PARAMETER_LASTNAME, lastname)
//                .addRequestBodyParameter(BODY_PARAMETER_ROLE, role)
//                .setOnResponseSuccessListener(response -> {
//                    view.onAddSuccess(MODEL_NAME_USER);
//                    view.closeWindow();
//                })
//                .setOnResponseFailListener((errCode, res) -> {
//                    PopupView popupView = new PopupView();
//                    popupView.start("Error", "WOULA ca marche pas", "OK");
//                    popupView.addOnBtnOkListener(null);
//                })
//                .build();
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

//        RequestBuilder.builder()
//                .setUrl(BASE_URL + EXTENDED_URL_TAG)
//                .setRequestMethod("POST")
//                .addRequestBodyParameter(BODY_PARAMETER_NAME, name)
//                .addRequestBodyParameter(BODY_PARAMETER_TAG_ASSOCIATED, tags)
//                .addRequestBodyParameter(BODY_PARAMETER_CATEGORY, category)
//                .setOnResponseSuccessListener(response -> {
//                    view.onAddSuccess(MODEL_NAME_TAG);
//                    view.closeWindow();
//                })
//                .setOnResponseFailListener((errCode, res) -> {
//                    PopupView popupView = new PopupView();
//                    popupView.start("Error", "WOULA ca marche pas", "OK");
//                    popupView.addOnBtnOkListener(null);
//                })
//                .build();
    }

    private void getAllCategory() {
        CategoryService.getAllCategories(this::parseCategories, null);
//        RequestBuilder.builder()
//                .setUrl(BASE_URL + EXTENDED_URL_CATEGORY)
//                .setRequestMethod("GET")
//                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
//                .setOnResponseSuccessListener(this::parseCategories)
//                .setOnResponseFailListener((errCode, response) -> System.out.println(errCode + " " + response))
//                .build();

    }

    private void getAllTag() {
        TagService.getAllTags(this::parseTags, null);
//        RequestBuilder.builder()
//                .setUrl(BASE_URL + EXTENDED_URL_TAG)
//                .setRequestMethod("GET")
//                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
//                .setOnResponseSuccessListener(this::parseTags)
//                .setOnResponseFailListener((errCode, response) -> Log.d(String.valueOf(errCode), response))
//                .build();
    }
}
