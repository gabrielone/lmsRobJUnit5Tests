package com.infosys.newsletter.services;

import com.infosys.newsletter.models.Newsletter;
import com.infosys.newsletter.dto.NewsletterDTO;
import com.infosys.newsletter.repositories.NewsletterRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsletterServiceImpl implements NewsletterService {

	private NewsletterRepository newsletterRepository;

	@Autowired
	public NewsletterServiceImpl(NewsletterRepository newsletterRepository) {
		this.newsletterRepository = newsletterRepository;
	}

	public List<NewsletterDTO> listAll() {

		List<Newsletter> newsletters = newsletterRepository.findAll(Sort.by(Sort.Direction.DESC, "created"));
		List<Newsletter> retNewsletters = new ArrayList<>();
		for (int i = 0; i < newsletters.size(); i++) {
			int startFrom = newsletters.get(i).getImage().indexOf("src=") + 5;
			int endAt = newsletters.get(i).getImage().indexOf("data-filename") - 2;

			Newsletter newNewsletters = new Newsletter();
			newNewsletters.setTitle(newsletters.get(i).getTitle()); // custom title settings if needed
			newNewsletters.setImage(newsletters.get(i).getImage().substring(startFrom, endAt));
			newNewsletters.setContent(newsletters.get(i).getContent());
			newNewsletters.setCreated(newsletters.get(i).getCreated());

			retNewsletters.add(newNewsletters);
		}

		return retNewsletters.stream().map(newsletter -> this.convertEntityToDto(newsletter))
				.collect(Collectors.toList());
	}

	public NewsletterDTO getById(Long newsletter_id) {

		Optional<Newsletter> result = newsletterRepository.findById(newsletter_id);
		if (result.isPresent()) {
			return this.convertEntityToDto(result.get());
		}

		return null;
	}

	@Override
	@Transactional
	public NewsletterDTO create(NewsletterDTO newsletter) {

		Newsletter newNewsletter = new Newsletter();
		newNewsletter.setTitle(newsletter.getTitle());
		newNewsletter.setImage(newsletter.getImage());
		newNewsletter.setContent(newsletter.getContent());

		Newsletter savedNewsletter = newsletterRepository.saveAndFlush(newNewsletter);
		return new NewsletterDTO(savedNewsletter.getTitle(), savedNewsletter.getImage(), savedNewsletter.getContent());

	}

	@Override
	public NewsletterDTO convertEntityToDto(Newsletter details) {

		if (details == null) {
			return null;
		}
		return new ModelMapper().map(details, NewsletterDTO.class);
	}

	@Override
	public Newsletter convertDtoToEntity(NewsletterDTO details) {
		if (details == null) {
			return null;
		}
		return new ModelMapper().map(details, Newsletter.class);
	}

}
