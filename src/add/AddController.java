package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import popup.PopupView;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;

public class AddController {

    public PasswordField pwf_password;
    public TextField txf_email;
    public TextField txf_firstname;
    public TextField txf_lastname;
    public ComboBox cmb_role;

    private AddView view;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        if (pwf_password.getText().isEmpty() || txf_email.getText().isEmpty() || txf_firstname.getText().isEmpty() || txf_lastname.getText().isEmpty()) {
            PopupView popupView =new PopupView();
            popupView.start("Erreur", "Tous les champs doivent\netre remplis", "OK");
            popupView.addOnBtnOkListener(null);
        } else {
            int role = cmb_role.getValue().equals("User") ? 0 : 1;
            addUser(txf_email.getText(), pwf_password.getText(), txf_firstname.getText(), txf_lastname.getText(), role);
        }
    }

    @FXML
    private void onBtnCancelClick(ActionEvent event) {
        view.closeWindow();
    }

    public void setView(AddView view) {
        this.view = view;
    }

    private void addUser(String email, String pwd, String firstname, String lastname, int role){
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS)
                .setRequestMethod("POST")
                .addRequestBodyParameter(BODY_PARAMETER_EMAIL, email)
                .addRequestBodyParameter(BODY_PARAMETER_PASSWORD, pwd)
                .addRequestBodyParameter(BODY_PARAMETER_FIRSTNAME, firstname)
                .addRequestBodyParameter(BODY_PARAMETER_LASTNAME, lastname)
                .addRequestBodyParameter(BODY_PARAMETER_ROLE, role)
                .setOnResponseSuccessListener(response -> {
                    view.onAddSuccess();
                    view.closeWindow();
                })
                .setOnResponseFailListener((errCode, res) -> {
                    PopupView popupView = new PopupView();
                    popupView.start("Error", "WOULA ca marche pas", "OK");
                    popupView.addOnBtnOkListener(null);
                })
                .build();
    }

    interface OnAddSuccessListener {
        void onAddSuccess();
    }
}
