package com.infosys.internal.portal.leave.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infosys.internal.portal.leave.management.models.VacantionType;

public interface VacantionTypeRepository extends JpaRepository<VacantionType, Long> {

	@Query(value = "select * from vacantion_type where type = :typeName", nativeQuery = true)
	public VacantionType getByType(@Param("typeName") String typeName);

	@Query(value = "select * from vacantion_type", nativeQuery = true)
	public List<VacantionType> getAll();

}
