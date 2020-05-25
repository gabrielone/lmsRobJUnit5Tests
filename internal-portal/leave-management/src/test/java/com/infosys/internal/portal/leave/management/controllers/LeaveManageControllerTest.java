package com.infosys.internal.portal.leave.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.email.EmailService;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.exception.CustomizedResponseEntityExceptionHandler;
import com.infosys.internal.portal.leave.management.service.LeaveManageService;
import com.infosys.internal.portal.leave.management.service.UserInfoService;
import com.infosys.internal.portal.leave.management.service.YearlyTimeOffService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;
import com.infosys.internal.portal.leave.management.util.JsonHelper;
import com.infosys.internal.portal.leave.management.util.ModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
@ComponentScan(basePackages = {"com.infosys.internal.portal.leave.management.exception"})
class LeaveManageControllerTest {

	private MockMvc mockMvc;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	@InjectMocks
	LeaveManageController leaveManageController;

	@Mock
	LeaveManageService leaveManageService;

	@Mock
	UserInfoService userInfoService;

	@Mock
	EmailService emailService;
	
	@Mock
	YearlyTimeOffService yearlyTimeOffService;
	
	private JacksonTester<LeaveDetailsDto> jsonLeaveDetails;

	@BeforeEach
	public void setup() {
		// Setup Spring test in webapp-mode (same config as spring-boot)
		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomizedResponseEntityExceptionHandler(),leaveManageController).build();
	}

	@Test
	public void testLoadApplyLeavePage() throws Exception {

		this.mockMvc.perform(get("/apply-leave")).andExpect(status().isOk()).andExpect(view().name("applyLeave"))
				.andExpect(model().attributeExists("leaveDetails"));
	}
	
	@Test
	public void testSubmitApplyLeave() throws ParseException, Exception {

		LeaveDetailsDto leaveDetails = ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("30-10-2019 10:20:56"),
				sdf.parse("20-11-2019 10:20:56"), "Sick", "Medical", true, false);

		//when(userInfoService.getUserInfo()).thenReturn(ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22));
		
		doNothing().when(emailService).sendEmail("infosysleavemanagement@gmail.com", "This email is just for test",
				leaveDetails.getReason());

		this.mockMvc.perform(post("/apply-leave").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(leaveDetails)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSubmitApplyLeaveWithBadRequest() throws ParseException, Exception {
		//UserInfoDto newUser = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		LeaveDetailsDto leaveDetailsNew =  ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("04-08-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Sick", "Medical", true, false);

		//when(userInfoService.getUserInfo()).thenReturn(newUser);
		doThrow(new BadRequestException("ERROR: There is already a request for the interval.")).when(leaveManageService).validateRequest(any(), any());
		
		this.mockMvc.perform(post("/apply-leave").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(leaveDetailsNew)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetAllLeaves() throws Exception {

		LeaveDetailsDto leaveDetails1 = ModelBuilder.constructLeaveDetailsDto(1, "johnsnow@email.com", "Snow", sdf.parse("18-04-2019 11:30:45"),
				sdf.parse("18-05-2019 11:30:45"), "Flu", "Medical", true, false);
		LeaveDetailsDto leaveDetails2 = ModelBuilder.constructLeaveDetailsDto(1, "johnsnow@email.com", "Snow", sdf.parse("04-08-2020 11:30:45"),
				sdf.parse("24-08-2020 11:30:45"), "Cold", "Medical", true, false);
		
		List<YearlyTimeOffDto> yearlyTimeOffList = Stream.of(ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("03-04-2020 11:30:45"), sdf.parse("05-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("10-04-2020 11:30:45"), sdf.parse("10-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("20-04-2020 11:30:45"), sdf.parse("21-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("25-04-2020 11:30:45"), sdf.parse("27-04-2020 11:30:45")),
				ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("05-05-2020 11:30:45"), sdf.parse("06-05-2020 11:30:45"))).collect(Collectors.toList());
		
		//when(userInfoService.getUserInfo()).thenReturn(ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22));
		when(leaveManageService.getAllLeavesOfUser("johnsnow@email.com"))
				.thenReturn(Stream.of(leaveDetails1, leaveDetails2).collect(Collectors.toList()));
		when(yearlyTimeOffService.getAllTimeOff()).thenReturn(yearlyTimeOffList);
		
		this.mockMvc
				.perform(get("/get-all-leaves").param("pending", "false").param("accepted", "true")
						.param("rejected", "false"))
				.andDo(print()).andExpect(status().isOk());
		verify(leaveManageService, times(1)).getAllLeavesOfUser("johnsnow@email.com");
	}
	
	@Test
	public void testManageLeaves() throws Exception {

		this.mockMvc.perform(get("/manage-leaves")).andExpect(status().isOk())
				.andExpect(view().name("manageLeaves")).andExpect(model().attributeExists("leavesList"));
	}
	
	@Test
	public void testAcceptOrRejectLeaves() throws Exception {

		LeaveDetailsDto leaveDetails = ModelBuilder.constructLeaveDetailsDto(1, "johnsnow@email.com", "John Snow", sdf.parse("18-04-2019 11:30:45"),
				sdf.parse("18-05-2019 11:30:45"), "Flu", "Medical", true, false);
	//	UserInfoDto userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		
		when(leaveManageService.getLeaveDetailsOnId(1)).thenReturn(leaveDetails);
		//when(userInfoService.findUserByEmail("johnsnow@email.com")).thenReturn(userInfo);
		//doNothing().when(userInfoService).saveUser(userInfo);
		doNothing().when(leaveManageService).updateLeaveDetails(leaveDetails);
		this.mockMvc.perform(get("/manage-leaves/{action}/{id}", "accept", 1))
				.andExpect(status().is3xxRedirection()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers
						.redirectedUrl("/user/manage-leaves?successMessage=Updated+Successfully%21"))
				.andExpect(model().attribute("successMessage", "Updated Successfully!"));

		verify(leaveManageService, times(1)).getLeaveDetailsOnId(1);
	}
	
	@Test
	public void testShowMyLeaves() throws Exception {

		//UserInfoDto userInfo1 = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		//when(userInfoService.getUserInfo()).thenReturn(userInfo1);
		//when(leaveManageService.getAllLeavesOfUser(userInfo1.getEmail())).thenReturn(Stream
				//.of(ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("31-08-2019 10:20:56"),
					////	sdf.parse("31-09-2019 10:20:56"), "Sick", "Medical", true, false))
				//.collect(Collectors.toList()));
		this.mockMvc.perform(get("/my-leaves")).andExpect(status().isOk()).andExpect(view().name("myLeaves"))
				.andExpect(model().attributeExists("leavesList"));
		//verify(leaveManageService, times(1)).getAllLeavesOfUser(userInfo1.getEmail());
	}

	@Test
	public void testSubmitApplyLeaveWithErrors() throws ParseException, Exception {
	//	UserInfoDto newUser = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		LeaveDetailsDto leaveDetailsNew =  ModelBuilder.constructLeaveDetailsDto(1, "John", "Snow", sdf.parse("04-08-2019 11:30:45"),
				sdf.parse("24-08-2019 11:30:45"), "Sick", "Medical", true, false);
		Errors errors = new Errors();
		errors.addMessage(new ErrorMessage("Attributes are null, they must be filled"));
	//	when(userInfoService.getUserInfo()).thenReturn(newUser);
		
		this.mockMvc.perform(post("/apply-leave").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(leaveDetailsNew)).content(JsonHelper.fromJsonToString(errors)))
				.andExpect(status().isBadRequest());
	}
}
