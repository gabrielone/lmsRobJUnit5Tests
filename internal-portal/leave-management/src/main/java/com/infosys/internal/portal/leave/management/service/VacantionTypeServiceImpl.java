package com.infosys.internal.portal.leave.management.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.models.VacantionType;
import com.infosys.internal.portal.leave.management.repository.VacantionTypeRepository;

@Service(value = "vacantionTypeService")
public class VacantionTypeServiceImpl implements VacantionTypeService {
	@Autowired
	VacantionTypeRepository vacantionTypeRepository;

	// CRUD methods

	public void save(VacantionTypeDto newVacantionType) {
		vacantionTypeRepository.save(this.convertDtoToEntity(newVacantionType));
	}

	public VacantionTypeDto getByType(String type) {
		return this.convertToDto(vacantionTypeRepository.getByType(type));
	}

	public List<VacantionTypeDto> getAll() {

		return vacantionTypeRepository.getAll().stream().map(vacantion -> this.convertToDto(vacantion))
				.collect(Collectors.toList());
	}

	@Override
	public VacantionTypeDto convertToDto(VacantionType details) {

		if (details == null) {
			return null;
		}
		return new ModelMapper().map(details, VacantionTypeDto.class);
	}

	@Override
	public VacantionType convertDtoToEntity(VacantionTypeDto details) {

		return new ModelMapper().map(details, VacantionType.class);
	}

	@Override
	public VacantionTypeDto findById(String id) {

		Optional<VacantionType> vacantion = vacantionTypeRepository.findById(Long.parseLong(id));
		if (vacantion.isPresent()) {
			return this.convertToDto(vacantion.get());
		}
		return null;
	}

	@Override
	public VacantionType findById(int id) {

		Optional<VacantionType> vacantion = vacantionTypeRepository.findById(Long.valueOf(id));
		if (vacantion.isPresent()) {
			return vacantion.get();
		}
		return null;
	}

	@Override
	public void validateVacantionType(VacantionTypeDto newVacantionType) {
	
		VacantionTypeDto vacantionType = this.getByType(newVacantionType.getType());
		
		if (vacantionType == null) {
			this.save(newVacantionType);
		} else {
			throw new BadRequestException("ERROR: This vacantion type, already exist, the existing one is: " + vacantionType.getType());
		}
	}

}
