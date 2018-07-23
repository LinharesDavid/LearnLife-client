package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static utils.Constants.*;

public class Challenge {
    private String _id;
    private String name;
    private String details;
    private String imageUrl;
    private int pointsGiven;
    //private String startDate;
    //private String endDate;
    private int duration;
    private ArrayList<String> tags;
    private String badge;
    private String user;
    private int verified;
    private String rawJson;
    private int votes;

    public Challenge(String name, String details, String imageUrl, int pointsGiven, String startDate, String endDate, int duration, ArrayList<String> tags, String badge, String user, int verified) {
        this.name = name;
        this.details = details;
        this.imageUrl = imageUrl;
        this.pointsGiven = pointsGiven;
        //this.startDate = startDate;
        //this.endDate = endDate;
        this.duration = duration;
        this.tags = tags;
        this.badge = badge;
        this.user = user;
        this.verified = verified;
    }

    @SuppressWarnings("unchecked cast")
    public Challenge(HashMap<String, Object> map) {
        if (map != null) {
            this._id = map.get(KEY_GENERIC_ID) == null ? "-1" : (String) map.get(KEY_GENERIC_ID);
            this.name = map.get(KEY_CHALLENGE_NAME) == null ? "-1" : (String) map.get(KEY_CHALLENGE_NAME);
            this.details = map.get(KEY_CHALLENGE_DETAILS) == null ? "-1" : (String) map.get(KEY_CHALLENGE_DETAILS);
            this.imageUrl = map.get(KEY_CHALLENGE_IMAGE) == null ? "-1" : (String) map.get(KEY_CHALLENGE_IMAGE);
            this.pointsGiven = map.get(KEY_CHALLENGE_POINTS_GIVEN) == null ? -1: (int) map.get(KEY_CHALLENGE_POINTS_GIVEN);
            //this.startDate = map.get(KEY_CHALLENGE_START_DATE) == null ? "-1" : (String) map.get(KEY_CHALLENGE_START_DATE);
            //this.endDate = map.get(KEY_CHALLENGE_END_DATE) == null ? "-1" : (String) map.get(KEY_CHALLENGE_END_DATE);
            this.duration = map.get(KEY_CHALLENGE_DURATION) == null ? -1 : (int) map.get(KEY_CHALLENGE_DURATION);
            this.tags = new ArrayList<>();
            this.tags = map.get(KEY_CHALLENGE_TAGS) == null ? (ArrayList<String>) Collections.singletonList("-1") : (ArrayList) map.get(KEY_CHALLENGE_TAGS);
            this.badge = map.get(KEY_CHALLENGE_BADGE) == null ? "-1" : (String) map.get(KEY_CHALLENGE_BADGE);
            this.user = map.get(KEY_CHALLENGE_USER) == null ? "-1" : (String) map.get(KEY_CHALLENGE_USER);
            this.verified = map.get(KEY_CHALLENGE_VERIFIED) == null ? -1 : (int) map.get(KEY_CHALLENGE_VERIFIED);
            this.rawJson = map.get(KEY_GENERIC_RAW_JSON) == null ? "-1" : (String) map.get(KEY_GENERIC_RAW_JSON);
            this.votes = map.get(KEY_CHALLENGE_VOTE) == null ? -1 : (int) map.get(KEY_CHALLENGE_VOTE);
        }
    }

    public String get_id() {
        return _id;
    }

    public Challenge set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(int pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    //public String getStartDate() {
    //    return startDate;
    //}
//
    //public void setStartDate(String startDate) {
    //    this.startDate = startDate;
    //}
//
    //public String getEndDate() {
    //    return endDate;
    //}

    //public void setEndDate(String endDate) {
    //    this.endDate = endDate;
    //}

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getRawJson() {
        return rawJson;
    }

    public Challenge setRawJson(String rawJson) {
        this.rawJson = rawJson;
        return this;
    }

    public int getVotes() {
        return votes;
    }

    public Challenge setVotes(int votes) {
        this.votes = votes;
        return this;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pointsGiven=" + pointsGiven +
                //", startDate='" + startDate + '\'' +
                //", endDate='" + endDate + '\'' +
                ", duration=" + duration +
                ", tags=" + tags +
                ", badge=" + badge +
                ", user=" + user +
                ", verified=" + verified +
                ", votes=" + votes +
                '}';
    }
}
