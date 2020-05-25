package com.infosys.internal.portal.claims.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.infosys.internal.portal.claims.model.ClaimsEntity;

@Repository
public class ClaimsNativeSqlRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<ClaimsEntity> getById(StringBuilder whereQuery) {

		Query query = entityManager.createNativeQuery("select * from tbl_employees where " + whereQuery,
				ClaimsEntity.class);

		return query.getResultList();
	}
}