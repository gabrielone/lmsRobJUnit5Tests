package com.infosys.newsletter.models;

import com.infosys.newsletter.models.Newsletter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NewsletterTest {

	@Test
	public void testGettersAndSetters() {
		Newsletter newsletter = new Newsletter();
		newsletter.getContent();
		newsletter.getImage();
		newsletter.getTitle();
		newsletter.setContent("Content");
		newsletter.setImage("Image");
		newsletter.setTitle("title");
	}

	@Test
	public void testConstructorWithArgs() {
		Newsletter newsletter = new Newsletter("title", "image", "content");
	}
}