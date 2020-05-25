package com.infosys.internal.portal.claims.controllers;

import com.infosys.internal.portal.claims.model.ClaimsEntity;
import com.infosys.internal.portal.claims.service.ClaimsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ClaimsControllerTest {

	private static ClaimsEntity claim1, claim2;

	private MockMvc mockMvc;

	@Mock
	ClaimsServiceImpl claimsService;

	@InjectMocks
	ClaimsController claimsController = new ClaimsController();

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(claimsController).build();
		MockitoAnnotations.initMocks(this);
	}

	@BeforeAll
	public static void init() {
		claim1 = new ClaimsEntity();
		claim1.setId(1L);
		claim1.setUserId(1L);
		claim1.setFirstName("FirstName");
		claim1.setLastName("LastName");
		claim1.setEmail("mymail@email.com");
		claim1.setStatus("mystatus");
		claim1.setProfilePicPath("/profilepic");

		claim2 = new ClaimsEntity();
		claim2.setId(2L);
		claim2.setUserId(2L);
		claim2.setFirstName("2FirstName");
		claim2.setLastName("2LastName");
		claim2.setEmail("2mymail@email.com");
		claim2.setStatus("2mystatus");
		claim2.setProfilePicPath("2/profilepic");

	}

	@Test
	public void getAllClaims_WhenNoRecord() throws Exception {

		Mockito.when(claimsService.getAllClaims()).thenReturn(Arrays.asList());

		MvcResult result = this.mockMvc.perform(get("/list-claims-link")) // same here
				.andExpect(status().isOk()).andExpect(view().name("list-claims"))
				.andExpect(model().attributeExists("claims")).andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claims"), new ArrayList<>());
	}

	@Test
	public void getAllClaims_WhenRecord() throws Exception {

		Mockito.when(claimsService.getAllClaims()).thenReturn(Arrays.asList(claim1, claim2));

		MvcResult result = this.mockMvc.perform(get("/list-claims-link")) // circular view path so i changed the method
				// invocation - use the view resolver
				.andExpect(status().isOk()).andExpect(view().name("list-claims"))
				.andExpect(model().attributeExists("claims")).andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claims"), Arrays.asList(claim1, claim2));
	}

	@Test // the {id} hould be removed from Controller because the endpoint does not
	// accept PathVariable in this case
	public void editEmployee_WhenMatch() throws Exception {

		// TODO FixOrNot: the controller method editEmployee cannot be called with id
		// and therefore the first branch cannot be tested

		this.mockMvc.perform(get("/edit")).andExpect(status().isOk()).andExpect(view().name("add-edit-claims"))
				.andExpect(model().attributeExists("claim")).andDo(MockMvcResultHandlers.print()).andReturn();

	}

	@Test
	public void updateEmployeeById__WhenMatch() throws Exception {

		// TODO FixOrNot: the controller method updateEmployeeById cannot be called
		// without id and therefore the last branch cannot be tested

		Optional<ClaimsEntity> optionalClaim1 = Optional.of(claim1);
		Mockito.when(claimsService.getClaimsById(1L)).thenReturn(optionalClaim1);

		MvcResult result = this.mockMvc.perform(get("/edit/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name("add-edit-claims")).andExpect(model().attributeExists("claim"))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claim"), optionalClaim1);
	}

	@Test
	public void deleteEmployeeById_WhenFound() throws Exception {

		Mockito.doNothing().when(claimsService).deleteClaimById(1L);

		this.mockMvc.perform(get("/delete/{id}", 1L)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.forwardedUrl("/list-claims")).andDo(MockMvcResultHandlers.print())
				.andReturn();

		Mockito.verify(claimsService, Mockito.times(1)).deleteClaimById(1L);

	}

	@Test
	public void createOrUpdateEmployee_WhenFound() throws Exception {

		Mockito.when(claimsService.createOrUpdateClaims(claim1)).thenReturn(claim1);

		this.mockMvc.perform(post("/createClaim").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(Arrays.asList(claim1, claim2).toString()))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.forwardedUrl("/list-claims")).andReturn();

	}

	// HR
	@Test
	public void getAllClaimsHrPage_WhenRecord() throws Exception {

		Mockito.when(claimsService.getAllClaimsSpecificHRPage()).thenReturn(Arrays.asList(claim1, claim2));

		MvcResult result = this.mockMvc.perform(get("/hr_page-link")) // same here
				.andExpect(status().isOk()).andExpect(view().name("hr_page"))
				.andExpect(model().attributeExists("claims")).andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claims"), Arrays.asList(claim1, claim2));
	}

	@Test
	public void editareEmployeeHrClaimsById_WhenRecord() throws Exception {

		Optional<ClaimsEntity> optionalClaim1 = Optional.of(claim1);
		Mockito.when(claimsService.getClaimsById(1L)).thenReturn(optionalClaim1);

		MvcResult result = this.mockMvc.perform(get("/edit_hr_page/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name("add-edit-claims_hr")).andExpect(model().attributeExists("claim"))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claim"), optionalClaim1);
	}

	@Test
	public void deleteAsHrRoleEmployeeById_WhenRecord() throws Exception {

		Mockito.doNothing().when(claimsService).deleteClaimById(1L);

		this.mockMvc.perform(get("/deletedall/{id}", 1L)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.forwardedUrl("/hr_page")).andDo(MockMvcResultHandlers.print())
				.andReturn();

		Mockito.verify(claimsService, Mockito.times(1)).deleteClaimById(1L);

	}

	@Test
	public void createOrUpdateAsHrClaimsEmployee_WhenFound() throws Exception {

		Mockito.when(claimsService.createOrUpdateClaims(claim1)).thenReturn(claim1);

		this.mockMvc.perform(post("/createClaimHr").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(Arrays.asList(claim1, claim2).toString()))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.forwardedUrl("/hr_page")).andReturn();

	}

	// MANAGER

	@Test
	public void getAllManagerClaims_WhenRecord() throws Exception {

		Mockito.when(claimsService.getAllClaimsSpecificManagerPage()).thenReturn(Arrays.asList(claim1, claim2));

		MvcResult result = this.mockMvc.perform(get("/manager_page-link")).andExpect(status().isOk())
				.andExpect(view().name("manager_page")).andExpect(model().attributeExists("claims"))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claims"), Arrays.asList(claim1, claim2));
	}

	@Test
	public void editareEmployeeManagerClaimsById_WhenRecord() throws Exception {

		Optional<ClaimsEntity> optionalClaim1 = Optional.of(claim1);
		Mockito.when(claimsService.getClaimsById(1L)).thenReturn(optionalClaim1);

		MvcResult result = this.mockMvc.perform(get("/edit_claim_status_manager/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name("edit_status_manager")).andExpect(model().attributeExists("claim"))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		Assertions.assertEquals(result.getModelAndView().getModel().get("claim"), optionalClaim1);
	}

	@Test
	public void deletedAsManagerRoleEmployeeById_WhenRecord() throws Exception {

		Mockito.doNothing().when(claimsService).deleteClaimById(1L);

		this.mockMvc.perform(get("/deleted/{id}", 1L)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.forwardedUrl("/manager_page")).andDo(MockMvcResultHandlers.print())
				.andReturn();

		Mockito.verify(claimsService, Mockito.times(1)).deleteClaimById(1L);

	}

	@Test
	public void createOrUpdateManagerClaimsEmployee_WhenFound() throws Exception {

		Mockito.when(claimsService.createOrUpdateClaims(claim1)).thenReturn(claim1);

		this.mockMvc.perform(post("/createClaimManager").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(Arrays.asList(claim1, claim2).toString()))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.forwardedUrl("/manager_page")).andReturn();

	}

}