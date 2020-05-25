package com.infosys.internal.portal.leave.management.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.models.LeaveDetails;
import com.infosys.internal.portal.leave.management.repository.LeaveManageNativeSqlRepo;
import com.infosys.internal.portal.leave.management.repository.LeaveManageRepository;
import com.infosys.internal.portal.leave.management.util.CalendarUtil;

@Service(value = "leaveManageService")
public class LeaveManageServiceImpl implements LeaveManageService {

	@Autowired
	private LeaveManageRepository leaveManageRepository;

	@Autowired
	private LeaveManageNativeSqlRepo leaveManageNativeRepo;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private YearlyTimeOffService yearlyTimeOffService;

	@SuppressWarnings("deprecation")
	public LeaveDetailsDto applyLeave(LeaveDetailsDto leaveDetails) {
		return this.convertToDto(leaveManageRepository.save(this.convertDtoToEntity(leaveDetails)));
	}

	public List<LeaveDetailsDto> getAllLeaves() {

		List<LeaveDetails> leaves = leaveManageRepository.findAll();
		return leaves.stream().map(leave -> this.convertToDto(leave)).collect(Collectors.toList());
	}

	public LeaveDetailsDto getLeaveDetailsOnId(int id) {

		Optional<LeaveDetails> leave = leaveManageRepository.findById(id);
		if (leave.isPresent()) {
			return this.convertToDto(leave.get());
		}

		return null;
	}

	public void updateLeaveDetails(LeaveDetailsDto leaveDetails) {

		leaveManageRepository.save(this.convertDtoToEntity(leaveDetails));

	}

	public List<LeaveDetailsDto> getAllActiveLeaves() {

		List<LeaveDetails> leaves = leaveManageRepository.getAllActiveLeaves();
		return leaves.stream().map(leave -> this.convertToDto(leave)).collect(Collectors.toList());
	}

	public List<LeaveDetailsDto> getAllLeavesOfUser(String username) {

		List<LeaveDetails> leaves = leaveManageRepository.getAllLeavesOfUser(username);
		return leaves.stream().map(leave -> this.convertToDto(leave)).collect(Collectors.toList());

	}

	public List<LeaveDetailsDto> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected) {

		StringBuilder whereQuery = new StringBuilder();
		if (pending)
			whereQuery.append("active=true or ");
		if (accepted)
			whereQuery.append("(active=false and accept_reject_flag=true) or ");
		if (rejected)
			whereQuery.append("(active=false and accept_reject_flag=false) or ");

		whereQuery.append(" 1=0");

		List<LeaveDetails> leaves = leaveManageNativeRepo.getAllLeavesOnStatus(whereQuery);
		return leaves.stream().map(leave -> this.convertToDto(leave)).collect(Collectors.toList());
	}

	@Override
	public LeaveDetailsDto convertToDto(LeaveDetails details) {

		if (details == null) {
			return null;
		}
		return new ModelMapper().map(details, LeaveDetailsDto.class);
	}

	@Override
	public LeaveDetails convertDtoToEntity(LeaveDetailsDto details) {
		if (details == null) {
			return null;
		}
		return new ModelMapper().map(details, LeaveDetails.class);
	}

	@Override
	public void validateRequest(LeaveDetailsDto leaveDetails, UserInfoDto userInfoDto) {
		List<LeaveDetailsDto> userLeaves = this.getAllLeavesOfUser(userInfoDto.getEmail());

		Date startDate1 = leaveDetails.getFromDate();
		Date endDate1 = leaveDetails.getToDate();

		Iterator<LeaveDetailsDto> iterator = userLeaves.iterator();
		while (iterator.hasNext()) {
			LeaveDetailsDto element = iterator.next();
			boolean isAccepted = element.isAcceptRejectFlag();
			boolean isActive = element.isActive();
			// throw error: if dates interval are equal
			if ((startDate1.compareTo(element.getFromDate()) == 0) && endDate1.compareTo(element.getToDate()) == 0) {
				String errorMessage = "ERROR: There is already a request for the interval : " + startDate1.toString()
						+ " and " + endDate1.toString();
				throw new BadRequestException("ERROR: There is already a request for the interval : "
						+ startDate1.toString() + " and " + endDate1.toString());
				// or if there is already a date that include this one
			} else if ((startDate1.compareTo(element.getToDate()) <= 0)
					&& (element.getFromDate().compareTo(endDate1) <= 0)) {
				if((!element.isActive() && !element.isAcceptRejectFlag())) {
					break;
				} else {
				throw new BadRequestException(
						"ERROR: This request overlaps with another request, that request has the interval : "
								+ element.getFromDate().toString() + " and " + element.getToDate().toString()
								+ " please request a new interval that not overlaps with this one");
				}
				// or startDate1 must be less than endDate
			} else if ((startDate1.compareTo(endDate1)) > 0) {
				throw new BadRequestException("ERROR: fromDate must be after endDate");
			}

		}
		
		checkVacantionDaysAvailable(leaveDetails, userInfoDto);
		
	}

	@Override
	public void checkVacantionDaysAvailable(LeaveDetailsDto leaveDetails, UserInfoDto userInfo) {
		Date startDate1 = leaveDetails.getFromDate();
		Date endDate1 = leaveDetails.getToDate();
		long diff = endDate1.getTime() - startDate1.getTime();
		int duration = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		duration = duration + 1;
		int duration2 = duration;
		
		//check if any yearly time off days in this interval
		List<YearlyTimeOffDto> list = yearlyTimeOffService.getAllTimeOff();
		
		int count = CalendarUtil.countOverlapingDays(list, startDate1, endDate1);
		duration = duration - count;
		
		//check if vacantion days available
//		if(duration < userInfo.getVacantionDays()) {
//			leaveDetails.setDuration(duration);
//			leaveDetails.setActive(true);
//		} else {
//			throw new BadRequestException("You do not have enough vacantion days available. You selected an interval of " +
//						duration2 + " days, but you have " + userInfo.getVacantionDays() + " days left.");
//		}
	}
	
}
