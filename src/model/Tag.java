package model;

import java.util.ArrayList;

public class Tag {

    private String name;
    private ArrayList<Tag> tagAssociated;
    private Category category;

    public Tag(String name, ArrayList<Tag> tagAssociated, Category category) {
        this.name = name;
        this.tagAssociated = tagAssociated;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Tag> getTagAssociated() {
        return tagAssociated;
    }

    public void setTagAssociated(ArrayList<Tag> tagAssociated) {
        this.tagAssociated = tagAssociated;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", tagAssociated=" + tagAssociated +
                ", category=" + category +
                '}';
    }
}
