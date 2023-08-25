package com.flurent.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flurent.domain.Reservation;
import com.flurent.dto.request.ReservationRequest;

@Mapper(componentModel="spring")
public interface ReservationMapper {
	
	@Mapping(target="car", ignore=true)
	@Mapping(target="id",ignore=true)
	@Mapping(target="status",ignore=true)
	@Mapping(target="totalPrice",ignore=true)
	@Mapping(target="user",ignore=true)
	Reservation reservationRequestToReservation(ReservationRequest reservationRequest);
	
}
