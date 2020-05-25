package com.infosys.newsletter.services;

import com.infosys.newsletter.dto.NewsletterDTO;
import com.infosys.newsletter.models.Newsletter;

import java.util.List;

import javax.validation.Valid;

public interface NewsletterService {

	NewsletterDTO create(@Valid NewsletterDTO newsletter);

	NewsletterDTO convertEntityToDto(Newsletter details);

	Newsletter convertDtoToEntity(NewsletterDTO details);

	List<NewsletterDTO> listAll();

	NewsletterDTO getById(Long newsletter_id);

}
