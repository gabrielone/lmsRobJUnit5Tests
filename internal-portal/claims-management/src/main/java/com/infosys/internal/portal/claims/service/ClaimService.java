package com.infosys.internal.portal.claims.service;

import java.util.List;
import java.util.Optional;

import com.infosys.internal.portal.claims.config.RecordNotFoundException;
import com.infosys.internal.portal.claims.model.ClaimsEntity;

public interface ClaimService {

	List<ClaimsEntity> getAllClaims();

	List<ClaimsEntity> getAllClaimsSpecificManagerPage();

	List<ClaimsEntity> getAllClaimsSpecificHRPage();

	Optional<ClaimsEntity> getClaimsByUserId(Long userId) throws RecordNotFoundException;

	void deleteClaimById(Long employeeId) throws RecordNotFoundException;

	Optional<ClaimsEntity> getClaimsById(Long id) throws RecordNotFoundException;

	ClaimsEntity createOrUpdateClaims(ClaimsEntity employee);

}
