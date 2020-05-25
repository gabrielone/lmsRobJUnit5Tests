package com.infosys.internal.portal.leave.management.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infosys.internal.portal.leave.management.models.LeaveDetails;
import com.infosys.internal.portal.leave.management.models.YearlyTimeOff;

@Repository(value = "timeOffManageRepository")
public interface YearlyTimeOffRepository extends JpaRepository<YearlyTimeOff, Serializable> {

	@Query(nativeQuery = true, value = "select * from yearly_time_off")
	public List<LeaveDetails> getAllTimeOff();

	@Query(value = "select * from yearly_time_off y where y.from_date = :from_date and y.to_date = :to_date", nativeQuery = true)
	public List<YearlyTimeOff> findByDate(@Param("from_date") Date from_date, @Param("to_date") Date to_date);
}
