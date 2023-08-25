package com.flurent.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flurent.domain.Car;
import com.flurent.domain.Reservation;
import com.flurent.domain.User;
import com.flurent.helper.ExcelReportHelper;
import com.flurent.repository.CarRepository;
import com.flurent.repository.ReservationRepository;
import com.flurent.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {
	
	UserRepository userRepository;
	
	CarRepository carRepository;	
	
	ReservationRepository	reservationRepository;
	
	
	public ByteArrayInputStream createUserReport() throws IOException{
		List<User> users = userRepository.findAll();
		
		return ExcelReportHelper.createUsersExcelReport(users);
		
		
	}
	
	
	public ByteArrayInputStream createCarReport() throws IOException{
		List<Car> cars = carRepository.findAll();
		
		return ExcelReportHelper.createCarsExcelReport(cars);
		
		
	}
	
	public ByteArrayInputStream createReservationReport() throws IOException{
		List<Reservation> reservations = reservationRepository.findAll();
		
		return ExcelReportHelper.createReservationsExcelReport(reservations);
		
		
	}
	
	

}
