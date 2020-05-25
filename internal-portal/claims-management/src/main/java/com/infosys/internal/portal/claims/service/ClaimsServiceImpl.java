package com.infosys.internal.portal.claims.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.internal.portal.claims.config.RecordNotFoundException;
import com.infosys.internal.portal.claims.model.ClaimsEntity;

import com.infosys.internal.portal.claims.repository.ClaimsRepository;
import com.infosys.internal.portal.common.dto.UserInfoDto;

@Service
public class ClaimsServiceImpl implements ClaimService {

	@Autowired
	public ClaimsRepository repository;

	@Autowired
	public ClaimsUserService claimsUserService;

	public List<ClaimsEntity> getAllClaims() {

		return (List<ClaimsEntity>) repository
				.getAllClaimByUserId(Long.valueOf(claimsUserService.getUserInfo().getId()));

	}

	public List<ClaimsEntity> getAllClaimsSpecificManagerPage() {
		return (List<ClaimsEntity>) repository.findAll();

	}

	public List<ClaimsEntity> getAllClaimsSpecificHRPage() {
		return (List<ClaimsEntity>) repository.findAll();

	}

	public Optional<ClaimsEntity> getClaimsById(Long id) throws RecordNotFoundException {

		return (Optional<ClaimsEntity>) repository.findById(id);

	}

	public Optional<ClaimsEntity> getClaimsByUserId(Long userId) throws RecordNotFoundException {
		return (Optional<ClaimsEntity>) repository.findById(userId);

	}

	public ClaimsEntity createOrUpdateClaims(ClaimsEntity entity) {

		UserInfoDto userInfo = claimsUserService.getUserInfo();

		entity.setUserId(Long.valueOf(userInfo.getId()));

		entity = repository.save(entity);

		Optional<ClaimsEntity> claims = this.repository.findById(entity.getId());

		ClaimsEntity newEntity = claims.get();
		newEntity.setEmail(entity.getEmail());
		newEntity.setFirstName(entity.getFirstName());
		newEntity.setLastName(entity.getLastName());
		newEntity.setStatus(entity.getStatus());
		newEntity.setProfilePicPath(entity.getProfilePicPath());

		newEntity = repository.save(newEntity);

		return newEntity;

	}

	public void deleteClaimById(Long id) throws RecordNotFoundException {

		repository.deleteById(id);

	}

}