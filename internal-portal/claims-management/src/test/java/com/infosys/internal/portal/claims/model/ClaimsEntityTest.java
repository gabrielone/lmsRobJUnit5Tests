package com.infosys.internal.portal.claims.model;

import com.infosys.internal.portal.claims.model.ClaimsEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ClaimsEntityTest {

	@Test
	public void testGettersAndSetters() {
		ClaimsEntity claim = new ClaimsEntity();
		claim.getUserId();
		claim.getFirstName();
		claim.getFirstName();
		claim.getEmail();
		claim.getStatus();
		claim.getProfilePicPath();
		claim.setUserId(1L);
		claim.setFirstName("John");
		claim.setFirstName("Doe");
		claim.setEmail("john.doe@email.com");
		claim.setStatus("StatusAsString?"); // is this a String?
		claim.setProfilePicPath("long/path/until/we/reach/the/end/of/the/world");

	}

	@Test
	public void testConstructorWithArgs() {
		ClaimsEntity claim = new ClaimsEntity(); // no constructor so no hibernate initialization for the entity
	}
}
