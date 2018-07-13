package main;

import add.AddView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.Category;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Log;
import utils.request.builder.RequestBuilder;

import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static utils.Constants.*;

@SuppressWarnings("unchecked")
public class MainController {
    public TableView tableView;
    public ListView listView;

    private ArrayList<Object> values = new ArrayList<>();

    public void start() {
        getAllUsers();

        ObservableList<String> modelListViewItems = FXCollections.observableArrayList ("User", "Tag", "Challenge", "Category", "Badge");
        listView.setItems(modelListViewItems);
        listView.setPrefWidth(100);
        listView.setPrefHeight(70);
        listView.setOrientation(Orientation.VERTICAL);
        listView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (ov, oldvalue, newvalue) -> {
            switch (newvalue) {
                case "User" :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    getAllUsers();
                    break;
                case "Tag" :
                    break;
                case "Challenge" :
                    break;
                case "Category" :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    getAllCategory();
                    break;
                case "Badge" :
                    break;
            }
        });

    }

    private <T> void addColumn(Class<T> objType) {
        tableView.getColumns().clear();
        try {
            // Add the columns with the right title
            Class cls = Class.forName(values.get(0).getClass().getName());
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                TableColumn col = new TableColumn(field.getName());
                col.setCellValueFactory(new PropertyValueFactory<>
                        (field.getName()));
                tableView.getColumns().add(col);
            }

            // Manage the context menu
            final ContextMenu tableContextMenu = new ContextMenu();
            final MenuItem deleteSelectedMenuItem = new MenuItem(DELETE_SELECTED_ITEM);
            final MenuItem addElement = new MenuItem(ADD_ITEM);
            deleteSelectedMenuItem.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
            deleteSelectedMenuItem.setOnAction(event -> {
                //final List<T> selectedRows = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
                T selectedRow = (T) tableView.getSelectionModel().getSelectedItem();
                if (selectedRow instanceof User) {
                    deleteUser(((User) selectedRow).get_id());
                }
            });
            addElement.setOnAction(event -> {
                AddView addView = new AddView();
                addView.start(objType.getSimpleName());
                addView.setOnCloseLoginListener(this::getAllUsers);
            });
            tableContextMenu.getItems().addAll(deleteSelectedMenuItem, addElement);

            // Manage when to show or hide the context menu
            tableView.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    tableContextMenu.show(tableView, event.getScreenX(), event.getScreenY());
                }
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (tableContextMenu.isShowing())
                        tableContextMenu.hide();
                }
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Log.d("DOUBLE CLICKED MODAFAKA" + tableView.getSelectionModel().getSelectedItem().toString());
                }
            });

            //Add all the rows to the tableview
            tableView.getItems().addAll(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private <T> void parseResponse(String response, Class<T> objType) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            int i = 0;
            while (true) {
                JSONObject jsonObject;
                try {
                    jsonObject = jsonArray.getJSONObject(i++);
                } catch (JSONException e) {
                    break;
                }

                Set<String> ss = jsonObject.keySet();
                Object[] ssarray = ss.toArray();

                HashMap<String, Object> map = new HashMap<>();

                for (Object o : ssarray) {
                    map.put(o.toString(), jsonObject.get(o.toString()));
                }

                T o = objType.getConstructor(HashMap.class).newInstance(map);

                values.add(o);
            }
            addColumn(objType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*************************************
     * All the dirty work
     *************************************/

    //Users
    private void getAllUsers() {
        values.clear();
        tableView.getItems().remove(0, tableView.getItems().size());
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(response -> parseResponse(response, User.class))
                .setOnResponseFailListener((errCode, response) -> Log.d(String.valueOf(errCode), response))
                .build();
    }

    private void deleteUser(String id) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_USERS + id)
                .setRequestMethod("DELETE")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(Log::d)
                .setOnResponseFailListener(((errCode, res) -> Log.d(String.valueOf(errCode), res)))
                .build();

        getAllUsers();
    }

    //Categories
    private void getAllCategory() {
        values.clear();
        tableView.getItems().remove(0, tableView.getItems().size());
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_CATEGORY)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(response -> parseResponse(response, Category.class))
                .setOnResponseFailListener((errCode, response) -> System.out.println(errCode + " " + response))
                .build();
    }
}
