package com.flurent.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flurent.dto.ReservationDTO;
import com.flurent.dto.request.ReservationRequest;
import com.flurent.dto.request.ReservationUpdateRequest;
import com.flurent.dto.response.CarAvailabilityResponse;
import com.flurent.dto.response.FlurentResponse;
import com.flurent.dto.response.ResponseMessage;
import com.flurent.service.ReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

	private ReservationService reservationService;

	@GetMapping("/admin/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllReservations() {
		List<ReservationDTO> allReservations = reservationService.getAllReservations();
		return ResponseEntity.ok(allReservations);
	}

	@GetMapping("/{id}/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
		ReservationDTO reservationDTO = reservationService.findReservationById(id);
		return ResponseEntity.ok(reservationDTO);
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> makeReservation(HttpServletRequest request,
			@RequestParam(value = "carId") Long carId, @Valid @RequestBody ReservationRequest reservationRequest) {

		Long userId = (Long) request.getAttribute("id");

		reservationService.createReservation(reservationRequest, userId, carId);

		FlurentResponse response = new FlurentResponse();

		response.setMessage(ResponseMessage.RESERVATION_SAVED_RESPONSE_MESSAGE);
		response.setSuccess(true);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@PostMapping("/add/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> adminAddReservation(@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "carId") Long carId, @Valid @RequestBody ReservationRequest reservationRequest) {

		reservationService.createReservation(reservationRequest, userId, carId);

		FlurentResponse response = new FlurentResponse();

		response.setMessage(ResponseMessage.RESERVATION_SAVED_RESPONSE_MESSAGE);
		response.setSuccess(true);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> updateReservation(@RequestParam(value = "reservationId") Long reservationId,
			@RequestParam(value = "carId") Long carId,
			@Valid @RequestBody ReservationUpdateRequest reservationUpdateRequest) {

		reservationService.reservationUpdate(reservationId, carId, reservationUpdateRequest);

		FlurentResponse response = new FlurentResponse();

		response.setMessage(ResponseMessage.RESERVATION_UPDATED_RESPONSE_MESSAGE);
		response.setSuccess(true);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/admin/auth/usres")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> adminGetReservationsByUserId(@RequestParam(value = "userId") Long userId) {
		List<ReservationDTO> reservationDTO = reservationService.findUsersReservationsByUserId(userId);
		return ResponseEntity.ok(reservationDTO);
	}

	@GetMapping("/auth")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> checkCarAvailable(@RequestParam(value = "carId") Long carId,
			@RequestParam(value = "pickUpDateTime") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime pickUpTime,
			@RequestParam(value = "dropOffDateTime") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dropOffTime) {

		boolean isNotAvailable = reservationService.checkCarAvailability(carId, pickUpTime, dropOffTime);

		double totalPrice = reservationService.getTotalPrice(carId, pickUpTime, dropOffTime);

		CarAvailabilityResponse response = new CarAvailabilityResponse(!isNotAvailable, totalPrice,
				ResponseMessage.CAR_AVAILABILITY_MESSAGE, true);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/{reservationId}/auth")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ReservationDTO> userGetReservationById(HttpServletRequest request,
																@PathVariable Long reservationId) {
		Long userId = (Long) request.getAttribute("id");

		ReservationDTO reservationDTO = reservationService.findReservationByIdAndUserId(reservationId, userId);
		return ResponseEntity.ok(reservationDTO);
	}
	
	@GetMapping("/auth/all")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getSelfReservations(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("id");

		List<ReservationDTO> reservationDTO = reservationService.findUsersReservationsByUserId(userId);
		return ResponseEntity.ok(reservationDTO);
	
	}
		

	@DeleteMapping("/admin/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")

	public ResponseEntity<FlurentResponse> deleteReservation(@PathVariable Long id) {

		reservationService.removeReservationById(id);

		FlurentResponse response = new FlurentResponse();

		response.setMessage(ResponseMessage.RESERVATION_DELETED_RESPONSE_MESSAGE);
		response.setSuccess(true);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
