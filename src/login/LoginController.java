package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;
import popup.PopupView;
import utils.Session;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;


public class LoginController {
    public TextField txf_login;
    public TextField txf_password;
    public Button btn_validate;

    private Stage stage;

    public void start() {

        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login_window.fxml"));
            GridPane rootLayout = loader.load();
            loader.setController(this);
            Scene scene = new Scene(rootLayout, 300, 200);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            btn_validate = (Button) scene.lookup("#btn_validate");
            txf_login = (TextField) scene.lookup("#txf_login");
            txf_password = (PasswordField) scene.lookup("#txf_password");

            btn_validate.setOnAction(this::onBtnValidateClick);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onConnexionSuccess() {
        if (stage == null)
            System.out.println("WTF");
        else
            stage.hide();
    }

    private void onConnexionFailed(String err) {
        PopupView popupView = new PopupView();
        popupView.start("Error", err, "OK");
        popupView.addOnBtnOkListener(event -> {
            System.out.println("clicked bitch suce ma cite sous la pluie stp");
        });
    }

    private void onBtnValidateClick(ActionEvent event){
        String login = txf_login.getText();
        String pwd = txf_password.getText();
        try {
            RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_LOGIN)
                .addRequestBodyParameter(BODY_PARAMETER_EMAIL, login)
                .addRequestBodyParameter(BODY_PARAMETER_PASSWORD, pwd)
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setRequestMethod("POST")
                .setOnResponseFailListener((errCode, res) -> this.onConnexionFailed(res))
                .setOnResponseSuccessListener(response -> {
                    if (response.contains(RESPONSE_FIELD_TOKEN)) {
                        JSONObject jsonObject = new JSONObject(response);
                        Session.getInstance().setToken(jsonObject.getString(RESPONSE_FIELD_TOKEN));
                        this.onConnexionSuccess();
                    } else {
                        this.onConnexionFailed(ERR_UNKNOWN);
                    }
                })
                .build();

        } catch (Exception e) {
            e.printStackTrace();
            this.onConnexionFailed(ERR_UNKNOWN);
        }
    }

}
