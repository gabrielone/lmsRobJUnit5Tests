package com.infosys.internal.portal.claims.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.infosys.internal.portal.claims.config.RecordNotFoundException;
import com.infosys.internal.portal.claims.model.ClaimsEntity;
import com.infosys.internal.portal.claims.service.ClaimService;
import com.infosys.lms.utils.AppConstants;

@Controller
public class ClaimsController {
	@Autowired
	ClaimService service;

	// USER

	@RequestMapping(value = AppConstants.LIST_CLAIMS)
	public String getAllClaims(Model model) {
		List<ClaimsEntity> list = service.getAllClaims();

		model.addAttribute("claims", list);
		return "list-claims";
	}

	@RequestMapping(value = AppConstants.EDIT_USER_PAGE)
	public String editEmployee(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
		if (id.isPresent()) {
			Optional<ClaimsEntity> entity = service.getClaimsById(id.get());
			model.addAttribute("claim", entity);
		} else {
			model.addAttribute("claim", new ClaimsEntity());
		}
		return "add-edit-claims";
	}

	@RequestMapping(value = AppConstants.EDIT_BY_ID)
	public String updateEmployeeById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Optional<ClaimsEntity> entity = service.getClaimsById(id.get());
			model.addAttribute("claim", entity);
		} else {
			model.addAttribute("claim", new ClaimsEntity());
		}
		return "add-edit-claims";
	}

	@RequestMapping(value = AppConstants.DELETE_USER_CLAIM)
	public String deleteEmployeeById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteClaimById(id);
		return "forward:/list-claims";
	}

	@RequestMapping(path = "/createClaim", method = RequestMethod.POST)
	public String createOrUpdateEmployee(ClaimsEntity claims) {

		service.createOrUpdateClaims(claims);
		return "forward:/list-claims";
	}

	// HR

	@RequestMapping(value = AppConstants.LIST_HR_CLAIMS)
	public String getAllClaimsHrPage(Model model) {
		List<ClaimsEntity> list = service.getAllClaimsSpecificHRPage();

		model.addAttribute("claims", list);
		return "hr_page";
	}

	@RequestMapping(value = AppConstants.EDIT_HR_PAGE)
	public String editareEmployeeHrClaimsById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Optional<ClaimsEntity> entity = service.getClaimsById(id.get());
			model.addAttribute("claim", entity);
		} else {
			model.addAttribute("claim", new ClaimsEntity());
		}
		return "add-edit-claims_hr";
	}

	@RequestMapping(value = AppConstants.DELETE_CLIM_HR)
	public String deleteAsHrRoleEmployeeById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteClaimById(id);
		return "forward:/hr_page";
	}

	@RequestMapping(path = "/createClaimHr", method = RequestMethod.POST)
	public String createOrUpdateAsHrClaimsEmployee(ClaimsEntity claims) {

		service.createOrUpdateClaims(claims);
		return "forward:/hr_page";
	}

	// MANAGER

	@RequestMapping(value = AppConstants.LIST_MANAGER_CLAIMS)
	public String getAllManagerClaims(Model model) {
		List<ClaimsEntity> list = service.getAllClaimsSpecificManagerPage();

		model.addAttribute("claims", list);
		return "manager_page";
	}

	@RequestMapping(value = AppConstants.EDIT_CLAIM_MANAGER)
	public String editareEmployeeManagerClaimsById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {
		if (id.isPresent()) {
			Optional<ClaimsEntity> entity = service.getClaimsById(id.get());
			model.addAttribute("claim", entity);
		} else {
			model.addAttribute("claim", new ClaimsEntity());
		}
		return "edit_status_manager";
	}

	@RequestMapping(value = AppConstants.DELETE_CLAIM_MANAGER)
	public String deletedAsManagerRoleEmployeeById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteClaimById(id);
		return "forward:/manager_page";
	}

	@RequestMapping(path = "/createClaimManager", method = RequestMethod.POST)
	public String createOrUpdateManagerClaimsEmployee(ClaimsEntity claims) {

		service.createOrUpdateClaims(claims);
		return "forward:/manager_page";
	}

}