package com.flurent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import com.flurent.dto.CarDTO;
import com.flurent.dto.response.FlurentResponse;
import com.flurent.dto.response.ResponseMessage;
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
		response.setMessage(ResponseMessage.CAR_SAVED_RESPONSE_MESSAGE);
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

	@GetMapping("/visitors/pages")
	public ResponseEntity<Page<CarDTO>> getAllWithPage(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("sort") String prop, @RequestParam("direction") Direction direction) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

		Page<CarDTO> carPage= carService.findAllWithPage(pageable);
		
		return ResponseEntity.ok(carPage);
		
	}
	
	
	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> updateCar(@RequestParam("id") Long id, @RequestParam("imageId") String imageId, @Valid @RequestBody CarDTO carDTO ){
		
		carService.updateCar(id, imageId, carDTO);
		
		FlurentResponse response = new FlurentResponse();
		
		response.setMessage(ResponseMessage.CAR_UPDATE_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
		
	}
	
	@DeleteMapping("/admin/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FlurentResponse> deleteCarById(@PathVariable Long id){
		
		carService.removeById(id);
		
FlurentResponse response = new FlurentResponse();
		
		response.setMessage(ResponseMessage.CAR_DELETE_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
		
	}
	
	
	
	
	
	

}
