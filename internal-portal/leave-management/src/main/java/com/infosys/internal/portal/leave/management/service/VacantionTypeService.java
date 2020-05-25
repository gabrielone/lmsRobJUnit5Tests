package com.infosys.internal.portal.leave.management.service;

import java.util.List;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.leave.management.dto.LeaveDetailsDto;
import com.infosys.internal.portal.leave.management.dto.VacantionTypeDto;
import com.infosys.internal.portal.leave.management.models.VacantionType;

public interface VacantionTypeService {
	void save(VacantionTypeDto newVacantionType);

	VacantionTypeDto getByType(String type);

	List<VacantionTypeDto> getAll();

	VacantionTypeDto convertToDto(VacantionType details);

	VacantionType convertDtoToEntity(VacantionTypeDto details);

	VacantionTypeDto findById(String id);

	VacantionType findById(int id);
	
	public void validateVacantionType(VacantionTypeDto newVacantionType);

}
