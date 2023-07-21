package com.flurent.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flurent.domain.User;
import com.flurent.helper.ExcelReportHelper;
import com.flurent.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {
	
	UserRepository userRepository;
	
	
	public ByteArrayInputStream createUserReport() throws IOException{
		List<User> users = userRepository.findAll();
		
		return ExcelReportHelper.createUsersExcelReport(users);
		
		
	}
	
	
	
	

}
