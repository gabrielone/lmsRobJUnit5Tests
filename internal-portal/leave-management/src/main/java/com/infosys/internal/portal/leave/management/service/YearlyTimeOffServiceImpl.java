package com.infosys.internal.portal.leave.management.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;
import com.infosys.internal.portal.leave.management.exception.BadRequestException;
import com.infosys.internal.portal.leave.management.models.VacantionType;
import com.infosys.internal.portal.leave.management.models.YearlyTimeOff;
import com.infosys.internal.portal.leave.management.repository.YearlyTimeOffRepository;

@Service(value = "yearlyTimeOffService")
public class YearlyTimeOffServiceImpl implements YearlyTimeOffService {

	@Autowired
	private YearlyTimeOffRepository timeOffManageRepository;
	@Autowired
	private VacantionTypeServiceImpl vacantionTypeService;

	@SuppressWarnings("deprecation")
	public void applyTimeOff(YearlyTimeOffDto timeOffDetails) {

		int duration = timeOffDetails.getToDate().getDate() - timeOffDetails.getFromDate().getDate();
		timeOffDetails.setDuration(duration + 1);
		timeOffManageRepository.save(this.convertDtoToEntity(timeOffDetails));
	}

	public List<YearlyTimeOffDto> getAllTimeOff() {

		return timeOffManageRepository.findAll().stream().map(timeOff -> this.convertToDto(timeOff))
				.collect(Collectors.toList());
	}

	public List<YearlyTimeOffDto> findByDate(Date from_date, Date to_date) {

		return timeOffManageRepository.findByDate(from_date, to_date).stream()
				.map(timeOff -> this.convertToDto(timeOff)).collect(Collectors.toList());
	}

	public YearlyTimeOffDto getTimeOffDetailsOnId(int id) {

		Optional<YearlyTimeOff> yearlyTimeOff = timeOffManageRepository.findById(id);
		if (yearlyTimeOff.isPresent()) {
			return this.convertToDto(yearlyTimeOff.get());
		}

		return null;
	}

	public void updateLeaveDetails(YearlyTimeOffDto timeOffDetails) {

		timeOffManageRepository.save(this.convertDtoToEntity(timeOffDetails));

	}

	@Override
	public YearlyTimeOffDto convertToDto(YearlyTimeOff details) {

		if (details == null) {
			return null;
		}
		return new ModelMapper().map(details, YearlyTimeOffDto.class);
	}

	@Override
	public YearlyTimeOff convertDtoToEntity(YearlyTimeOffDto details) {

		YearlyTimeOff destination = new YearlyTimeOff();
		VacantionType vacantionType = vacantionTypeService.findById(details.getVacantionType().getId());

		destination.setDuration(details.getDuration());
		destination.setFromDate(details.getFromDate());
		destination.setToDate(details.getToDate());

		vacantionType.setYearlyTimeOff(destination);
		destination.setVacantionType(vacantionType);

		return destination;
	}

	@Override
	public void validateTimeOffDetails(YearlyTimeOffDto timeOffDetails) {
		
		if (timeOffDetails.getVacantionType() == null) {
			String message = "Please select an Existing Vacantion Type, or create a new one";
			throw new BadRequestException(message);
		}
		
		List<YearlyTimeOffDto> list = this.getAllTimeOff();

		Date startDate1 = timeOffDetails.getFromDate();
		Date endDate1 = timeOffDetails.getToDate();

		Iterator<YearlyTimeOffDto> iterator = list.iterator();
		while (iterator.hasNext()) {
			YearlyTimeOffDto element = iterator.next();

			// throw error: if dates interval are equal
			if ((startDate1.compareTo(element.getFromDate()) == 0) && endDate1.compareTo(element.getToDate()) == 0) {
				throw new BadRequestException("ERROR: There is already a request for the interval : "
						+ startDate1.toString() + " and " + endDate1.toString());
				// or if there is already a date that include this one
			} else if ((startDate1.compareTo(element.getToDate()) <= 0)
					&& (element.getFromDate().compareTo(endDate1) <= 0)) {
				throw new BadRequestException(
						"ERROR: This request overlaps with another request, that request has the interval : "
								+ element.getFromDate().toString() + " and " + element.getToDate().toString()
								+ " please request a new interval that not overlaps with this one");
				// or startDate1 must be less than endDate
			} else if ((startDate1.compareTo(endDate1)) > 0) {
				throw new BadRequestException("ERROR: fromDate must be after endDate");
			}

		}
	}
}
