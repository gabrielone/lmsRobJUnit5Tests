package com.infosys.internal.portal.leave.management.controllers;

import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.email.EmailService;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.service.LeaveManageService;
import com.infosys.internal.portal.leave.management.service.UserInfoService;
import com.infosys.internal.portal.leave.management.service.YearlyTimeOffService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;


@Controller
public class LeaveManageController {

	@Autowired
	private LeaveManageService leaveManageService;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private YearlyTimeOffService timeOffManageService;

	@GetMapping(value = "/apply-leave")
	public ModelAndView applyLeave(ModelAndView mav) {

		UserInfoDto userInfo = userInfoService.getUserInfo();
		mav.addObject(ApplicationView.LEAVEDETAILS_ATTRIBUTE, new LeaveDetailsDto());
		mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, userInfo);
		mav.setViewName(ApplicationView.APPLY_LEAVE_VIEW);
		return mav;
	}

	@PostMapping(value = "/apply-leave", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> submitApplyLeave(@Valid @RequestBody LeaveDetailsDto leaveDetails, Errors errors) {
		

		UserInfoDto userInfo = userInfoService.getUserInfo();

		if (errors.hasErrors()) {
        	throw new BadRequestException("Attributes are null, they must be filled", errors);     
        }        	
        
		leaveManageService.validateRequest(leaveDetails, userInfo);

		leaveDetails.setUsername(userInfo.getEmail());
		leaveDetails.setEmployeeName(userInfo.getFirstName() + " " + userInfo.getLastName());
		leaveManageService.applyLeave(leaveDetails);
		
		//send mail to manager
		new EmailService("infosysleavemanagement@gmail.com", "Inf@sys2020")
			.sendEmail("infosysleavemanagement@gmail.com", "This email is just for test", leaveDetails.getReason());

        return  ResponseEntity.ok(leaveDetails);
	}



	@GetMapping(value = "/get-all-leaves")
	public @ResponseBody String getAllLeaves(@RequestParam(value = "pending", defaultValue = "false") boolean pending,
			@RequestParam(value = "accepted", defaultValue = "false") boolean accepted,
			@RequestParam(value = "rejected", defaultValue = "false") boolean rejected) {

		UserInfoDto userInfo = userInfoService.getUserInfo();
		Iterator<LeaveDetailsDto> iterator = leaveManageService.getAllLeavesOfUser(userInfo.getEmail()).iterator();
		if (pending || accepted || rejected)
			iterator = leaveManageService.getAllLeavesOnStatus(pending, accepted, rejected).iterator();
		JSONArray jsonArr = new JSONArray();

		while (iterator.hasNext()) {
			LeaveDetailsDto leaveDetails = iterator.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("title", leaveDetails.getEmployeeName());
			jsonObj.put("start", leaveDetails.getFromDate());
			Date toDate = leaveDetails.getToDate();
			toDate.setDate(toDate.getDate() + 1);
			jsonObj.put("end", toDate);
			if (leaveDetails.isActive())
				jsonObj.put(ApplicationView.COLOR_ATTRIBUTE, "#0878af");
			if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag())
				jsonObj.put(ApplicationView.COLOR_ATTRIBUTE, "green");
			if (!leaveDetails.isActive() && !leaveDetails.isAcceptRejectFlag())
				jsonObj.put(ApplicationView.COLOR_ATTRIBUTE, "red");
			jsonArr.put(jsonObj);
		}
		
		Iterator<YearlyTimeOffDto> iterator2 = timeOffManageService.getAllTimeOff().iterator();

		while (iterator2.hasNext()) {
			YearlyTimeOffDto yearlyTimeOff = iterator2.next();
			JSONObject jsonObj2 = new JSONObject();
			jsonObj2.put("title", " ");
			jsonObj2.put("start", yearlyTimeOff.getFromDate());
			Date toDate = yearlyTimeOff.getToDate();
			toDate.setDate(toDate.getDate() + 1);
			jsonObj2.put("end", toDate);
			jsonObj2.put(ApplicationView.COLOR_ATTRIBUTE, "orange");
			jsonArr.put(jsonObj2);
		}

		return jsonArr.toString();
	}

	@GetMapping(value = "/manage-leaves")
	public ModelAndView manageLeaves(ModelAndView mav) {

		mav.addObject(ApplicationView.LEAVES_LIST_ATTRIBUTE, leaveManageService.getAllActiveLeaves());
		mav.setViewName(ApplicationView.MANAGE_LEAVES_VIEW);
		return mav;
	}

	@GetMapping(value = "/manage-leaves/{action}/{id}")
	public ModelAndView acceptOrRejectLeaves(ModelAndView mav, @PathVariable("action") String action,
			@PathVariable("id") int id) {

		LeaveDetailsDto leaveDetails = leaveManageService.getLeaveDetailsOnId(id);
		String redirectView = "/user/manage-leaves";
		
		UserInfoDto user = userInfoService.findUserByEmail(leaveDetails.getUsername());

		if (action.equals("accept")) {
			leaveDetails.setAcceptRejectFlag(true);
			leaveDetails.setActive(false);
		//	user.setVacantionDays(user.getVacantionDays() - leaveDetails.getDuration());
			userInfoService.saveUser(user);
		} else if (action.equals("reject")) {
			leaveDetails.setAcceptRejectFlag(false);
			leaveDetails.setActive(false);
		}
		leaveManageService.updateLeaveDetails(leaveDetails);
		mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, user);
		mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE, "Updated Successfully!");
		mav.setView(new RedirectView(redirectView));
		return mav;
	}

	@GetMapping(value = "/my-leaves")
	public ModelAndView showMyLeaves(ModelAndView mav) {

		UserInfoDto userInfo = userInfoService.getUserInfo();
		List<LeaveDetailsDto> leavesList = leaveManageService.getAllLeavesOfUser(userInfo.getEmail());
		mav.addObject(ApplicationView.LEAVES_LIST_ATTRIBUTE, leavesList);
		mav.setViewName(ApplicationView.MY_LEAVES_VIEW);
		return mav;
	}
}
