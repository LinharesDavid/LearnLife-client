package edit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Badge;
import model.Category;
import model.Tag;
import popup.PopupView;
import service.BadgeService;
import service.OtherService;
import service.TagService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class EditController {

    public TextArea txaJsonEdit;
    private String prettyJson;
    protected EditView view;

    protected ArrayList<Tag> tags;
    protected ArrayList<Badge> badges;
    ArrayList<Category> categories;
    File newImage = null;

    ListView liv_badge;
    ListView liv_tag;
    TextField txf_badge_search;
    TextField txf_tag_search;

    @FXML
    protected void onBtnCancelClick(ActionEvent event) {
        view.closeWindow();
    }

    public void setView(EditView view) {
        this.view = view;
    }

    public void setJsonText(String jsonText) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonText);
        String prettyJsonString = gson.toJson(je);
        prettyJson = prettyJsonString;
        txaJsonEdit.setText(prettyJsonString);
    }

    void editModel(String json, String type) {
        OtherService.editModel(
                json,
                type,
                response -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca a changé", "OK");
                    popupView.addOnBtnOkListener(e -> {
                        view.onSuccess();
                        view.closeWindow();
                    });
                },
                (errCode, res) -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca marche pas", "OK");
                    popupView.addOnBtnOkListener(null);
                }
        );
    }

    void initTagListView(String oldTags) {
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
        for (Tag tag : tags) {
            if (oldTags.contains(tag.get_id()))
                liv_tag.getSelectionModel().select(tag.getName());
        }

    }

    void initBadgeListView(String oldTags) {
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
        for (Badge badge : badges) {
            if (oldTags.contains(badge.get_id()))
                liv_badge.getSelectionModel().select(badge.getName());
        }

    }

    void setTxfNumbersOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    void initImv(Scene scene, ImageView imageView) {
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            FileChooser.ExtensionFilter imageFilter
                    = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

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
}
