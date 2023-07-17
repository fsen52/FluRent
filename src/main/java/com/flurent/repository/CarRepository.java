package com.flurent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flurent.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	

}
