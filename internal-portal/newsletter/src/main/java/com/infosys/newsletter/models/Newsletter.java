package com.infosys.newsletter.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@ApiModel(description = "Details about contact")
@Entity
public class Newsletter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(notes = "The Newsletter title")
	@NotBlank
	@Size(min = 3)
	private String title;

	@ApiModelProperty(notes = "The Newsletter image")
	@NotBlank
	@Size(min = 3)
	@Lob
	private String image;

	@ApiModelProperty(notes = "The Newsletter content")
	@NotBlank
	@Size(min = 3)
	@Lob
	private String content;

	@CreationTimestamp
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime created;

	public Newsletter() {

	}

	public Newsletter(String title, String image, String content) {
		this.title = title;
		this.image = image;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Newsletter{" + "id=" + id + ", title='" + title + '\'' + ", image=" + image + ", content='" + content
				+ '\'' + '}';
	}
}
