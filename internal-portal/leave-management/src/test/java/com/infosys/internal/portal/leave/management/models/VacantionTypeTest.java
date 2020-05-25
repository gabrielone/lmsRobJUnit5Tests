package com.infosys.internal.portal.leave.management.models;

///import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VacantionTypeTest {

	private Validator validator;
	
	@BeforeEach
	public void setup() throws ParseException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
    public void testValidateSuccessfullyVacantionType() throws ParseException{
		VacantionType vacantionType = constructVacantionType();
        Set<ConstraintViolation<VacantionType>> violations = validator.validate(vacantionType);
       // assertTrue(violations.isEmpty());
    }
	
	@Test
	public void testTypeIsEmpty() throws ParseException {
		VacantionType vacantionType = constructVacantionType();
		vacantionType.setType("");
		Set<ConstraintViolation<VacantionType>> violations = validator.validate(vacantionType);
		//assertEquals(1, violations.size());
		//assertEquals("Please select type of vacantion!", violations.iterator().next().getMessage());
	}
	
	public VacantionType constructVacantionType() {
		VacantionType vacantionType = new VacantionType();
		vacantionType.setId(1L);
		vacantionType.setType("Craciun");
		return vacantionType;
	}
}
