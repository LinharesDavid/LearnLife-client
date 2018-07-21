package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import utils.Session;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;


public class LoginController {
    public TextField txf_login;
    public TextField txf_password;
    public TextField txf_url;
    private ConnexionListener listener;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        String url = txf_url.getText();
        if (!url.isEmpty()) {
            BASE_URL = txf_url.getText();
        }
        BASE_URL = txf_url.getText();
        String login = txf_login.getText();
        String pwd = txf_password.getText();
        try {
            RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_LOGIN)
                .addRequestBodyParameter(KEY_USER_EMAIL, login)
                .addRequestBodyParameter(KEY_USER_PASSWORD, pwd)
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setRequestMethod("POST")
                .setOnResponseFailListener((errCode, res) -> listener.onConnexionFailed(res))
                .setOnResponseSuccessListener(response -> {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getJSONObject(MODEL_NAME_USER).getInt(KEY_USER_ROLE) == 1) {
                        Session.getInstance().setToken(jsonObject.getString(RESPONSE_FIELD_TOKEN));
                        listener.onConnexionSuccess();
                    } else {
                        listener.onConnexionFailed("user not an admin");
                    }
                })
                .build();

        } catch (Exception e) {
            e.printStackTrace();
            listener.onConnexionFailed(ERR_UNKNOWN);
        }
    }

    public void setListener(ConnexionListener listener) {
        this.listener = listener;
    }

    interface ConnexionListener {
        void onConnexionSuccess();
        void onConnexionFailed(String err);
    }
}
