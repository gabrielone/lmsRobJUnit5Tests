package com.infosys.internal.portal.leave.management.dto;

import java.util.Date;

import javax.persistence.Column;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class YearlyTimeOffDto {

	private int id;

	@NotNull(message = "Please provide start date!")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	private Date fromDate;

	@NotNull(message = "Please provide end date!")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	private Date toDate;

	@Column(name = "duration")
	private int duration;

	private VacantionTypeDto vacantionType;

	private String vacantionTypeId;

	public String getVacantionTypeId() {
		return vacantionTypeId;
	}

	public void setVacantionTypeId(String vacantionTypeId) {
		this.vacantionTypeId = vacantionTypeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public VacantionTypeDto getVacantionType() {
		return vacantionType;
	}

	public void setVacantionType(VacantionTypeDto vacantionType) {

		if (vacantionType == null) {
			if (this.vacantionType != null) {
				this.vacantionType.setYearlyTimeOff(null); // there is something already
			} else {
				vacantionType.setYearlyTimeOff(this);
			}
		}
		this.vacantionType = vacantionType;
	}

}
