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

public class LeaveDetailsTest {

	private Validator validator;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	@BeforeEach
	public void setup() throws ParseException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
    public void testValidateSuccessfullyLeaveDetails() throws ParseException{
		LeaveDetails leaveDetails = constructLeaveDetails();
        Set<ConstraintViolation<LeaveDetails>> violations = validator.validate(leaveDetails);
       // assertTrue(violations.isEmpty());
    }
	
	@Test
	public void testFromDateIsNull() throws ParseException {
		LeaveDetails leaveDetails = constructLeaveDetails();
		leaveDetails.setFromDate(null);
		Set<ConstraintViolation<LeaveDetails>> violations = validator.validate(leaveDetails);
		//assertEquals(1, violations.size());
		//assertEquals("Please provide start date!", violations.iterator().next().getMessage());
	}
	
	@Test
	public void testToDateIsNull() throws ParseException {
		LeaveDetails leaveDetails = constructLeaveDetails();
		leaveDetails.setToDate(null);
		Set<ConstraintViolation<LeaveDetails>> violations = validator.validate(leaveDetails);
		//assertEquals(1, violations.size());
		//assertEquals("Please provide end date!", violations.iterator().next().getMessage());
	}
	
	@Test
	public void testLeaveTypeIsNull() throws ParseException {
		LeaveDetails leaveDetails = constructLeaveDetails();
		leaveDetails.setLeaveType(null);
		Set<ConstraintViolation<LeaveDetails>> violations = validator.validate(leaveDetails);
		//assertEquals(1, violations.size());
		//assertEquals("Please select type of leave!", violations.iterator().next().getMessage());
	}
	
	@Test
	public void testReasonIsNull() throws ParseException {
		LeaveDetails leaveDetails = constructLeaveDetails();
		leaveDetails.setReason(null);
		Set<ConstraintViolation<LeaveDetails>> violations = validator.validate(leaveDetails);
		//assertEquals(1, violations.size());
		//assertEquals("Please provide a reason for the leave!", violations.iterator().next().getMessage());
	}
	
	public LeaveDetails constructLeaveDetails() throws ParseException {
		LeaveDetails leaveDetails = new LeaveDetails();
		leaveDetails.setId(1);
		leaveDetails.setUsername("John");
		leaveDetails.setEmployeeName("Snow");
		leaveDetails.setFromDate(sdf.parse("20-11-2019 10:20:56"));
		leaveDetails.setToDate(sdf.parse("25-11-2019 10:20:56"));
		leaveDetails.setAcceptRejectFlag(false);
		leaveDetails.setActive(false);
		leaveDetails.setLeaveType("SICK");
		leaveDetails.setReason("Flu");
		return leaveDetails;
	}
}
