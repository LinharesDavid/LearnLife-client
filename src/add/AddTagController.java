package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import model.Category;
import model.Tag;
import org.json.JSONArray;
import popup.PopupView;
import service.TagService;

import static utils.Constants.MODEL_NAME_TAG;

public class AddTagController extends AddController {

    @FXML
    private ComboBox cmb_tag_category;

    public void init(Scene scene) {
        initTagListView();
        initCategoryCmb();
    }

    @FXML
    private void onBtnValidateClick(ActionEvent event) {
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

            TagService.addTag(
                    newTagName,
                    tagsArray,
                    selectedCategoryId,
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
    }


    @FXML
    private void onBtnUnselectAllClick(ActionEvent event) {
        liv_tag.getSelectionModel().clearSelection();
    }
}
