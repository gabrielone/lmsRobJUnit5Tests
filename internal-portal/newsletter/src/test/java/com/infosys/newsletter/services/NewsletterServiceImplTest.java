package com.infosys.newsletter.services;

import com.infosys.newsletter.models.Newsletter;
import com.infosys.newsletter.exceptions.ResourceNotFoundException;
import com.infosys.newsletter.dto.NewsletterDTO;
import com.infosys.newsletter.repositories.NewsletterRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.any;

@RunWith(MockitoJUnitRunner.class)
public class NewsletterServiceImplTest {

	@Mock
	NewsletterRepository newsletterRepository;
	@InjectMocks
	NewsletterServiceImpl service;

	@Test
	public void testCreate() {
		Newsletter newsletter = new Newsletter("Newsletter title", "Newsletter image", "Newsletter content");
		NewsletterDTO newsletterDTO = new NewsletterDTO("Newsletter title", "Newsletter image", "Newsletter content");

		Mockito.when(newsletterRepository.saveAndFlush(any(Newsletter.class))).thenReturn(newsletter);
		NewsletterDTO result = service.create(newsletterDTO);
		Mockito.verify(newsletterRepository).saveAndFlush(any(Newsletter.class));
		Assert.assertEquals(newsletter.getTitle(), result.getTitle());

	}

	@Test
	public void testListAll() {
		Newsletter newsletter = new Newsletter("Newsletter title",
				"<p><br><img style=\"width: 297px;\" src=\"data:image/jpeg;base64,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\" data-filename=\"img1.jpg\"></p>",
				"Newsletter content");
		List<Newsletter> newsletters = new ArrayList<>();
		newsletters.add(newsletter);

		Mockito.when(newsletterRepository.findAll(Sort.by(Sort.Direction.DESC, "created"))).thenReturn(newsletters);
		List<NewsletterDTO> results = service.listAll();
		Assert.assertEquals(results.get(0).getTitle(), newsletter.getTitle());
		Mockito.verify(newsletterRepository).findAll(Sort.by(Sort.Direction.DESC, "created"));

	}

	@Test
	public void testConvertEntityToDto() {
		Newsletter newsletter = new Newsletter("Newsletter title", "Newsletter image", "Newsletter content");
		NewsletterDTO resultedNewsletterDTO = service.convertEntityToDto(newsletter);
		Assert.assertEquals(resultedNewsletterDTO.getTitle(), newsletter.getTitle());
		Assert.assertEquals(resultedNewsletterDTO.getImage(), newsletter.getImage());
		Assert.assertEquals(resultedNewsletterDTO.getContent(), newsletter.getContent());
	}

	@Test
	public void testConvertDtoToEntity() {
		NewsletterDTO newsletterDTO = new NewsletterDTO("Newsletter title", "Newsletter image", "Newsletter content");
		Newsletter resultedNewsletter = service.convertDtoToEntity(newsletterDTO);
		Assert.assertEquals(newsletterDTO.getTitle(), resultedNewsletter.getTitle());
		Assert.assertEquals(newsletterDTO.getImage(), resultedNewsletter.getImage());
		Assert.assertEquals(newsletterDTO.getContent(), resultedNewsletter.getContent());
	}

	@Test
	public void testGetById() {
		Optional<Newsletter> result = Optional
				.of(new Newsletter("Newsletter title", "Newsletter image", "Newsletter content"));
		Mockito.when(newsletterRepository.findById(anyLong())).thenReturn(result);
		NewsletterDTO resultNewsletterDTO = service.getById((long) 1);
		Mockito.verify(newsletterRepository).findById(anyLong());
		Assert.assertEquals(resultNewsletterDTO.getTitle(), result.get().getTitle());

	}

}