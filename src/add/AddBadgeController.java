package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import popup.PopupView;
import service.BadgeService;

import static utils.Constants.MODEL_NAME_BADGE;

public class AddBadgeController extends AddController {

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField txf_badge_points;

    public void init(Scene scene) {
        btn_cancel.setOnAction(this::onBtnCancelClick);
        setTxfNumbersOnly(txf_badge_points);
    }

    @FXML
    private void onBtnValidateClick(ActionEvent event) {
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
}
