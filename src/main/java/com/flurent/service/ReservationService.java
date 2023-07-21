package com.flurent.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flurent.domain.Car;
import com.flurent.domain.Reservation;
import com.flurent.domain.User;
import com.flurent.domain.enums.ReservationStatus;
import com.flurent.dto.ReservationDTO;
import com.flurent.dto.mapper.ReservationMapper;
import com.flurent.dto.request.ReservationRequest;
import com.flurent.exception.BadRequestException;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.CarRepository;
import com.flurent.repository.ReservationRepository;
import com.flurent.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReservationService {

	private ReservationRepository reservationRepository;

	private CarRepository carRepository;

	private UserRepository userRepository;

	private ReservationMapper reservationMapper;

	@Transactional(readOnly = true)
	public List<ReservationDTO> getAllReservations() {
		return reservationRepository.findAllBy();
	}

	@Transactional(readOnly = true)
	public ReservationDTO findReservationById(Long id) {
		ReservationDTO reservationDTO = reservationRepository.findReservationDTOById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return reservationDTO;
	}

	public void createReservation(ReservationRequest reservationRequest, Long userId, Long carId) {

		checkReservationTimeCorrect(reservationRequest.getPickUpTime(), reservationRequest.getDropOffTime());

		Car car = carRepository.findById(carId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, carId)));

		boolean carStatus = checkCarAvailability(carId, reservationRequest.getPickUpTime(),
				reservationRequest.getDropOffTime());

		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, userId)));

		Reservation reservation = reservationMapper.reservationRequestToReservation(reservationRequest);

		if (!carStatus) {
			reservation.setStatus(ReservationStatus.CREATED);
		} else {
			throw new BadRequestException(ErrorMessage.CAR_NOT_AVAILABLE_MESSAGE);
		}

		reservation.setCar(car);
		reservation.setUser(user);
		Double totalPrice = getTotalPrice(car, reservationRequest.getPickUpTime(), reservationRequest.getDropOffTime());

		reservation.setTotalPrice(totalPrice);

		reservationRepository.save(reservation);
	}

	private Double getTotalPrice(Car car, LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
		Long hours = (new Reservation()).getTotalHours(pickUpTime, dropOffTime);

		return car.getPricePerHour() * hours;
	}

	public boolean checkCarAvailability(Long carId, LocalDateTime pickUpTime, LocalDateTime dropOffTime) {

		ReservationStatus[] status = { ReservationStatus.CANCELLED, ReservationStatus.DONE };

		List<Reservation> existReservations = reservationRepository.checkCarStatus(carId, pickUpTime, dropOffTime,
				status);

		return !existReservations.isEmpty();
	}

	private void checkReservationTimeCorrect(LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
		LocalDateTime now = LocalDateTime.now();

		if (pickUpTime.isBefore(now)) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}

		boolean isEqual = pickUpTime.isEqual(dropOffTime) ? true : false;
		boolean isBefore = pickUpTime.isBefore(dropOffTime) ? true : false;

		if (isEqual || !isBefore) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}

	}

}
