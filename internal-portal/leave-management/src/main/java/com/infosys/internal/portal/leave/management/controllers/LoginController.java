package com.infosys.internal.portal.leave.management.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.common.model.UserInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosys.internal.portal.leave.management.service.LeaveManageService;
import com.infosys.internal.portal.leave.management.service.UserInfoService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;

/**
 * This controller will provide the basic operations fo users. Like
 * signing-in,registering a new user.
 */
@Controller
public class LoginController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	LeaveManageService leaveManageService;

	/**
	 * This method opens up the login page if user is not authenticated otherwise
	 * redirects the user to user home page.
	 * 
	 * @return
	 */
	@GetMapping(value = { "/", "/login" })
	public ModelAndView login(ModelAndView mav) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserInfoDto userInfoObj = userInfoService.findUserByEmail(auth.getName());

		mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, userInfoObj);
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mav.setViewName(ApplicationView.HOME_VIEW);
			return mav;
		}
		mav.setViewName(ApplicationView.LOGIN_VIEW);
		return mav;
	}

	/**
	 * Opens the registration page to register a new user.
	 * 
	 * @return ModelAndView
	 */
	@GetMapping(value = "/registration")
	public ModelAndView registration(ModelAndView mav) {

		UserInfoDto userInfoObj = new UserInfoDto();
		mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, userInfoObj);
		mav.setViewName(ApplicationView.REGISTRATON_VIEW);
		return mav;
	}

	/**
	 * Gets the form input from registration page and adds the user to the database.
	 * 
	 * @param user
	 * @param bindResult
	 * @return ModelAndView
	 */
	@PostMapping(value = "/registration")
	public ModelAndView createNewUser(ModelAndView mav,
			@Valid @ModelAttribute(ApplicationView.USERINFO_ATTRIBUTE) UserInfoDto userInfoObj,
			BindingResult bindResult) {

		UserInfoDto userExists = userInfoService.findUserByEmail(userInfoObj.getEmail());

		if (userExists != null) {
			bindResult.rejectValue("email", "error.user", "User already exists with Email id");
		}

		if (bindResult.hasErrors()) {
			mav.setViewName(ApplicationView.REGISTRATON_VIEW);
		} else {
			userInfoService.saveUser(userInfoObj);
			mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE,
					"User registered successfully! Awaiting for Manager approval!!");
			mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, new UserInfo());
			mav.setViewName(ApplicationView.REGISTRATON_VIEW);
		}
		return mav;
	}

	/**
	 * Shows the admin page after user authentication is done.
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws JSONException
	 */
	@GetMapping(value = "/home")
	public ModelAndView home(ModelAndView mav, HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserInfoDto userInfo = userInfoService.findUserByEmail(auth.getName());
		request.getSession().setAttribute(ApplicationView.USERINFO_ATTRIBUTE, userInfo);

		mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, userInfo);
		mav.setViewName(ApplicationView.HOME_VIEW);
		return mav;

	}

}
