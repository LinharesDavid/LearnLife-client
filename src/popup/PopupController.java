package popup;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PopupController {

    public Button btn_ok;
    public Button btn_nok;
    public Label lbl_message;

    public void setText(String message, String OK, String NOK) {
        if (NOK == null) {
            btn_nok.setVisible(false);
        } else {
            btn_nok.setText(NOK);
        }
        lbl_message.setText(message);
        btn_ok.setText(OK);
    }

    public void setText(String message, String OK) {
        setText(message, OK, null);
    }
}
