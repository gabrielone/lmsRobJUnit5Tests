package com.infosys.internal.portal.leave.management.controllers;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.infosys.internal.portal.leave.management.service.UserInfoService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;

import java.util.List;

@Controller
public class AdminController {

	@Autowired
	UserInfoService userInfoService;

	@GetMapping(value = "/change-password")
	public ModelAndView changePasswordForm(ModelAndView mav) {

		mav.setViewName(ApplicationView.CHANGE_PASSWORD_VIEW);
		return mav;
	}

	@PostMapping(value = "/change-password")
	public ModelAndView changePasswordSubmit(@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword) {

		String REDIRECT_VIEW_ADDR_1 = "../user/change-password";
		String REDIRECT_VIEW_ADDR_2 = "../user/home";

		ModelAndView mav = new ModelAndView();
		BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfoDto userInfo = userInfoService.findUserByEmail(username);
		String encodedPassword = userInfo.getPassword();

		if (!bCryptPassEncoder.matches(currentPassword, encodedPassword)) {
			mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE, "Current Password entered is wrong!!!");
			mav.setView(new RedirectView(REDIRECT_VIEW_ADDR_1));
			return mav;
		}
		userInfo.setPassword(newPassword);
		userInfoService.saveUser(userInfo);
		mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE, "Password changed Successfully!!!");
		mav.setView(new RedirectView(REDIRECT_VIEW_ADDR_2));
		return mav;
	}

	@GetMapping(value = "/manage-users")
	public ModelAndView showManageUsers(ModelAndView mav) {

		List<UserInfoDto> userList = userInfoService.getUsers();
		mav.addObject(ApplicationView.USERS_ATTRIBUTE, userList);
		mav.setViewName(ApplicationView.MANAGE_USERS_VIEW);
		return mav;
	}

	@GetMapping(value = "/manage-users/{action}/{id}")
	public ModelAndView manageUsers(ModelAndView mav, @PathVariable("action") String action,
			@PathVariable("id") int id) {

		String redirectViewAddr = "/user/manage-users";
		UserInfoDto userInfo = null;
		if (action.equals("edit")) {
			userInfo = userInfoService.getUserById(id);
			mav.addObject(ApplicationView.USERINFO_ATTRIBUTE, userInfo);
			mav.setViewName(ApplicationView.EDIT_USER_VIEW);

		} else if (action.equals("delete")) {
			userInfoService.deleteUser(id);
			mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE, "User removed successfully!!");
			mav.setView(new RedirectView(redirectViewAddr));
		} else if (action.equals("block")) {
			userInfoService.blockUser(id);
			mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE, "User blocked successfully!!");
			mav.setView(new RedirectView(redirectViewAddr));
		} else if (action.equals("unblock")) {
			userInfoService.unBlockUser(id);
			mav.addObject(ApplicationView.SUCCESS_MESSAGE_ATTRIBUTE, "User is active now!!");
			mav.setView(new RedirectView(redirectViewAddr));
		}

		return mav;
	}

}
