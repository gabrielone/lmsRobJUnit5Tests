package com.infosys.newsletter.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.infosys.newsletter.dto.NewsletterDTO;

@RunWith(MockitoJUnitRunner.class)
public class NewsletterDTOTest {

	@Test
	public void testGettersAndSetters() {
		NewsletterDTO newsletter = new NewsletterDTO();
		newsletter.getContent();
		newsletter.getImage();
		newsletter.getTitle();
		newsletter.setContent("Content");
		newsletter.setImage("Image");
		newsletter.setTitle("title");
	}
}
