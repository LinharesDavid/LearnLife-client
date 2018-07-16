package main;

import add.AddView;
import edit.EditView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.*;

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
        refresh(User.class.getSimpleName());

        ObservableList<String> modelListViewItems = FXCollections.observableArrayList (
                LEFT_PANNEL_TABLE_NAME_USER,
                LEFT_PANNEL_TABLE_NAME_TAG,
                LEFT_PANNEL_TABLE_NAME_CHALLENGE,
                LEFT_PANNEL_TABLE_NAME_CATEGORY,
                LEFT_PANNEL_TABLE_NAME_BADGE);

        listView.setItems(modelListViewItems);
        listView.setPrefWidth(100);
        listView.setPrefHeight(70);
        listView.setOrientation(Orientation.VERTICAL);
        listView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (ov, oldvalue, newvalue) -> {
            switch (newvalue) {
                case LEFT_PANNEL_TABLE_NAME_USER :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    refresh(User.class.getSimpleName());
                    break;
                case LEFT_PANNEL_TABLE_NAME_TAG :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    refresh(Tag.class.getSimpleName());
                    break;
                case LEFT_PANNEL_TABLE_NAME_CHALLENGE :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    refresh(Challenge.class.getSimpleName());
                    break;
                case LEFT_PANNEL_TABLE_NAME_CATEGORY :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    refresh(Category.class.getSimpleName());
                    break;
                case LEFT_PANNEL_TABLE_NAME_BADGE :
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().removeAll();
                    refresh(Badge.class.getSimpleName());
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
                if (field.getName().toLowerCase().equals(JSON_ENTRY_KEY_RAW_JSON)) continue;
                TableColumn col = new TableColumn(field.getName());
                col.setMaxWidth(400);
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
                    UserService.deleteUser(
                            ((User) selectedRow).get_id(),
                            response -> refresh(User.class.getSimpleName()),
                            null);
                }
            });
            addElement.setOnAction(event -> {
                AddView addView = new AddView();
                addView.start(objType.getSimpleName());
                addView.setOnCloseAddWindowListener(this::refresh);
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
                    T selectedRow = (T) tableView.getSelectionModel().getSelectedItem();
                    if (selectedRow instanceof User) {
                        EditView editView = new EditView();
                        editView.start(selectedRow.getClass().getSimpleName(), ((User) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(User.class.getSimpleName()));
                    }
                    if (selectedRow instanceof Tag) {
                        EditView editView = new EditView();
                        editView.start(selectedRow.getClass().getSimpleName(), ((Tag) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(Tag.class.getSimpleName()));
                    }
                }
            });

            //Add all the rows to the tableview
            tableView.getItems().addAll(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private <T> void parseResponse(String response, Class<T> objType) {
        if (response.equals("[]")) return;
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
                    Object objToPutInTheMap = jsonObject.get(o.toString());
                    ArrayList<String> jsonArrayList = new ArrayList<>();
                    if (objToPutInTheMap instanceof JSONArray) {
                        int l = ((JSONArray) objToPutInTheMap).length();
                        for (int j = 0; j < l; j++) {
                            String value = ((JSONArray) objToPutInTheMap).getString(j);
                            jsonArrayList.add(value);
                        }
                        map.put(o.toString(), jsonArrayList);
                    } else {
                        map.put(o.toString(), objToPutInTheMap);
                    }
                }
                map.put(JSON_ENTRY_KEY_RAW_JSON, jsonObject.toString());

                T o = objType.getConstructor(HashMap.class).newInstance(map);

                values.add(o);
            }
            addColumn(objType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refresh(String type) {
        values.clear();
        tableView.getItems().remove(0, tableView.getItems().size());
        tableView.getColumns().removeAll();
        switch (type.toLowerCase()) {
            case MODEL_NAME_USER:
                UserService.getAllUsers(response -> parseResponse(response, User.class), null);
                break;
            case MODEL_NAME_TAG:
                TagService.getAllTags(response -> parseResponse(response, Tag.class), null);
                break;
            case MODEL_NAME_CATEGORY:
                CategoryService.getAllCategories(response -> parseResponse(response, Category.class),
                        (errCode, response) -> System.out.println(errCode + " " + response));
                break;
            case MODEL_NAME_CHALLENGE:
                ChallengeService.getAllChallenges(response -> parseResponse(response, Challenge.class), null);
                break;
            case MODEL_NAME_BADGE:
                BadgeService.getAllBadges(response -> parseResponse(response, Badge.class), null);
                break;
        }
    }


    /*************************************
     * All the dirty work
     *************************************/

    //Users
    private void deleteUser(String id) {
        UserService.deleteUser(id, null, null);
        refresh(User.class.getSimpleName());
    }

    //Tag

    //Challenge
}
