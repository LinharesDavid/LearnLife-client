package model;

public class Badge {

    private String name;
    private String description;
    private String thumbnail;
    private int achievementPoints;

    public Badge(String name, String description, String thumbnail, int achievementPoints) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.achievementPoints = achievementPoints;
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
