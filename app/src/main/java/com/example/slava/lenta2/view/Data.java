package com.example.slava.lenta2.view;

/**
 * Created by slava on 13.10.2017.
 */

public class Data {
    public Data(String link, String title, String description,
                String pubDate, String picLink, String category) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.picLink = picLink;
        this.category = category;
    }

    private String link;

    private String title;

    private String description;

    private String pubDate;

    private String picLink;

    private String category;

    public String getPicLink() {
        return picLink;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate.substring(0, pubDate.length() - 5);
    }

    public String getCategory() {
        return category;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
