package com.infosys.internal.portal.leave.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.exception.CustomizedResponseEntityExceptionHandler;
import com.infosys.internal.portal.leave.management.service.VacantionTypeService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
@ComponentScan(basePackages = {"com.infosys.internal.portal.leave.management.exception"})
class VacantionTypeControllerTest {
	
	private MockMvc mock;
	
	@InjectMocks
	VacantionTypeController vacantionTypeController;
	
	@Mock
	VacantionTypeService vacantionTypeService;
	
	private JacksonTester<VacantionTypeDto> jsonLeaveDetails;
	
	@BeforeEach
	public void setup() {
		
		JacksonTester.initFields(this, new ObjectMapper());
		this.mock = MockMvcBuilders.standaloneSetup(new CustomizedResponseEntityExceptionHandler(), vacantionTypeController).build();
	}
	
	@Test
	public void testApplyLeave() throws Exception {
		
		this.mock.perform(get("/create-vacationtype"))
					.andExpect(status().isOk())
					.andExpect(view().name(ApplicationView.CREATE_VACANTION_TYPE_VIEW))
					.andExpect(model().attributeExists(ApplicationView.VACANTIONTYPE_ATTRIBUTE));
	}

	@Test
	public void testSubmitApplyLeave() throws ParseException, Exception {

		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");

		this.mock.perform(post("/create-vacationtype").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(vacantionType)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSubmitApplyLeaveWithBadRequest() throws ParseException, Exception {
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");

		doThrow(new BadRequestException("ERROR: This vacantion type, already exist, the existing one is: " + vacantionType.getType())).when(vacantionTypeService).validateVacantionType(any());
		
		this.mock.perform(post("/create-vacationtype").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(vacantionType)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSubmitApplyLeaveWithErrors() throws ParseException, Exception {
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		Errors errors = new Errors();
		errors.addMessage(new ErrorMessage("Attributes are null, they must be filled"));
		
		this.mock.perform(post("/create-vacationtype").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonHelper.fromJsonToString(vacantionType)).content(JsonHelper.fromJsonToString(errors)))
				.andExpect(status().isBadRequest());
	}
}
