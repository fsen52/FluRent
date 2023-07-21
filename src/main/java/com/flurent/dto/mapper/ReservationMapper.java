package com.flurent.dto.mapper;

import org.mapstruct.Mapper;

import com.flurent.domain.Reservation;
import com.flurent.dto.request.ReservationRequest;

@Mapper(componentModel="spring")
public interface ReservationMapper {
	

	Reservation reservationRequestToReservation(ReservationRequest reservationRequest);
	
}
