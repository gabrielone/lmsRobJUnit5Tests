package com.infosys.internal.portal.leave.management.util;

public class ApplicationView {

	private ApplicationView() {
		throw new IllegalStateException("Application view class");
	}

	// views
	public final static String LOGIN_VIEW = "login";
	public final static String REGISTRATON_VIEW = "registration";
	public final static String HOME_VIEW = "home";
	public final static String APPLY_LEAVE_VIEW = "applyLeave";
	public final static String CREATE_VACANTION_TYPE_VIEW = "createvacantiontype";
	public final static String MY_LEAVES_VIEW = "myLeaves";
	public final static String APPLY_YEARRLY_TIME_OFF_VIEW = "applyYearlyTimeOff";
	public final static String CHANGE_PASSWORD_VIEW = "changePassword";
	public final static String MANAGE_USERS_VIEW = "manageUsers";
	public final static String EDIT_USER_VIEW = "editUser";
	public final static String MANAGE_LEAVES_VIEW = "manageLeaves";

	// views - attributes
	public final static String USERINFO_ATTRIBUTE = "userInfo";
	public final static String SUCCESS_MESSAGE_ATTRIBUTE = "successMessage";
	public final static String COLOR_ATTRIBUTE = "color";
	public final static String USERS_ATTRIBUTE = "users";
	public final static String LEAVEDETAILS_ATTRIBUTE = "leaveDetails";
	public final static String LEAVES_LIST_ATTRIBUTE = "leavesList";
	public final static String VACANTIONTYPE_ATTRIBUTE = "vacantionType";
	public final static String VACANTIONTYPE_LIST = "vacantionTypeList";
	public final static String YEARLY_TIME_OFF_ATTRIBUTE = "yearlyTimeOff";

	// views - errors - attributes
	public final static String ERROR_INTERVAL_ATTRIBUTE = "error.interval";
	public final static String ERROR_DUPLICATE_ATTRIBUTE = "error.duplicate";
	public final static String ERROR_NULL_ATTRIBUTE = "error.nullAttribute";
}
