package com.infosys.internal.portal.leave.management.util;

import java.util.Date;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.models.LeaveDetails;
import com.infosys.internal.portal.leave.management.models.VacantionType;
import com.infosys.internal.portal.leave.management.models.YearlyTimeOff;


public class ModelBuilder {

	public static LeaveDetailsDto constructLeaveDetailsDto(int id, String username, String employeeName, Date fromDate, Date toDate,
			String leaveType, String reason, boolean acceptRejectFlag, boolean active) {

		return GenericBuilder.of(LeaveDetailsDto::new).with(LeaveDetailsDto::setId, id)
				.with(LeaveDetailsDto::setUsername, username).with(LeaveDetailsDto::setEmployeeName, employeeName)
				.with(LeaveDetailsDto::setFromDate, fromDate).with(LeaveDetailsDto::setToDate, toDate)
				.with(LeaveDetailsDto::setLeaveType, leaveType).with(LeaveDetailsDto::setReason, reason)
				.with(LeaveDetailsDto::setAcceptRejectFlag, acceptRejectFlag).with(LeaveDetailsDto::setActive, active)
				.build();
	}

//	public static UserInfoDto constructUserInfoDto(int id, String firstName, String lastName, String email, int vacantionDays) {
//
//		return GenericBuilder.of(UserInfoDto::new).with(UserInfoDto::setId, id)
//				.with(UserInfoDto::setFirstName, firstName).with(UserInfoDto::setLastName, lastName)
//				.with(UserInfoDto::setEmail, email)
//				.with(UserInfoDto::setVacantionDays, vacantionDays).build();
//	}
	
	public static VacantionTypeDto constructVacantionTypeDto(int id, String type) {

		return GenericBuilder.of(VacantionTypeDto::new).with(VacantionTypeDto::setId, id)
				.with(VacantionTypeDto::setType, type).build();
	}
	
	public static YearlyTimeOffDto constructYearlyTimeOffDto(int id, Date fromDate, Date toDate, 
			VacantionTypeDto vacantionType, String vacantionTypeId) {

		return GenericBuilder.of(YearlyTimeOffDto::new).with(YearlyTimeOffDto::setId, id)
				.with(YearlyTimeOffDto::setFromDate, fromDate)
				.with(YearlyTimeOffDto::setToDate, toDate)
				.with(YearlyTimeOffDto::setVacantionType, vacantionType)
				.with(YearlyTimeOffDto::setVacantionTypeId, vacantionTypeId).build();
	}
	
	public static YearlyTimeOff constructYearlyTimeOff(int id, Date fromDate, Date toDate) {

		return GenericBuilder.of(YearlyTimeOff::new).with(YearlyTimeOff::setId, id)
				.with(YearlyTimeOff::setFromDate, fromDate)
				.with(YearlyTimeOff::setToDate, toDate).build();
	}
	
	public static VacantionType constructVacantionType(Long id, String type) {

		return GenericBuilder.of(VacantionType::new)
				.with(VacantionType::setId, id)
				.with(VacantionType::setType, type).build();
	}
	
	public static YearlyTimeOffDto constructYearlyTimeOffDto(int id, Date fromDate, Date toDate) {

		return GenericBuilder.of(YearlyTimeOffDto::new).with(YearlyTimeOffDto::setId, id)
				.with(YearlyTimeOffDto::setFromDate, fromDate)
				.with(YearlyTimeOffDto::setToDate, toDate).build();
	}
}
