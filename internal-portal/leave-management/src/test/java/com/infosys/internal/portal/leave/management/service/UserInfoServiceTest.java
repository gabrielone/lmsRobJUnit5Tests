package com.infosys.internal.portal.leave.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.common.repository.UserInfoRepository;
import com.infosys.internal.portal.leave.management.util.ModelBuilder;

@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
class UserInfoServiceTest {

	@InjectMocks
	UserInfoServiceImpl userInfoService;
	
	private MockMvc mockMvc;
	
	UserInfoDto userInfo;
	
	@Mock
	UserInfoRepository userInfoRepository;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@BeforeEach
	public void setup() {

		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(userInfoService).build();
	}
	
	@Test
	public void testFindUserByEmail() {
	//	userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		String email = "johnsnow@email.com";
		when(userInfoRepository.findByEmail(email))
					.thenReturn(userInfoService.convertDtoToEntity(userInfo));
		assertEquals(userInfo.getFirstName(), userInfoService.findUserByEmail(email).getFirstName());
		assertEquals(userInfo.getLastName(), userInfoService.findUserByEmail(email).getLastName());
		assertEquals(userInfo.getId(), userInfoService.findUserByEmail(email).getId());
		assertEquals(userInfo.getEmail(), userInfoService.findUserByEmail(email).getEmail());
	}
	
	@Test
	public void testSaveUser() throws Exception {
		//userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		userInfo.setPassword("Password");
		userInfoService=Mockito.spy(userInfoService);
		userInfoService.saveUser(userInfo);
        verify(userInfoService, times(1)).saveUser(userInfo);
	}
	
	@Test
	public void testGetUsers() {
		//userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
	//	UserInfoDto userInfo2 = ModelBuilder.constructUserInfoDto(2, "Queen", "Els", "queenelsa@email.com",22);
		
		//when(userInfoRepository.findAllByOrderById()).thenReturn(Stream.of(
			//	userInfoService.convertDtoToEntity(userInfo), userInfoService.convertDtoToEntity(userInfo2))
			//	.collect(Collectors.toList()));
		
		assertEquals(2, userInfoService.getUsers().size());
	}
	
	@Test
	public void testGetUserById() {
		//userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		when(userInfoRepository.findById(1))
					.thenReturn(userInfoService.convertDtoToEntity(userInfo));
		assertEquals(userInfo.getFirstName(), userInfoService.getUserById(1).getFirstName());
		assertEquals(userInfo.getLastName(), userInfoService.getUserById(1).getLastName());
		assertEquals(userInfo.getId(), userInfoService.getUserById(1).getId());
		assertEquals(userInfo.getEmail(), userInfoService.getUserById(1).getEmail());
	}
	
	@Test
	public void testDeleteUser() throws Exception {
	//	userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		userInfoService=Mockito.spy(userInfoService);
		doNothing().when(userInfoRepository).deleteById(1);
		userInfoService.deleteUser(1);
        verify(userInfoService, times(1)).deleteUser(1);
	}
	
	@Test
	public void testBlockUser() throws Exception {
	//	userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		userInfoService=Mockito.spy(userInfoService);
		doNothing().when(userInfoRepository).blockUser(1);
		userInfoService.blockUser(1);
        verify(userInfoService, times(1)).blockUser(1);
	}
	
	@Test
	public void testUnBlockUser() throws Exception {
		//userInfo = ModelBuilder.constructUserInfoDto(1, "John", "Snow", "johnsnow@email.com",22);
		userInfoService=Mockito.spy(userInfoService);
		doNothing().when(userInfoRepository).unBlockUser(1);
		userInfoService.unBlockUser(1);
        verify(userInfoService, times(1)).unBlockUser(1);
	}
}
