package com.flurent.dto;

import java.time.LocalDateTime;

import com.flurent.domain.Reservation;
import com.flurent.domain.enums.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
	private Long id;

	private CarDTO car;

	private Long userId;

	private LocalDateTime pickUpTime;

	private LocalDateTime dropOffTime;

	private String pickUpLocation;

	private String dropOffLocation;

	private ReservationStatus status;

	private Double totalPrice;

	public ReservationDTO(Reservation reservation) {
		this.id = reservation.getId();
		this.car = new CarDTO(reservation.getCar());
		this.userId = reservation.getUser().getId();
		this.pickUpTime = reservation.getPickUpTime();
		this.dropOffTime = reservation.getDropOffTime();
		this.pickUpLocation = reservation.getPickUpLocation();
		this.dropOffLocation = reservation.getDropOffLocation();
		this.status = reservation.getStatus();
		this.totalPrice = reservation.getTotalPrice();
	}

}
