package com.infosys.internal.portal.leave.management.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.infosys.internal.portal.leave.management.models.LeaveDetails;

@Repository
public class LeaveManageNativeSqlRepo {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<LeaveDetails> getAllLeavesOnStatus(StringBuilder whereQuery) {

		Query query = entityManager.createNativeQuery("select * from leave_details where " + whereQuery,
				LeaveDetails.class);

		return query.getResultList();
	}
}
