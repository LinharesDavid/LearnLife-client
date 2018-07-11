package main;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static utils.Constants.*;

public class MainController {
    public TableView tableView;

    private ArrayList<User> users = new ArrayList<>();

    public void addColumn() {
        TableColumn _idColumn = new TableColumn("ID");
        _idColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));

        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn firstnameColumn = new TableColumn("Firstname");
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));

        TableColumn lastnameColumn = new TableColumn("Lastname");
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        tableView.getColumns().addAll(_idColumn, emailColumn, firstnameColumn, lastnameColumn);

        tableView.getItems().addAll(users);

    }

    public void getAllUsers() {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(this::parseResponse)
                .setOnResponseFailListener((errCode, response) -> {
                    System.out.println(errCode + " " + response);
                })
                .build();
    }

    private void parseResponse(String response) {
        JSONArray jsonArray = new JSONArray(response);
        int i = 0;
        while (true) {
            JSONObject jsonObject;
            try {
                jsonObject = jsonArray.getJSONObject(i++);
            } catch (JSONException e) {
                break;
            }
            User user = new User(
                    jsonObject.getString("_id"),
                    jsonObject.getString("email"),
                    jsonObject.getString("firstname"),
                    jsonObject.getString("lastname"));

            System.out.println(user.toString());

            users.add(user);
        }
        addColumn();
    }
}
