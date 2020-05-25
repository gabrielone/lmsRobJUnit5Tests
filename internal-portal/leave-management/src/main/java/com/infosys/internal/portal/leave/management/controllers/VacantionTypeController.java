package com.infosys.internal.portal.leave.management.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.email.EmailService;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.models.VacantionType;
import com.infosys.internal.portal.leave.management.service.VacantionTypeService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;

@Controller
public class VacantionTypeController {

	@Autowired
	private VacantionTypeService vacantionTypeService;

	@GetMapping(value = "/create-vacationtype")
	public ModelAndView applyLeave(ModelAndView mav) {

		mav.addObject(ApplicationView.VACANTIONTYPE_ATTRIBUTE, new VacantionType());
		mav.setViewName(ApplicationView.CREATE_VACANTION_TYPE_VIEW);
		return mav;
	}
	
	@PostMapping(value = "/create-vacationtype", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createVacantionType(@Valid @RequestBody VacantionTypeDto newVacantionType, Errors errors) {

		if (errors.hasErrors()) {
        	throw new BadRequestException("Attributes are null, they must be filled", errors);     
        }        
		
		vacantionTypeService.validateVacantionType(newVacantionType);

        return  ResponseEntity.ok(newVacantionType);
	}
}
