package com.infosys.newsletter.dto;

import org.modelmapper.ModelMapper;

public class NewsletterDTO {

	private String title;
	private String image;
	private String content;
	private String created;

	public NewsletterDTO() {

	}

	public NewsletterDTO(String title, String image, String content) {
		this.title = title;
		this.image = image;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "NewsletterDTO {" + "title='" + title + '\'' + ", image='" + image + '\'' + ", content='" + content
				+ '\'' + '}';
	}

}
