package model;

import java.util.HashMap;

import static utils.Constants.*;

public class Category {

    private String _id;
    private String name;
    private String rawJson;

    public Category(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public Category(HashMap<String, Object> map) {
        this._id = map.get(JSON_ENTRY_KEY_ID) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_ID);
        this.name = map.get(JSON_ENTRY_KEY_BADGE_NAME) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_CATEGORY_NAME);
        this.rawJson = map.get(JSON_ENTRY_KEY_RAW_JSON) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_RAW_JSON);
    }

    public String get_id() {
        return _id;
    }

    public Category set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawJson() {
        return rawJson;
    }

    public Category setRawJson(String rawJson) {
        this.rawJson = rawJson;
        return this;
    }

    @Override
    public String toString() {
        return "Category{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", rawJson='" + rawJson + '\'' +
                '}';
    }
}
