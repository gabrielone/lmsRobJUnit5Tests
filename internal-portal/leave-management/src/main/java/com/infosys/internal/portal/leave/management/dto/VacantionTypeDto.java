package com.infosys.internal.portal.leave.management.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class VacantionTypeDto {

	private int id;

	@NotEmpty(message = "Please select type of vacantion!")
	@Valid
	private String type;

	private YearlyTimeOffDto yearlyTimeOff;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public YearlyTimeOffDto getYearlyTimeOff() {
		return yearlyTimeOff;
	}

	public void setYearlyTimeOff(YearlyTimeOffDto yearlyTimeOff) {
		this.yearlyTimeOff = yearlyTimeOff;
	}

}
