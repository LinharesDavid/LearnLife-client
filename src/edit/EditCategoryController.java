package edit;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.JSONObject;


import static utils.Constants.*;

public class EditCategoryController extends EditController {

    private JSONObject oldCategory;

    private TextField txf_category_name;
    private Button btn_validate;
    private Button btn_cancel;

    public void init(Scene scene, String json) {
        txf_category_name = (TextField) scene.lookup("#txf_category_name");
        btn_validate = (Button) scene.lookup("#btn_validate");
        btn_cancel = (Button) scene.lookup("#btn_cancel");

        oldCategory = new JSONObject(json);

        txf_category_name.setText(oldCategory.getString(JSON_ENTRY_KEY_CATEGORY_NAME));
        btn_validate.setOnAction(this::onBtnValidateClick);
        btn_cancel.setOnAction(this::onBtnCancelClick);
    }

    private void onBtnValidateClick(ActionEvent event){
        JSONObject newCategory = new JSONObject();
        newCategory.put(JSON_ENTRY_KEY_ID, oldCategory.getString(JSON_ENTRY_KEY_ID));
        newCategory.put(JSON_ENTRY_KEY_CATEGORY_NAME, txf_category_name.getText());

        editModel(newCategory.toString(), MODEL_NAME_CATEGORY);
    }

}
