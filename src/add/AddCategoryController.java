package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import popup.PopupView;
import service.CategoryService;

import static utils.Constants.*;

public class AddCategoryController extends BaseAddController {


    @FXML
    private TextField txf_category_name;
    @FXML
    private Button btn_cancel;

    public void init(Scene scene) {
        btn_cancel.setOnAction(this::onBtnCancelClick);
    }

    @FXML
    private void onBtnValidateClick(ActionEvent event) {
        String name = txf_category_name.getText();
        if (name.isEmpty()) {
            PopupView popupView = new PopupView();
            popupView.start(ERR, ERR_FILL_FIELDS, OK);
            popupView.addOnBtnOkListener(null);
        } else {
            CategoryService.addCategory(
                    name,
                    response -> {
                        view.onAddSuccess(MODEL_NAME_CATEGORY);
                        view.closeWindow();
                    },
                    (errCode, res) -> showErorPopup()
            );
        }
    }
}
