package com.infosys.internal.portal.leave.management.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.exception.CustomizedResponseEntityExceptionHandler;
import com.infosys.internal.portal.leave.management.models.LeaveDetails;
import com.infosys.internal.portal.leave.management.repository.LeaveManageRepository;
import com.infosys.internal.portal.leave.management.util.CalendarUtil;
import com.infosys.internal.portal.leave.management.util.ModelBuilder;

import static org.mockito.Mockito.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
@ComponentScan(basePackages = {"com.infosys.internal.portal.leave.management.exception"})
class LeaveManageServiceTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	@InjectMocks
	LeaveManageServiceImpl service;
	
	UserInfoDto newUser;
	
	private MockMvc mockMvc;

	@Mock
	LeaveManageRepository leaveManageRepository;
	
	@Mock
	UserInfoService userInfoService;
	
	@Mock
	YearlyTimeOffService yearlyTimeOffService;
	
	List<YearlyTimeOffDto> yearlyTimeOffList;
	
	@BeforeEach
	public void setup() throws ParseException {

		JacksonTester.initFields(this, new ObjectMapper());
	//	newUser = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		yearlyTimeOffList = Stream.of(ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("03-04-2020 11:30:45"), sdf.parse("05-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("10-04-2020 11:30:45"), sdf.parse("10-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("20-04-2020 11:30:45"), sdf.parse("21-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("25-04-2020 11:30:45"), sdf.parse("27-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("05-05-2020 11:30:45"), sdf.parse("06-05-2020 11:30:45"))).collect(Collectors.toList());
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomizedResponseEntityExceptionHandler(),service).build();
	}


	@Test
	public void testGetAllLeaves() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		LeaveDetailsDto leaveDetailsDto1 = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("04-08-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Sick", "Medical", true, true);
		LeaveDetailsDto leaveDetailsDto2 = ModelBuilder.constructLeaveDetailsDto(1, "Harry", "Potter", sdf.parse("31-08-2019 10:20:56"),
				sdf.parse("31-09-2019 10:20:56"), "Sick", "Medical", true, true);
		LeaveDetails leaveDetails1 = service.convertDtoToEntity(leaveDetailsDto1);
		LeaveDetails leaveDetails2 = service.convertDtoToEntity(leaveDetailsDto2);

		when(leaveManageRepository.findAll())
				.thenReturn(Stream.of(leaveDetails1, leaveDetails2).collect(Collectors.toList()));
		assertEquals(2, service.getAllLeaves().size());
	}

	@Test
	public void testGetLeaveDetailsOnId() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		LeaveDetailsDto leave1 = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("04-08-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Sick", "Medical", true, true);

		when(leaveManageRepository.findById(1)).thenReturn(Optional.of(service.convertDtoToEntity(leave1)));
		assertEquals(leave1.getFromDate(), service.getLeaveDetailsOnId(1).getFromDate());
		assertEquals(leave1.getToDate(), service.getLeaveDetailsOnId(1).getToDate());
		assertEquals(leave1.getId(), service.getLeaveDetailsOnId(1).getId());
		assertEquals(leave1.getLeaveType(), service.getLeaveDetailsOnId(1).getLeaveType());
	}

	@Test
	public void testGetAllLeavesOfUser() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		LeaveDetailsDto leave1 = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("31-08-2019 10:20:56"),
				sdf.parse("31-09-2019 10:20:56"), "Sick", "Medical", true, true);

		when(leaveManageRepository.getAllLeavesOfUser(newUser.getEmail()))
				.thenReturn(Stream.of(service.convertDtoToEntity(leave1)).collect(Collectors.toList()));
		assertEquals(1, service.getAllLeavesOfUser(newUser.getEmail()).size());
	}

	@Test
	public void testGetAllActiveLeaves() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		LeaveDetailsDto leave1 = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("31-08-2019 10:20:56"),
				sdf.parse("31-09-2019 10:20:56"), "Sick", "Medical", true, true);

		when(leaveManageRepository.getAllActiveLeaves())
				.thenReturn(Stream.of(service.convertDtoToEntity(leave1)).collect(Collectors.toList()));
		assertEquals(1, service.getAllActiveLeaves().size());
	}
	
	@Test
	public void testValidateSameDates() throws ParseException {
		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "Queen", "Elsa", sdf.parse("18-04-2019 11:30:45"),
				sdf.parse("18-05-2019 11:30:45"), "Flu", "Medical", true, false);
		LeaveDetailsDto leaveDetails2 = ModelBuilder.constructLeaveDetailsDto(1, "Leo", "Tomas", sdf.parse("04-08-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Cold", "Medical", true, false);
		LeaveDetailsDto leaveDetailsNew = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("04-08-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Sick", "Medical", true, false);
		
		when(userInfoService.getUserInfo()).thenReturn(newUser);
		when(leaveManageRepository.getAllLeavesOfUser(newUser.getEmail()))
		.thenReturn(Stream.of(service.convertDtoToEntity(leaveDetails1), service.convertDtoToEntity(leaveDetails2)).collect(Collectors.toList()));
		
		try {
			service.validateRequest(leaveDetailsNew, newUser);
		    fail( "My method didn't throw when I expected it to" );
		} catch (BadRequestException expectedException) {
			String expectedMessage = "ERROR: There is already a request for the interval : " + leaveDetailsNew.getFromDate().toString()
				+ " and " + leaveDetailsNew.getToDate().toString();
		    assertEquals(expectedMessage, expectedException.getMessage());
		}
	}
	
	
	@Test
	public void testValidateDateOverlap() throws ParseException {
		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "Queen", "Elsa", sdf.parse("18-04-2019 11:30:45"),
				sdf.parse("18-05-2019 11:30:45"), "Flu", "Medical", true, false);
		LeaveDetailsDto leaveDetails2 = ModelBuilder.constructLeaveDetailsDto(1, "Leo", "Tomas", sdf.parse("20-05-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Cold", "Medical", true, false);
		LeaveDetailsDto leaveDetailsNew = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("20-04-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Sick", "Medical", true, false);
		
		when(userInfoService.getUserInfo()).thenReturn(newUser);
		when(leaveManageRepository.getAllLeavesOfUser(newUser.getEmail()))
		.thenReturn(Stream.of(service.convertDtoToEntity(leaveDetails1), service.convertDtoToEntity(leaveDetails2)).collect(Collectors.toList()));
		
		try {
			service.validateRequest(leaveDetailsNew, newUser);
		    fail( "My method didn't throw when I expected it to" );
		} catch (BadRequestException expectedException) {
			String expectedMessage = "ERROR: This request overlaps with another request, that request has the interval : "
					+ leaveDetails1.getFromDate().toString() + " and " + leaveDetails1.getToDate().toString()
					+ " please request a new interval that not overlaps with this one";
		    assertEquals(expectedMessage, expectedException.getMessage());
		}
	}
	
	@Test
	public void testValidateDateIntervalOrder() throws ParseException {
		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "Queen", "Elsa", sdf.parse("18-04-2019 11:30:45"),
				sdf.parse("18-05-2019 11:30:45"), "Flu", "Medical", true, false);
		LeaveDetailsDto leaveDetailsNew = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("24-08-2019 11:30:45"),
				sdf.parse("20-08-2019 11:30:45"), "Sick", "Medical", true, false);
		
		when(userInfoService.getUserInfo()).thenReturn(newUser);
		when(leaveManageRepository.getAllLeavesOfUser(newUser.getEmail()))
		.thenReturn(Stream.of(service.convertDtoToEntity(leaveDetails1)).collect(Collectors.toList()));
		
		try {
			service.validateRequest(leaveDetailsNew, newUser);
		    fail( "My method didn't throw when I expected it to" );
		} catch (BadRequestException expectedException) {
			String expectedMessage = "ERROR: fromDate must be after endDate";
		    assertEquals(expectedMessage, expectedException.getMessage());
		}
	}
	
	@Test
	public void testCheckVacantionDaysAvailable() throws ParseException {
		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "Queen", "Elsa", sdf.parse("05-04-2020 11:30:45"),
				sdf.parse("20-04-2020 11:30:45"), "Flu", "Medical", true, false);
		
		when(yearlyTimeOffService.getAllTimeOff()).thenReturn(yearlyTimeOffList);
		service.checkVacantionDaysAvailable(leaveDetails1, newUser);
		assertEquals(13, leaveDetails1.getDuration());
	}
	
	@Test
	public void testCheckVacantionDaysAvailableBadRequest() throws ParseException {
		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "Queen", "Elsa", sdf.parse("05-04-2020 11:30:45"),
				sdf.parse("20-04-2020 11:30:45"), "Flu", "Medical", true, false);
	//	newUser.setVacantionDays(2);
		when(yearlyTimeOffService.getAllTimeOff()).thenReturn(yearlyTimeOffList);
		
		Date startDate1 = leaveDetails1.getFromDate();
		Date endDate1 = leaveDetails1.getToDate();
		long diff = endDate1.getTime() - startDate1.getTime();
		int duration = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		duration = duration + 1;
		
		try {
			service.checkVacantionDaysAvailable(leaveDetails1, newUser);
		    fail( "My method didn't throw when I expected it to" );
		} catch (BadRequestException expectedException) {
		//	String expectedMessage = "You do not have enough vacantion days available. You selected an interval of " +
		//			duration + " days, but you have " + newUser.getVacantionDays() + " days left.";
		   // assertEquals(expectedMessage, expectedException.getMessage());
		}
	}
	
	@Test
	public void testCountOverlapingDays () throws ParseException {
		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "Queen", "Elsa", sdf.parse("05-04-2020 11:30:45"),
				sdf.parse("20-04-2020 11:30:45"), "Flu", "Medical", true, false);
		Date startDate1 = leaveDetails1.getFromDate();
		Date endDate1 = leaveDetails1.getToDate();
		assertEquals(3, CalendarUtil.countOverlapingDays(yearlyTimeOffList, startDate1, endDate1));
	}
}

