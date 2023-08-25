package com.flurent.exception.message;

public class ErrorMessage {
	
	
	public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with this id %d not found";
	
	
	public final static String USER_NOT_FOUND_MESSAGE="User with this id %s not found";

	public final static String EMAIL_ALREADY_EXIST="This e-mail already exist : %s ";
	
	public final static String ROLE_NOT_FOUND_MESSAGE="Role with name %s not found";
	
	public final static String NOT_PERMITTED_PROCESS_MESSAGE="You don't have permission to change this value"; 
	
	public final static String PASSWORD_NOT_MATCHED="Your password is not matched";
	
	public final static String IMAGE_NOT_FOUND_MESSAGE="Image file with this id %s not found";

	public final static String RESERVATION_TIME_INCORRECT_MESSAGE="Reservation pick up time or drop off time is not correct";
	
	public final static String CAR_NOT_AVAILABLE_MESSAGE="Car is not available for selected time";
	
	public final static String EXCEL_REPORT_CREATION_ERROR_MESSAGE="Error occurred while generation excel report";

	public final static String CAR_USED_BY_RESERVATION_MESSAGE="Car couldn't be deleted. Because this car has a reservation";

	public final static String USER_USED_BY_RESERVATION_MESSAGE="User couldn't be deleted. Because this user has a reservation";
}
