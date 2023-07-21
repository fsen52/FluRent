package com.flurent.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flurent.dto.ReservationDTO;
import com.flurent.dto.request.ReservationRequest;
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

}
