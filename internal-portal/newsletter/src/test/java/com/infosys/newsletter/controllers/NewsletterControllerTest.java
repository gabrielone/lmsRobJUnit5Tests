package com.infosys.newsletter.controllers;

import com.infosys.newsletter.dto.NewsletterDTO;
import com.infosys.newsletter.models.Newsletter;
import com.infosys.newsletter.services.NewsletterServiceImpl;

import springfox.documentation.builders.ModelBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class NewsletterControllerTest {

	@Mock
	NewsletterServiceImpl newsletterServiceImpl;
	@InjectMocks
	NewsletterController controller;

	@Test
	public void testCreateNewsletter() {
		NewsletterDTO newsletter = new NewsletterDTO("Newsletter title", "Newsletter image", "Newsletter content");
		Mockito.when(newsletterServiceImpl.create(newsletter)).thenReturn(newsletter);
		NewsletterDTO result = controller.createNewsletter(newsletter);
		Assert.assertEquals(newsletter.getTitle(), result.getTitle());
		Mockito.verify(newsletterServiceImpl).create(newsletter);

	}

	@Test
	public void testGetNewsletters() {
		NewsletterDTO newsletter = new NewsletterDTO("Newsletter title", "Newsletter image", "Newsletter content");
		List<NewsletterDTO> newsletters = new ArrayList<>();
		newsletters.add(newsletter);
		Mockito.when(newsletterServiceImpl.listAll()).thenReturn(newsletters);
		List<NewsletterDTO> results = controller.getNewsletters();
		Assert.assertEquals(results.get(0).getTitle(), newsletter.getTitle());
		Mockito.verify(newsletterServiceImpl).listAll();

	}

}