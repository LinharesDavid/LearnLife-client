package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String _id;
    private String email;
    private String firstname;
    private String lastname;
    private Integer age;
    private ArrayList<Badge> badges;
    private Integer points;
    private ArrayList<Tag> tags;
    private String thumbnail;
    private Integer role;

    public User(String _id, String email, String firstname, String lastname) {
        this._id = _id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String _id, String email, String firstname, String lastname, int age, ArrayList<Badge> badges, int points, ArrayList<Tag> tags, String thumbnail, int role) {
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

    public User(HashMap<String, Object> map) {
        if (map != null) {
            this._id = map.get("_id") == null ? "-1" : (String) map.get("_id");
            this.email = map.get("email") == null ? "-1" : (String) map.get("email");
            this.firstname = map.get("firstname") == null ? "-1" : (String) map.get("firstname");
            this.lastname = map.get("lastname") == null ? "-1" : (String) map.get("lastname");
            this.age = map.get("age") == null ? -1 : (Integer) map.get("age");
            this.badges = new ArrayList<>();
            //this.badges =(ArrayList<Badge>) map.get("_id");
            this.points = map.get("points") == null ? -1 : (Integer) map.get("points");
            this.tags = new ArrayList<>();
            //this.tags = (ArrayList<Tag>) map.get("_id");
            this.thumbnail = map.get("thumbnail") == null ? "-1" : (String) map.get("thumbnail");
            this.role = map.get("role") == null ? -1 : (Integer) map.get("role");
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
                '}';
    }
}
