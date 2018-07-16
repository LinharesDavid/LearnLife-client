package model;

import java.util.HashMap;

import static utils.Constants.*;

public class Badge {

    private String _id;
    private String name;
    private String description;
    private String thumbnail;
    private int achievementPoints;
    private String rawJson;

    public Badge(String name, String description, String thumbnail, int achievementPoints) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.achievementPoints = achievementPoints;
    }

    public Badge(HashMap<String, Object> map) {
        this._id = map.get(JSON_ENTRY_KEY_ID) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_ID);
        this.name =  map.get(JSON_ENTRY_KEY_BADGE_NAME) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_BADGE_NAME);
        this.description = map.get(JSON_ENTRY_KEY_BADGE_DESCRIPTION) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_BADGE_DESCRIPTION);
        this.thumbnail = map.get(JSON_ENTRY_KEY_BADGE_THUMBNAIL) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_BADGE_THUMBNAIL);
        this.achievementPoints = map.get(JSON_ENTRY_KEY_BADGE_ACHIEVEMENT_POINTS) == null ? -1 : (int) map.get(JSON_ENTRY_KEY_BADGE_ACHIEVEMENT_POINTS);
        this.rawJson = map.get(JSON_ENTRY_KEY_RAW_JSON) == null ? "-1" : (String) map.get(JSON_ENTRY_KEY_RAW_JSON);
    }

    public String get_id() {
        return _id;
    }

    public Badge set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public String getRawJson() {
        return rawJson;
    }

    public Badge setRawJson(String rawJson) {
        this.rawJson = rawJson;
        return this;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", achievementPoints=" + achievementPoints +
                '}';
    }
}
