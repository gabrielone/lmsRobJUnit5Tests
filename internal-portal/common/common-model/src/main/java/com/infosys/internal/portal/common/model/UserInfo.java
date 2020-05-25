package com.infosys.internal.portal.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "userinfo")
public class UserInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3531580686565149631L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "E-mail cannot be empty!")
    @Email(message = "Please provide a valid email!")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password cannot be empty!")
    @Length(min = 5, message = "Choose atleast five characters for password!")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Please provide a role!")
    @Column(name = "role")
    private String role;

    @NotEmpty(message = "Please provide first name!")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Please provide last name!")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active")
    private boolean active;
    
    @Column(name="status", nullable=false, length=200)
    private String status;
    
    
    



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
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}