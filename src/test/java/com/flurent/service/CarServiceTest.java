package com.flurent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flurent.domain.Car;
import com.flurent.dto.CarDTO;
import com.flurent.dto.mapper.CarMapper;
import com.flurent.repository.CarRepository;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

	
	@Mock
	CarRepository carRepository;
	
	@Mock
	CarMapper carMapper;
	
	@InjectMocks
	CarService carService;
	
	
	@Test
	public void getAllCarTest() {
		
		List<Car> list= new ArrayList<>();
		
		Car car1 = new Car();
		car1.setId(1L);
		car1.setModel("Audi");
		

		Car car2 = new Car();
		car2.setId(1L);
		car2.setModel("Audi");
	

		Car car3 = new Car();
		car3.setId(1L);
		car3.setModel("Audi");	
		
		list.add(car1);
		list.add(car2);
		list.add(car3);
		
		
	List<CarDTO> listDTO= new ArrayList<>();
		
		CarDTO carDTO1 = new CarDTO();
		carDTO1.setCarId(1L);
		carDTO1.setModel("Audi");
		

		CarDTO carDTO2 = new CarDTO();
		carDTO2.setCarId(1L);
		carDTO2.setModel("Audi");
	

		CarDTO carDTO3 = new CarDTO();
		carDTO3.setCarId(1L);
		carDTO3.setModel("Audi");	
		
		listDTO.add(carDTO1);
		listDTO.add(carDTO2);
		listDTO.add(carDTO3);
		
		
		when(carRepository.findAll()).thenReturn(list);
		
		when(carMapper.map(list)).thenReturn(listDTO);

		List<CarDTO> actualList = carService.getAllCars();
		
		assertEquals(3, actualList.size());
		
		verify(carRepository,times(1)).findAll();

	
	}
	
}
