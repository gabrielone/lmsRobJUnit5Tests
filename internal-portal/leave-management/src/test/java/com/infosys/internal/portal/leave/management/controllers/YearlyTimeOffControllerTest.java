package com.infosys.internal.portal.leave.management.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.exception.CustomizedResponseEntityExceptionHandler;
import com.infosys.internal.portal.leave.management.service.VacantionTypeService;
import com.infosys.internal.portal.leave.management.service.YearlyTimeOffService;
import com.infosys.internal.portal.leave.management.util.ApplicationView;
import com.infosys.internal.portal.leave.management.util.JsonHelper;
import com.infosys.internal.portal.leave.management.util.ModelBuilder;

@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
@ComponentScan(basePackages = {"com.infosys.internal.portal.leave.management.exception"})
class YearlyTimeOffControllerTest {

	private MockMvc mockMvc;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	@InjectMocks
	YearlyTimeOffController yearlyTimeOffController;

	@Mock
	YearlyTimeOffService yearlyTimeOffService;
	
	@Mock
	VacantionTypeService vacantionTypeService;
	
	@BeforeEach
	public void setup() {
		// Setup Spring test in webapp-mode (same config as spring-boot)
		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomizedResponseEntityExceptionHandler(), yearlyTimeOffController).build();
	}
	
	@Test
	public void testApplyLeave() throws Exception {
		
		VacantionTypeDto vacantionType1 = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		VacantionTypeDto vacantionType2 = ModelBuilder.constructVacantionTypeDto(2, "Paste");
		
		when(vacantionTypeService.getAll()).thenReturn(Stream.of(vacantionType1, vacantionType2).collect(Collectors.toList()));

		this.mockMvc.perform(get("/apply-yearly-time-off"))
					.andExpect(status().isOk())
					.andExpect(view().name(ApplicationView.APPLY_YEARRLY_TIME_OFF_VIEW))
					.andExpect(model().attributeExists(ApplicationView.YEARLY_TIME_OFF_ATTRIBUTE))
					.andExpect(model().attributeExists(ApplicationView.VACANTIONTYPE_LIST));
	}
	
	@Test
	public void testSubmitApplyLeave() throws Exception {

		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		YearlyTimeOffDto yearlyTimeOff = ModelBuilder.constructYearlyTimeOffDto(1, sdf.parse("30-10-2019 10:20:56"),
				sdf.parse("20-11-2019 10:20:56"), vacantionType, "1");

		when(vacantionTypeService.findById(yearlyTimeOff.getVacantionTypeId())).thenReturn(vacantionType);

		this.mockMvc.perform(post("/apply-yearly-time-off").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(yearlyTimeOff)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSubmitApplyLeaveWithBadRequest() throws ParseException, Exception {
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		YearlyTimeOffDto yearlyTimeOff = ModelBuilder.constructYearlyTimeOffDto(1, sdf.parse("30-10-2019 10:20:56"),
				sdf.parse("20-11-2019 10:20:56"), vacantionType, "1");

		when(vacantionTypeService.findById(yearlyTimeOff.getVacantionTypeId())).thenReturn(vacantionType);
		doThrow(new BadRequestException("Please select an Existing Vacantion Type, or create a new one")).when(yearlyTimeOffService).validateTimeOffDetails(any());
		
		this.mockMvc.perform(post("/apply-yearly-time-off").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(yearlyTimeOff)))
				.andExpect(status().isBadRequest());
	}
}
