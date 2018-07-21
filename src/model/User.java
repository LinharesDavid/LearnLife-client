package model;

import utils.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static utils.Constants.*;

public class User {
    private String _id;
    private String email;
    private String firstname;
    private String lastname;
    private Integer age;
    private ArrayList<String> badges;
    private Integer points;
    private ArrayList<String> tags;
    private String thumbnail;
    private Integer role;
    private String rawJson;

    public User(String _id, String email, String firstname, String lastname) {
        this._id = _id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String _id, String email, String firstname, String lastname, int age, ArrayList<String> badges, int points, ArrayList<String> tags, String thumbnail, int role) {
        this._id = _id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.badges = badges;
        this.points = points;
        this.tags = tags;
        this.thumbnail = thumbnail;
        this.role = role;
    }

    public User(String str) {
        this._id = str;
    }

    @SuppressWarnings("unchecked cast")
    public User(HashMap<String, Object> map) {
        if (map != null) {
            this._id = map.get(KEY_GENERIC_ID) == null ? "-1" : (String) map.get(KEY_GENERIC_ID);
            this.email = map.get(KEY_USER_EMAIL) == null ? "-1" : (String) map.get(KEY_USER_EMAIL);
            this.firstname = map.get(KEY_USER_FIRSTNAME) == null ? "-1" : (String) map.get(KEY_USER_FIRSTNAME);
            this.lastname = map.get(KEY_USER_LASTNAME) == null ? "-1" : (String) map.get(KEY_USER_LASTNAME);
            this.age = map.get(KEY_USER_AGE) == null ? -1 : (Integer) map.get(KEY_USER_AGE);
            this.badges = new ArrayList<>();
            this.badges = map.get(KEY_USER_BADGES) == null ? (ArrayList<String>) Collections.singletonList("-1") : (ArrayList) map.get(KEY_USER_BADGES);
            this.points = map.get(KEY_USER_POINTS) == null ? -1 : (Integer) map.get(KEY_USER_POINTS);
            this.tags = new ArrayList<>();
            this.tags = map.get(KEY_USER_TAGS) == null ? (ArrayList<String>) Collections.singletonList("-1") : (ArrayList) map.get(KEY_USER_TAGS);
            this.thumbnail = map.get(KEY_USER_IMAGE) == null ? "-1" : (String) map.get(KEY_USER_IMAGE);
            this.role = map.get(KEY_USER_ROLE) == null ? -1 : (Integer) map.get(KEY_USER_ROLE);
            this.rawJson = map.get(KEY_GENERIC_RAW_JSON) == null ? "-1" : (String) map.get(KEY_GENERIC_RAW_JSON);
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList getBadges() {
        return badges;
    }

    public void setBadges(ArrayList badges) {
        this.badges = badges;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList getTags() {
        return tags;
    }

    public void setTags(ArrayList tags) {
        this.tags = tags;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRawJson() {
        return rawJson;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }


    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", badges=" + badges +
                ", points=" + points +
                ", tags=" + tags +
                ", thumbnail='" + thumbnail + '\'' +
                ", role=" + role +
                ", rawJson='" + rawJson + '\'' +
                '}';
    }
}
