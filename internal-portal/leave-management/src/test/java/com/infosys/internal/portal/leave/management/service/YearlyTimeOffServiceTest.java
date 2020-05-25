package com.infosys.internal.portal.leave.management.service;

//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.models.VacantionType;
import com.infosys.internal.portal.leave.management.models.YearlyTimeOff;
import com.infosys.internal.portal.leave.management.repository.VacantionTypeRepository;
import com.infosys.internal.portal.leave.management.repository.YearlyTimeOffRepository;
import com.infosys.internal.portal.leave.management.util.ModelBuilder;

@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
class YearlyTimeOffServiceTest {

	@InjectMocks
	YearlyTimeOffServiceImpl service;

	@Mock
	YearlyTimeOffRepository repository;

	@Mock
	VacantionTypeServiceImpl vacantionTypeService;
	
	@Mock
	VacantionTypeRepository vacantionTypeRepository;

	private MockMvc mockMvc;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}
	
	@Test
	public void convertToDtoTest() throws ParseException {
		
		 VacantionType vacantionType = ModelBuilder.constructVacantionType(1L, "Easter");
		 YearlyTimeOff timeOff = ModelBuilder.constructYearlyTimeOff(1,sdf.parse("04-04-2019 11:30:45"),sdf.parse("24-04-2019 11:30:45"));
		 timeOff.setVacantionType(vacantionType);	    
		// assertTrue(service.convertToDto(timeOff).getClass()== YearlyTimeOffDto.class);
		
	}
	
	@Test
	public void convertDtoToEntity() throws ParseException {
		VacantionType vacantion = new VacantionType();
		vacantion.setId(1L);
		vacantion.setType("Paste");
		
		VacantionTypeDto vacantionType = ModelBuilder.constructVacantionTypeDto(1, "Paste");
		YearlyTimeOff timeOff = ModelBuilder.constructYearlyTimeOff(1,sdf.parse("04-04-2019 11:30:45"), sdf.parse("24-04-2019 11:30:45"));
		YearlyTimeOffDto timeOffDto = ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("04-04-2019 11:30:45"), sdf.parse("24-04-2019 11:30:45"), vacantionType, "1");
		when(vacantionTypeService.findById(1)).thenReturn(vacantion);
		//assertTrue(service.convertDtoToEntity(timeOffDto).getClass()== YearlyTimeOff.class);
		
	}


	@Test
	public void getAllTimeOffTest() throws Exception {
		
		YearlyTimeOff timeOff1 = ModelBuilder.constructYearlyTimeOff(1,sdf.parse("04-04-2019 11:30:45"), sdf.parse("24-04-2019 11:30:45"));
		YearlyTimeOff timeOff2 = ModelBuilder.constructYearlyTimeOff(2,sdf.parse("04-04-2019 11:30:45"), sdf.parse("24-04-2019 11:30:45"));
			
		when(repository.findAll())
		.thenReturn(Stream.of(timeOff1, timeOff2).collect(Collectors.toList()));
				
		assertEquals(2, service.getAllTimeOff().size());
	}
	
	@Test
	public void validateTimeOffDetailsTest() throws Exception {
		YearlyTimeOffDto timeOffDto = ModelBuilder.constructYearlyTimeOffDto(1,sdf.parse("04-04-2019 11:30:45"), sdf.parse("24-04-2019 11:30:45"));

		try {
			service.validateTimeOffDetails(timeOffDto);
		    fail( "My method didn't throw when I expected it to" );
		} catch (BadRequestException expectedException) {
			String expectedMessage = "Please select an Existing Vacantion Type, or create a new one";
		    assertEquals(expectedMessage, expectedException.getMessage());
		}
	}
}