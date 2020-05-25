package com.infosys.internal.portal.claims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.infosys.internal.portal.claims.model.ClaimsEntity;

@Repository
public interface ClaimsRepository extends JpaRepository<ClaimsEntity, Long> {

	@Query(nativeQuery = true, value = "select * from tbl_employees where user_id = :userId")
	public List<ClaimsEntity> getAllClaimByUserId(@Param("userId") Long userId);

}
