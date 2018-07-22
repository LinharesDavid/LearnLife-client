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
import popup.PopupView;
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

    private String currentTable = MODEL_NAME_USER;

    private ArrayList<Object> values = new ArrayList<>();

    public void start() {
        refresh(MODEL_NAME_USER);

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
                    currentTable = MODEL_NAME_USER;
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().remove(0, tableView.getItems().size());
                    refresh(MODEL_NAME_USER);
                    break;
                case LEFT_PANNEL_TABLE_NAME_TAG :
                    currentTable = MODEL_NAME_TAG;
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().remove(0, tableView.getItems().size());
                    refresh(MODEL_NAME_TAG);
                    break;
                case LEFT_PANNEL_TABLE_NAME_CHALLENGE :
                    currentTable = MODEL_NAME_CHALLENGE;
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().remove(0, tableView.getItems().size());
                    refresh(MODEL_NAME_CHALLENGE);
                    break;
                case LEFT_PANNEL_TABLE_NAME_CATEGORY :
                    currentTable = MODEL_NAME_CATEGORY;
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().remove(0, tableView.getItems().size());
                    refresh(MODEL_NAME_CATEGORY);
                    break;
                case LEFT_PANNEL_TABLE_NAME_BADGE :
                    currentTable = MODEL_NAME_BADGE;
                    tableView.getItems().remove(0, tableView.getItems().size());
                    tableView.getColumns().remove(0, tableView.getColumns().size());
                    refresh(MODEL_NAME_BADGE);
                    break;
            }
        });

    }

    private <T> void addColumn(Class<T> objType) {
        tableView.getColumns().clear();
        try {
            Class cls = Class.forName(values.get(0).getClass().getName());
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().toLowerCase().equals(KEY_GENERIC_RAW_JSON)) continue;
                if (field.getName().toLowerCase().equals(KEY_BADGE_IMAGE)) continue;
                TableColumn col = new TableColumn(field.getName());
                col.setMaxWidth(400);
                col.setCellValueFactory(new PropertyValueFactory<>
                        (field.getName()));
                tableView.getColumns().add(col);
            }

            final ContextMenu tableContextMenu = new ContextMenu();
            final MenuItem deleteSelectedMenuItem = new MenuItem(DELETE_SELECTED_ITEM);
            final MenuItem addElement = new MenuItem(ADD_ITEM);
            final MenuItem viewJson = new MenuItem("View JSON");
            deleteSelectedMenuItem.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
            viewJson.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
            deleteSelectedMenuItem.setOnAction(event -> {
                T selectedRow = (T) tableView.getSelectionModel().getSelectedItem();
                if (selectedRow instanceof User) {
                    UserService.deleteUser(
                            ((User) selectedRow).get_id(),
                            response -> refresh(MODEL_NAME_USER),
                            null
                    );
                } else if (selectedRow instanceof Tag) {
                    TagService.deleteTag(
                            ((Tag) selectedRow).get_id(),
                            response -> refresh(MODEL_NAME_TAG),
                            null
                    );
                } else if (selectedRow instanceof Badge) {
                    BadgeService.deleteBadge(
                            ((Badge) selectedRow).get_id(),
                            response -> refresh(MODEL_NAME_BADGE),
                            null
                    );
                } else if (selectedRow instanceof Challenge) {
                    ChallengeService.deleteChallenge(
                            ((Challenge) selectedRow).get_id(),
                            response -> refresh(MODEL_NAME_CHALLENGE),
                            null
                    );
                } else if (selectedRow instanceof Category) {
                    CategoryService.deleteCategory(
                            ((Category) selectedRow).get_id(),
                            response -> refresh(MODEL_NAME_CATEGORY),
                            null
                    );
                }

            });

            addElement.setOnAction(event -> {
                AddView addView = new AddView();
                addView.start(currentTable);
                addView.setOnCloseAddWindowListener(this::refresh);
            });

            viewJson.setOnAction(event -> {
                T selectedRow = (T) tableView.getSelectionModel().getSelectedItem();
                EditView editView = new EditView();
                if (selectedRow instanceof User) {
                    editView.start(((User) selectedRow).getRawJson());

                } else if (selectedRow instanceof Tag) {
                    editView.start(((Tag) selectedRow).getRawJson());

                } else if (selectedRow instanceof Badge) {
                    editView.start(((Badge) selectedRow).getRawJson());

                } else if (selectedRow instanceof Challenge) {
                    editView.start(((Challenge) selectedRow).getRawJson());

                } else if (selectedRow instanceof Category) {
                    editView.start(((Category) selectedRow).getRawJson());
                }
            });

            tableContextMenu.getItems().addAll(deleteSelectedMenuItem, addElement, viewJson);

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
                    EditView editView = new EditView();
                    if (selectedRow instanceof User) {
                        editView.start(MODEL_NAME_USER, ((User) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(MODEL_NAME_USER));

                    } else if (selectedRow instanceof Tag) {
                        editView.start(MODEL_NAME_TAG, ((Tag) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(MODEL_NAME_TAG));

                    } else if (selectedRow instanceof Badge) {
                        editView.start(MODEL_NAME_BADGE, ((Badge) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(MODEL_NAME_BADGE));

                    } else if (selectedRow instanceof Challenge) {
                        editView.start(MODEL_NAME_CHALLENGE, ((Challenge) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(MODEL_NAME_CHALLENGE));

                    } else if (selectedRow instanceof Category) {
                        editView.start(MODEL_NAME_CATEGORY, ((Category) selectedRow).getRawJson());
                        editView.setOnCloseEditWindowListener(() -> refresh(MODEL_NAME_CATEGORY));
                    }
                }
            });

            tableView.getItems().addAll(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private <T> void parseResponse(String response, Class<T> objType) {
        if (response.equals("[]")) return;
        ArrayList<UserVote> userVotes = new ArrayList<>();
        if (objType.getSimpleName().toLowerCase().equals(MODEL_NAME_CHALLENGE)) {
            OtherService.getAllUserVote(
                    res -> {
                        JSONArray jsonArrayUV = new JSONArray(res);
                        for (int i = 0; i < jsonArrayUV.length(); i++) {
                            JSONObject jsonObjectUV = jsonArrayUV.getJSONObject(i);
                            UserVote userVote = new UserVote(
                                    jsonObjectUV.getString(KEY_GENERIC_ID),
                                    jsonObjectUV.getString(KEY_USER_VOTE_USER),
                                    jsonObjectUV.getString(KEY_USER_VOTE_CHALLENGE)
                            );

                            userVotes.add(userVote);
                        }
                    },
                    (errCode, res) -> {
                        PopupView popupView = new PopupView();
                        popupView.start(ERR, ERR_GETTING_USER_VOTES, OK);
                        popupView.addOnBtnOkListener(null);
                    }
            );
        }
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
                if (objType.getSimpleName().toLowerCase().equals(MODEL_NAME_CHALLENGE)) {

                    int votes = 0;
                    String id = jsonObject.getString(KEY_GENERIC_ID);
                    for (UserVote userVote : userVotes) {
                        if (userVote.getUser().equals(id)){
                            votes++;
                        }
                    }
                    jsonObject.put(KEY_CHALLENGE_VOTE, votes);
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
                map.put(KEY_GENERIC_RAW_JSON, jsonObject.toString());

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
        tableView.getColumns().remove(0, tableView.getItems().size());
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
}