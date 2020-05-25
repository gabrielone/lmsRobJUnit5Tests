package com.infosys.internal.portal.leave.management.service;



import java.util.List;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.models.LeaveDetails;



public interface LeaveManageService {

	LeaveDetailsDto applyLeave(LeaveDetailsDto leaveDetails);

	List<LeaveDetailsDto> getAllLeaves();

	LeaveDetailsDto getLeaveDetailsOnId(int id);

	void updateLeaveDetails(LeaveDetailsDto leaveDetails);

	List<LeaveDetailsDto> getAllActiveLeaves();

	List<LeaveDetailsDto> getAllLeavesOfUser(String username);

	List<LeaveDetailsDto> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected);

	LeaveDetailsDto convertToDto(LeaveDetails details);

	LeaveDetails convertDtoToEntity(LeaveDetailsDto details);
	
	void validateRequest(LeaveDetailsDto leaveDetails, UserInfoDto userInfo);
	
	void checkVacantionDaysAvailable(LeaveDetailsDto leaveDetails, UserInfoDto userInfo);

}
