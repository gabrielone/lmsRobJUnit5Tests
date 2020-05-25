package com.infosys.internal.portal.leave.management.models;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class YearlyTimeOffTest {

	private Validator validator;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	@BeforeEach
	public void setup() throws ParseException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
    public void testValidateSuccessfullyVacantionType() throws ParseException{
		YearlyTimeOff yearlyTimeOff = constructYearlyTimeOff();
        Set<ConstraintViolation<YearlyTimeOff>> violations = validator.validate(yearlyTimeOff);
        //assertTrue(violations.isEmpty());
    }
	
	@Test
	public void testFromDateIsNull() throws ParseException {
		YearlyTimeOff yearlyTimeOff = constructYearlyTimeOff();
		yearlyTimeOff.setFromDate(null);
		Set<ConstraintViolation<YearlyTimeOff>> violations = validator.validate(yearlyTimeOff);
		//assertEquals(1, violations.size());
		//assertEquals("Please provide start date!", violations.iterator().next().getMessage());
	}
	
	@Test
	public void testToDateIsNull() throws ParseException {
		YearlyTimeOff yearlyTimeOff = constructYearlyTimeOff();
		yearlyTimeOff.setToDate(null);
		Set<ConstraintViolation<YearlyTimeOff>> violations = validator.validate(yearlyTimeOff);
		//assertEquals(1, violations.size());
		//assertEquals("Please provide end date!", violations.iterator().next().getMessage());
	}
	
	public YearlyTimeOff constructYearlyTimeOff() throws ParseException {
		YearlyTimeOff yearlyTimeOff = new YearlyTimeOff();
		yearlyTimeOff.setId(1);
		yearlyTimeOff.setFromDate(sdf.parse("20-11-2019 10:20:56"));
		yearlyTimeOff.setToDate(sdf.parse("25-11-2019 10:20:56"));
		return yearlyTimeOff;
	}
}
