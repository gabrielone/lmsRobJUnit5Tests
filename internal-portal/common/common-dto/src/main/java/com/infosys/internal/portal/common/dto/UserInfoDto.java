package com.infosys.internal.portal.common.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class UserInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6064574299409914308L;

	private int id;

	@NotEmpty(message = "E-mail cannot be empty!")
	@Email(message = "Please provide a valid email!")
	private String email;

	@NotEmpty(message = "Password cannot be empty!")
	@Length(min = 5, message = "Choose atleast five characters for password!")
	private String password;

	@NotEmpty(message = "Please provide a role!")
	private String role;

	@NotEmpty(message = "Please provide first name!")
	private String firstName;

	@NotEmpty(message = "Please provide last name!")
	private String lastName;

	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
