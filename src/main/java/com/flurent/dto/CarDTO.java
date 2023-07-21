package com.flurent.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.flurent.domain.Car;
import com.flurent.domain.ImageFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

	private Long carId;

	@Size(max = 30, message = "Size is exceeded")
	@NotNull(message = "Please provide car model")
	private String model;

	@NotNull(message = "Please provide car doors info")
	private Integer doors;

	@NotNull(message = "Please provide car seats info")
	private Integer seats;

	@NotNull(message = "Please provide car luggage info")
	private Integer luggage;

	@Size(max = 30, message = "Size is exceeded")
	@NotNull(message = "Please provide car transmission info")
	private String transmission;

	@NotNull(message = "Please provide car air conditioning info")
	private Boolean airConditioning;

	@NotNull(message = "Please provide car year info")
	private Integer year;

	@NotNull(message = "Please provide car price per hour")
	private Double pricePerHour;

	@Size(max = 30, message = "Size is exceeded")
	@NotNull(message = "Please provide car fuel type info")
	private String fuelType;

	private Boolean builtIn = false;

	private Set<String> image;

	public CarDTO(Car car) {
		this.carId = car.getId();
		this.model = car.getModel();
		this.doors = car.getDoors();
		this.seats = car.getSeats();
		this.luggage = car.getLuggage();
		this.transmission = car.getTransmission();
		this.airConditioning = car.getAirConditioning();
		this.year = car.getYear();
		this.pricePerHour = car.getPricePerHour();
		this.fuelType = car.getFuelType();
		this.builtIn = car.getBuiltIn();
		this.image = getImageId(car.getImage());
	}

	public Set<String> getImageId(Set<ImageFile> images) {

		Set<String> imageStrSet = new HashSet<>();

		imageStrSet = images.stream().map(image -> image.getId().toString()).collect(Collectors.toSet());

		return imageStrSet;
	}

}
