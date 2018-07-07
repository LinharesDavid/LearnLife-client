package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static utils.Constants.*;


public class LoginController {
    public TextField txf_login;
    public TextField txf_password;
    private ConnexionListener listener;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        String login = txf_login.getText();
        String pwd = txf_password.getText();
        try {
            RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_LOGIN)
                .addRequestBodyParameter("email", login)
                .addRequestBodyParameter("password", pwd)
                .addRequestProperty("Content-Type", "application/json")
                .setRequestMethod("POST")
                .setOnResponseFailListener((errCode, res) -> listener.onConnexionFailed(res))
                .setOnResponseSuccessListener(response -> {
                    if (response.contains("token")) {
                        listener.onConnexionSuccess();
                    } else {
                        listener.onConnexionFailed("unknown error");
                    }
                })
                .build();

        } catch (Exception e) {
            e.printStackTrace();
            listener.onConnexionFailed("unknown error");
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
