package com.flurent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flurent.dto.CarDTO;
import com.flurent.dto.response.FlurentResponse;
import com.flurent.service.CarService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {

	private CarService carService;

	@PostMapping("/admin/{imageId}/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> saveCar(@PathVariable String imageId, @Valid @RequestBody CarDTO carDTO) {
		carService.saveCar(carDTO, imageId);

		FlurentResponse response = new FlurentResponse();
		response.setMessage("Car successfully created");
		response.setSuccess(true);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/visitors/all")
	public ResponseEntity<List<CarDTO>> getAllCars() {
		List<CarDTO> carDTOs = carService.getAllCars();
		return ResponseEntity.ok(carDTOs);

	}

	@GetMapping("/visitors/{id}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
		CarDTO carDTO = carService.getCarById(id);
		return ResponseEntity.ok(carDTO);

	}

}
