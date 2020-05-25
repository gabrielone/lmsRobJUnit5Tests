package com.infosys.internal.portal.leave.management.controllers;

import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.service.VacantionTypeService;
import com.infosys.internal.portal.leave.management.service.YearlyTimeOffService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;

@Controller
public class YearlyTimeOffController {

	@Autowired
	private YearlyTimeOffService timeOffManageService;

	@Autowired
	private VacantionTypeService vacantionTypeService;

	@GetMapping(value = "/apply-yearly-time-off")
	public ModelAndView applyLeave(ModelAndView mav) {

		List<VacantionTypeDto> vacantionTypeList = vacantionTypeService.getAll();

		mav.addObject(ApplicationView.VACANTIONTYPE_LIST, vacantionTypeList);
		mav.addObject(ApplicationView.YEARLY_TIME_OFF_ATTRIBUTE, new YearlyTimeOffDto());

		mav.setViewName(ApplicationView.APPLY_YEARRLY_TIME_OFF_VIEW);
		return mav;
	}

	@PostMapping(value = "/apply-yearly-time-off", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> submitApplyLeave(@Valid @RequestBody YearlyTimeOffDto timeOffDetails, Errors errors) {

		VacantionTypeDto vacantionType = vacantionTypeService.findById(timeOffDetails.getVacantionTypeId());
		timeOffDetails.setVacantionType(vacantionType);
		
		if (errors.hasErrors()) {
        	throw new BadRequestException("Attributes are null, they must be filled", errors);     
        }        
        
		timeOffManageService.validateTimeOffDetails(timeOffDetails);
		timeOffManageService.applyTimeOff(timeOffDetails);

        return  ResponseEntity.ok(timeOffDetails);
	}
}
