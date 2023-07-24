package com.flurent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flurent.domain.Car;
import com.flurent.domain.ImageFile;
import com.flurent.dto.CarDTO;
import com.flurent.dto.mapper.CarMapper;
import com.flurent.exception.BadRequestException;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.CarRepository;
import com.flurent.repository.ImageFileRepository;
import com.flurent.repository.ReservationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarService {

	private CarRepository carRepository;

	private ImageFileRepository imageFileRepository;

	private CarMapper carMapper;

	private ReservationRepository reservationRepository;
	
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

	@Transactional(readOnly = true)
	public Page<CarDTO> findAllWithPage(Pageable pageable) {
		return carRepository.findAllCarWithPage(pageable);
	}

	/**
	 * Wir benutzen " /** " für Javadoc.
	 * 
	 * @param id
	 * @param imageId
	 * @param carDTO
	 */
	
	@Transactional
	public void updateCar(Long id, String imageId, CarDTO carDTO) {

		Car foundCar = carRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

		ImageFile imFile = imageFileRepository.findById(imageId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));

		if (foundCar.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_PROCESS_MESSAGE);
		}

		Set<ImageFile> imgs = foundCar.getImage();
		imgs.add(imFile);

		Car newCar = carMapper.carDTOToCar(carDTO);
		
		newCar.setId(foundCar.getId());
		newCar.setImage(imgs);

		carRepository.save(newCar);

	}

	public void removeById(Long id) {
		
		Car foundCar = carRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		
		boolean exists = reservationRepository.existsByCar(foundCar);
		
		if(exists) {
			throw new BadRequestException(ErrorMessage.CAR_USED_BY_RESERVATION_MESSAGE);
		}
		
		
		if (foundCar.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_PROCESS_MESSAGE);
		}
		
		carRepository.deleteById(id);
		
	}
	
	
}
