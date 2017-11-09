package com.example.slava.lenta2.view;

/**
 * Created by slava on 13.10.2017.
 */

public
class Data
{
	public
	Data(final String link, final String title, final String description,
	     final String pubDate, final String picLink, final String category) {
		this.link = link;
		this.title = title;
		this.description = description;
		this.pubDate = pubDate;
		this.picLink = picLink;
		this.category = category;
	}

	private String link;

	private String title;

	private final String description;

	private final String pubDate;

	private final String picLink;

	private final String category;

	public
	String getPicLink() {
		return picLink;
	}

	public
	String getLink() {
		return link;
	}

	public
	String getTitle() {
		return title;
	}

	public
	String getDescription() {
		return description;
	}

	public
	String getPubDate() {
		return pubDate.substring(0, pubDate.length() - 5);
	}

	public
	String getCategory() {
		return category;
	}

	public
	void setLink(final String link) {
		this.link = link;
	}

	public
	void setTitle(final String title) {
		this.title = title;
	}

}
