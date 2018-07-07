package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginController {
    public TextField txf_login;
    public TextField txf_password;
    private ConnexionListener listener;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        String login = txf_login.getText();
        String pwd = txf_password.getText();
        try {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("email", login);
            jsonObjectBuilder.add("password", pwd);
            JsonObject jsonObject = jsonObjectBuilder.build();
            URL url = new URL("http://192.168.43.76:8080/auth/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            con.addRequestProperty("email", login);
            con.addRequestProperty("password", pwd);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(jsonObject.toString());
            wr.flush();
            StringBuffer content;
            if (con.getResponseCode() == 404) {
                content = readResponse(con.getErrorStream());
                listener.onConnexionFailed(content == null ? "user not found" : content.toString());
            } else {
                content = readResponse(con.getInputStream());
                if (content != null && content.toString().contains("token")) {
                    listener.onConnexionSuccess();
                } else {
                    listener.onConnexionFailed("unknown error");
                }
            }
            con.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
            listener.onConnexionFailed("unknown error");
        }
    }

    private StringBuffer readResponse(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
