package com.infosys.internal.portal.claims.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.infosys.internal.portal.claims.model.ClaimsEntity;
import com.infosys.internal.portal.claims.repository.ClaimsRepository;
import com.infosys.internal.portal.common.dto.UserInfoDto;

import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
public class ClaimServiceImplTest {

	private static ClaimsEntity claim1, claim2;

	@Mock
	ClaimsUserIServiceImpl claimsUserService;

	@Mock
	ClaimsRepository repository;

	@InjectMocks
	ClaimsServiceImpl service;

	@BeforeEach
	public void setup() {
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
	public void getAllClaims_test() {

		UserInfoDto res = new UserInfoDto();
		res.setId(1);

		List<ClaimsEntity> claims = new ArrayList<ClaimsEntity>();
		claims.add(claim1);
		claims.add(claim2);

		Mockito.when(repository.getAllClaimByUserId(any(Long.class))).thenReturn(claims);
		Mockito.when(claimsUserService.getUserInfo()).thenReturn(res);

		Assertions.assertEquals(service.getAllClaims().size(), 2);

		List<ClaimsEntity> results = service.getAllClaims();
		Assertions.assertEquals(results.get(0).getId(), claim1.getId());

	}

	@Test
	public void getAllClaimsSpecificManagerPage_test() {

		List<ClaimsEntity> claims = new ArrayList<ClaimsEntity>();
		claims.add(claim1);
		claims.add(claim2);

		Mockito.when(repository.findAll()).thenReturn(claims);

		Assertions.assertEquals(service.getAllClaimsSpecificManagerPage().size(), 2);

		List<ClaimsEntity> results = service.getAllClaimsSpecificManagerPage();
		Assertions.assertEquals(results.get(0).getId(), claim1.getId());

	}

	@Test
	public void getAllClaimsSpecificHRPage_test() {

		List<ClaimsEntity> claims = new ArrayList<ClaimsEntity>();
		claims.add(claim1);
		claims.add(claim2);

		Mockito.when(repository.findAll()).thenReturn(claims);

		Assertions.assertEquals(service.getAllClaimsSpecificHRPage().size(), 2);

		List<ClaimsEntity> results = service.getAllClaimsSpecificHRPage();
		Assertions.assertEquals(results.get(0).getId(), claim1.getId());

	}

	@Test
	public void getClaimsById_test() throws Exception {

		Optional<ClaimsEntity> optionalClaim1 = Optional.of(claim1);

		Mockito.when(repository.findById(1L)).thenReturn(optionalClaim1);

		Assertions.assertEquals(service.getClaimsById(1L).isPresent(), true);

		Optional<ClaimsEntity> results = service.getClaimsById(1L);
		Assertions.assertEquals(results.get().getId(), claim1.getId());

	}

	@Test
	public void getClaimsByUserId_test() throws Exception {

		Optional<ClaimsEntity> optionalClaim1 = Optional.of(claim1);

		Mockito.when(repository.findById(1L)).thenReturn(optionalClaim1);

		Assertions.assertEquals(service.getClaimsByUserId(1L).isPresent(), true);

		Optional<ClaimsEntity> results = service.getClaimsByUserId(1L);
		Assertions.assertEquals(results.get().getId(), claim1.getId());

	}

	@Test
	public void createOrUpdateClaims_test() throws Exception {

		// TODO Fix: method with redundant code(save again another entity) + exception
		// in optional if not exists
		claim1 = new ClaimsEntity();
		claim1.setId(1L);
		claim1.setFirstName("FirstName");
		claim1.setLastName("LastName");
		claim1.setEmail("mymail@email.com");
		claim1.setStatus("mystatus");
		claim1.setProfilePicPath("/profilepic");

		claim2 = new ClaimsEntity();
		claim2.setId(1L);
		claim2.setUserId(1L);
		claim2.setFirstName("FirstName");
		claim2.setLastName("LastName");
		claim2.setEmail("mymail@email.com");
		claim2.setStatus("mystatus");
		claim2.setProfilePicPath("/profilepic");

		Optional<ClaimsEntity> optionalClaim1 = Optional.of(claim1);

		UserInfoDto userInfo = new UserInfoDto();
		userInfo.setId(1);

		Mockito.when(claimsUserService.getUserInfo()).thenReturn(userInfo);
		Mockito.when(repository.save(claim1)).thenReturn(claim1);
		Mockito.when(repository.findById(1L)).thenReturn(optionalClaim1);

		Assertions.assertEquals(service.createOrUpdateClaims(claim1).getUserId(), claim2.getUserId());

	}

	@Test
	public void deleteClaimById_test() throws Exception {
		service.deleteClaimById(1L);
		Mockito.doNothing().when(repository).deleteById(1L);
		Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
	}

}