package com.flurent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flurent.domain.Car;
import com.flurent.domain.ImageFile;
import com.flurent.dto.CarDTO;
import com.flurent.dto.mapper.CarMapper;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.CarRepository;
import com.flurent.repository.ImageFileRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarService {

	private CarRepository carRepository;

	private ImageFileRepository imageFileRepository;

	private CarMapper carMapper;

	@Transactional(readOnly = true)
	public List<CarDTO> getAllCars() {
		List<Car> carList = carRepository.findAll();

		return carMapper.map(carList);
	}

	@Transactional(readOnly = true)
	public CarDTO getCarById(Long id) {

		Car car = carRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

		return carMapper.carTOCarDTO(car);

	}

	public void saveCar(CarDTO carDTO, String imageId) {
		ImageFile imFile = imageFileRepository.findById(imageId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));

		Car car = carMapper.carDTOToCar(carDTO);

		Set<ImageFile> imFiles = new HashSet<>();
		imFiles.add(imFile);

		car.setImage(imFiles);

		carRepository.save(car);

	}

}
