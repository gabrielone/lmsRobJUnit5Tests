package com.infosys.internal.portal.leave.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.util.Optional;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.exception.CustomizedResponseEntityExceptionHandler;
import com.infosys.internal.portal.leave.management.repository.VacantionTypeRepository;
import com.infosys.internal.portal.leave.management.util.ModelBuilder;

@ExtendWith(SpringExtension.class)
///@RunWith(MockitoJUnitRunner.class)
@ComponentScan(basePackages = {"com.infosys.internal.portal.leave.management.exception"})
class VacantionTypeServiceTest {

	@InjectMocks
	VacantionTypeServiceImpl service;
	
	private MockMvc mockMvc;
	
	@Mock
	VacantionTypeRepository repository;
	
	@BeforeEach
	public void setup() {

		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomizedResponseEntityExceptionHandler(),service).build();
	}
	
	@Test
	public void testSave() throws Exception {
		
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		service=Mockito.spy(service);
		service.save(vacantionType);
        verify(service, times(1)).save(vacantionType);
	}
	
	@Test
	public void testGetByType() throws Exception {
		
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		String type = "Craciun";
		
		when(repository.getByType(type))
				.thenReturn(service.convertDtoToEntity(vacantionType));
		assertEquals(1, service.getByType(type).getId());
	}
	
	@Test
	public void testGetAll() throws Exception {
		
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");
		VacantionTypeDto vacantionType1 = ModelBuilder.constructVacantionTypeDto(2, "Paste");
		
		when(repository.getAll()).thenReturn(Stream.of(service.convertDtoToEntity(vacantionType), 
				service.convertDtoToEntity(vacantionType1)).collect(Collectors.toList()));
		assertEquals(2, service.getAll().size());
	}
	
	@Test
	public void testFindByIdString() throws ParseException {

		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Craciun");

		when(repository.findById(Long.parseLong("1"))).thenReturn(Optional.of(service.convertDtoToEntity(vacantionType)));
		assertEquals(vacantionType.getType(), service.findById("1").getType());
		assertEquals(vacantionType.getId(), service.findById("1").getId());
	}
	
	@Test
	public void findByIdIntegerTest() throws Exception {

		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Paste");

		when(repository.findById(Long.valueOf(1))).thenReturn(Optional.of(service.convertDtoToEntity(vacantionType)));
		assertEquals(vacantionType.getType(), service.findById(1).getType());
	}
	
	@Test
	public void validateDifferentTypesTest() throws Exception {
		
		VacantionTypeDto vacantionTypeNew = ModelBuilder.constructVacantionTypeDto(2, "Craciun");
		
		when(repository.getByType("Craciun")).thenReturn(null);
		service=Mockito.spy(service);
		service.validateVacantionType(vacantionTypeNew);
        verify(service, times(1)).save(vacantionTypeNew);
	}
	
	@Test
	public void validateSameTypeTest() throws Exception {
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Paste");
		VacantionTypeDto vacantionTypeNew = ModelBuilder.constructVacantionTypeDto(2, "Paste");
		
		when(repository.getByType("Paste")).thenReturn(service.convertDtoToEntity(vacantionType));
		
		try {
			service.validateVacantionType(vacantionTypeNew);
		    fail( "My method didn't throw when I expected it to" );
		} catch (BadRequestException expectedException) {
			String expectedMessage = "ERROR: This vacantion type, already exist, the existing one is: " + vacantionType.getType();
		    assertEquals(expectedMessage, expectedException.getMessage());
		}
	}
}
