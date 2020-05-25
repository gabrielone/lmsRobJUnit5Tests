package com.infosys.internal.portal.leave.management.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "yearly_time_off")
public class YearlyTimeOff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotNull(message = "Please provide start date!")
	@Column(name = "from_date")
	private Date fromDate;

	@NotNull(message = "Please provide end date!")
	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "duration")
	private int duration;

	@OneToOne(mappedBy = "yearlyTimeOff", fetch = FetchType.EAGER)
	private VacantionType vacantionType;

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

	public VacantionType getVacantionType() {
		return vacantionType;
	}

	public void setVacantionType(VacantionType vacantionType) {

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
