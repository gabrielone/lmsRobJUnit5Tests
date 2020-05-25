package com.infosys.internal.portal.leave.management.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "vacantion_type")
public class VacantionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty(message = "Please select type of vacantion!")
	@Column(name = "type")
	@Valid
	private String type;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "yearlytimeoff_id")
	private YearlyTimeOff yearlyTimeOff;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public YearlyTimeOff getYearlyTimeOff() {
		return yearlyTimeOff;
	}

	public void setYearlyTimeOff(YearlyTimeOff yearlyTimeOff) {
		this.yearlyTimeOff = yearlyTimeOff;
	}

}
