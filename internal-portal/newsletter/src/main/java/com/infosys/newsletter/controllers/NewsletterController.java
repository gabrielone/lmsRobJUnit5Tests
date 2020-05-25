package com.infosys.newsletter.controllers;

import com.infosys.newsletter.models.Newsletter;
import com.infosys.newsletter.dto.NewsletterDTO;
import com.infosys.newsletter.repositories.NewsletterRepository;
import com.infosys.newsletter.services.NewsletterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class NewsletterController {

	final static String HOME_PAGE = "index.html";
	final static String NEWSLETTER_FORM = "create-newsletter.html";

	@Autowired
	NewsletterService newsletterService;

	public NewsletterController(NewsletterService newsletterService) {
		this.newsletterService = newsletterService;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<NewsletterDTO> newsletterDTOList = newsletterService.listAll();
		model.addAttribute("newsletters", newsletterDTOList);
		return HOME_PAGE;
	}

	@GetMapping("/newsletter")
	public String showNewsletterForm(Model model) {
		NewsletterDTO newsletterDTO = new NewsletterDTO();
		model.addAttribute("newsletterDTO", newsletterDTO);
		return NEWSLETTER_FORM;
	}

	@PostMapping("/newsletter")
	public String submitNewsletterForm(@ModelAttribute("newsletterDTO") NewsletterDTO newsletterDTO) {

		newsletterService.create(newsletterDTO);

		return "redirect:/";
	}

	@ApiOperation(value = "Display all Newsletters", notes = "You can provide pagination parameters if too many rows are returned", response = Newsletter.class)
	@GetMapping("/api/newsletters")
	public List<NewsletterDTO> getNewsletters() {

		return newsletterService.listAll();
	}

	@ApiOperation(value = "Display specific Newsletter", notes = "You can provide a newsletter Id as a path variable", response = Newsletter.class)
	@GetMapping("/api/newsletters/{newsletterId}")
	public NewsletterDTO getNewsletterById(
			@ApiParam(required = true, name = "newsletterId", value = "ID of the Newsletter you want to fetch", defaultValue = "1") @PathVariable Long newsletter_id) {
		return newsletterService.getById(newsletter_id);
	}

	@ApiOperation(value = "Create a new Newsletter", notes = "Allows us to create a new Newsletter", response = Newsletter.class)
	@PostMapping("/api/newsletters")
	public NewsletterDTO createNewsletter(
			@ApiParam(value = "Newsletter body in JSON format", required = true) @Valid @RequestBody NewsletterDTO newsletter) {
		return newsletterService.create(newsletter);
	}
}