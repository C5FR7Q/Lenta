package com.example.slava.lenta2.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by slava on 29.08.2017.
 */

@Root(name = "item", strict = false)
public class Data {
    @Element(name = "link")
    private String link;

    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @Element(name = "pubDate")
    private String pubDate;

    @Element(name = "category")
    private String category;

    @Path(value = "enclosure")
    @Attribute(name = "url")
    private String picLink;

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
        return pubDate;
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
