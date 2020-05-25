package com.infosys.internal.portal.leave.management.service;

import java.util.Date;
import java.util.List;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.models.YearlyTimeOff;

public interface YearlyTimeOffService {

	void applyTimeOff(YearlyTimeOffDto timeOffDetails);

	List<YearlyTimeOffDto> getAllTimeOff();

	List<YearlyTimeOffDto> findByDate(Date from_date, Date to_date);

	YearlyTimeOffDto getTimeOffDetailsOnId(int id);

	void updateLeaveDetails(YearlyTimeOffDto timeOffDetails);

	YearlyTimeOffDto convertToDto(YearlyTimeOff details);

	YearlyTimeOff convertDtoToEntity(YearlyTimeOffDto details);
	
	void validateTimeOffDetails(YearlyTimeOffDto timeOffDetails);
}
