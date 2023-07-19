package com.flurent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flurent.domain.Car;
import com.flurent.dto.CarDTO;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	@Query("SELECT new com.flurent.dto.CarDTO(car) FROM Car car")
	Page<CarDTO> findAllCarWithPage(Pageable pageable);
}
