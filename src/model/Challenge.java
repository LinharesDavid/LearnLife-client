package model;

import java.util.ArrayList;

public class Challenge {
    private String name;
    private String details;
    private String imageUrl;
    private int pointsGiven;
    private String startDate;
    private String endDate;
    private int duration;
    private ArrayList<Tag> tags;
    private ArrayList<Badge> badges;
    private User user;
    private int verified;

    public Challenge(String name, String details, String imageUrl, int pointsGiven, String startDate, String endDate, int duration, ArrayList<Tag> tags, ArrayList<Badge> badges, User user, int verified) {
        this.name = name;
        this.details = details;
        this.imageUrl = imageUrl;
        this.pointsGiven = pointsGiven;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.tags = tags;
        this.badges = badges;
        this.user = user;
        this.verified = verified;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Badge> getBadges() {
        return badges;
    }

    public void setBadges(ArrayList<Badge> badges) {
        this.badges = badges;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pointsGiven=" + pointsGiven +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", duration=" + duration +
                ", tags=" + tags +
                ", badges=" + badges +
                ", user=" + user +
                ", verified=" + verified +
                '}';
    }
}
