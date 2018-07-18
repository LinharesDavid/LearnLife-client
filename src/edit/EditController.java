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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Badge;
import model.Tag;
import popup.PopupView;
import service.BadgeService;
import service.OtherService;
import service.TagService;

import java.util.ArrayList;

public class EditController {

    public TextArea txaJsonEdit;
    private String prettyJson;
    protected EditView view;

    protected ArrayList<Tag> tags;
    protected ArrayList<Badge> badges;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        if (txaJsonEdit.getText().equals(prettyJson)) {
            view.closeWindow();
        } else {
            //editModel(txaJsonEdit.getText());
        }
    }

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

    protected void editModel(String json, String type) {
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

    void initTagListView(TextField textField, String oldTags, ListView liv_tag) {
        if (tags == null) {
            TagService.getAllTags(response -> tags = TagService.parseTags(response), null);
        }
        ObservableList<String> listViewData = FXCollections.observableArrayList();
        for (Tag tag : tags) {
            listViewData.add(tag.getName());
        }
        FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

        setFilterTxtField(filteredData, textField.textProperty(), textField.getText());

        liv_tag.setItems(filteredData);
        liv_tag.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (Tag tag : tags) {
            if (oldTags.contains(tag.get_id()))
                liv_tag.getSelectionModel().select(tag.getName());
        }

    }

    void initBadgeListView(TextField textField, String oldTags, ListView liv_badge) {
        if (badges == null) {
            BadgeService.getAllBadges(response -> badges = BadgeService.parseBadges(response), null);
        }
        ObservableList<String> listViewData = FXCollections.observableArrayList();
        for (Badge badge : badges) {
            listViewData.add(badge.getName());
        }
        FilteredList<String> filteredData = new FilteredList<>(listViewData, s -> true);

        setFilterTxtField(filteredData, textField.textProperty(), textField.getText());

        liv_badge.setItems(filteredData);
        liv_badge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (Badge badge : badges) {
            if (oldTags.contains(badge.get_id()))
                liv_badge.getSelectionModel().select(badge.getName());
        }

    }

    private void setFilterTxtField(FilteredList<String> filteredData, StringProperty stringProperty, String text) {
        setTextFieldFilterForListView(filteredData, stringProperty, text);
    }

    public static void setTextFieldFilterForListView(FilteredList<String> filteredData, StringProperty stringProperty, String text) {
        stringProperty.addListener(obs -> {
            String filter = text;
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });
    }
}
