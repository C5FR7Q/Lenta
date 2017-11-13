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

	@Override
	public
	boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Data data = (Data) o;

		if (link != null ? !link.equals(data.link) : data.link != null) {
			return false;
		}
		if (title != null ? !title.equals(data.title) : data.title != null) {
			return false;
		}
		if (description != null ? !description.equals(data.description) : data.description != null) {
			return false;
		}
		if (pubDate != null ? !pubDate.equals(data.pubDate) : data.pubDate != null) {
			return false;
		}
		if (picLink != null ? !picLink.equals(data.picLink) : data.picLink != null) {
			return false;
		}
		return category != null ? category.equals(data.category) : data.category == null;
	}

	@Override
	public
	int hashCode() {
		int result = link != null ? link.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
		result = 31 * result + (picLink != null ? picLink.hashCode() : 0);
		result = 31 * result + (category != null ? category.hashCode() : 0);
		return result;
	}
}
