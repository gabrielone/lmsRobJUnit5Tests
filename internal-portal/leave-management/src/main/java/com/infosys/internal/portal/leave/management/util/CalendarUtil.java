package com.infosys.internal.portal.leave.management.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.infosys.internal.portal.leave.management.dto.YearlyTimeOffDto;

public class CalendarUtil {

	public static int countOverlapingDays(List<YearlyTimeOffDto> list, Date startDate1, Date endDate1) {
		Iterator<YearlyTimeOffDto> iterator = list.iterator();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		int count = 0;
		while(iterator.hasNext()) {
			YearlyTimeOffDto element = iterator.next();
			Instant instantFromDate = element.getFromDate().toInstant();
			Instant instantToDate = element.getToDate().toInstant();
			LocalDate localDateFromDate = instantFromDate.atZone(defaultZoneId).toLocalDate();
			LocalDate localDateToDate = instantToDate.atZone(defaultZoneId).toLocalDate();
			List<LocalDate> totalDates =  localDateFromDate.datesUntil(localDateToDate)
					  .collect(Collectors.toList());
			totalDates.add(localDateToDate);
			
			Instant instantDateStart1 = startDate1.toInstant();
			LocalDate localDateStart1 = instantDateStart1.atZone(defaultZoneId).toLocalDate();
			Instant instantDateEnd1 = endDate1.toInstant();
			LocalDate localDateEnd1 = instantDateEnd1.atZone(defaultZoneId).toLocalDate();
			
			for(LocalDate localDate : totalDates) {
				if(!(localDate.isBefore(localDateStart1) || localDate.isAfter(localDateEnd1))) {
					count++;
				}
			}
		}
		return count;
	}
}
