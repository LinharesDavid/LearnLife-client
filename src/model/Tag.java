package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static utils.Constants.*;

public class Tag {

    private String _id;
    private String name;
    private ArrayList<String> tagAssociated;
    private String category;
    private String rawJson;

    public Tag() {}

    public Tag(String name, ArrayList<String> tagAssociated, String category) {
        this.name = name;
        this.tagAssociated = tagAssociated;
        this.category = category;
    }

    @SuppressWarnings("unchecked cast")
    public Tag(HashMap<String, Object> map) {
        if (map != null) {
            this._id = map.get(KEY_GENERIC_ID) == null ? "-1" : (String) map.get(KEY_GENERIC_ID);
            this.name = map.get(KEY_TAG_NAME) == null ? "-1" : (String) map.get(KEY_TAG_NAME);
            this.tagAssociated = new ArrayList<>();
            this.tagAssociated = map.get(KEY_TAG_TAG_ASSOSCIATED) == null ? (ArrayList<String>) Collections.singletonList("-1") : (ArrayList) map.get(KEY_TAG_TAG_ASSOSCIATED);
            this.category = map.get(KEY_TAG_CATEGORY) == null ? "-1" : (String) map.get(KEY_TAG_CATEGORY);
            this.rawJson = map.get(KEY_GENERIC_RAW_JSON) == null ? "-1" : (String) map.get(KEY_GENERIC_RAW_JSON);
        }
    }

    public String get_id() {
        return _id;
    }

    public Tag set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTagAssociated() {
        return tagAssociated;
    }

    public void setTagAssociated(ArrayList<String> tagAssociated) {
        this.tagAssociated = tagAssociated;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRawJson() {
        return rawJson;
    }

    public Tag setRawJson(String rawJson) {
        this.rawJson = rawJson;
        return this;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", tagAssociated=" + tagAssociated +
                ", category='" + category + '\'' +
                ", rawJson='" + rawJson + '\'' +
                '}';
    }

    public boolean contains(String name) {
        return this.name.contains(name);
    }
}
