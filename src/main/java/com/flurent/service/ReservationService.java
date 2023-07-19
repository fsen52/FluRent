package com.flurent.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.flurent.domain.Car;
import com.flurent.domain.enums.ReservationStatus;
import com.flurent.dto.request.ReservationRequest;
import com.flurent.exception.BadRequestException;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.CarRepository;
import com.flurent.repository.ReservationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReservationService {

	private ReservationRepository reservationRepository;

	private CarRepository carRepository;

	public void createReservation(ReservationRequest reservationRequest, Long userId, Long carId) {

		checkReservationTimeCorrect(reservationRequest.getPickUpTime(), reservationRequest.getDropOffTime());
	
		Car car = carRepository.findById(carId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, carId)));

	
	
	
	}
	
	public boolean checkCarAvailability(Long carId, LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
		
		ReservationStatus [] status = {ReservationStatus.CANCELLED, ReservationStatus.DONE};
		
		//TODO: von hier weitergehen
		
		return false;
	}
	
	private void checkReservationTimeCorrect(LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
		LocalDateTime now = LocalDateTime.now();
		
		if(pickUpTime.isBefore(now)) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}
		
		boolean isEqual = pickUpTime.isEqual(dropOffTime)?true:false;
		boolean isBefore = pickUpTime.isBefore(dropOffTime)?true:false;
		
		if(isEqual||!isBefore) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}
		
	}

}
