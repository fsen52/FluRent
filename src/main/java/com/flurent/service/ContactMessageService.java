package com.flurent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flurent.domain.ContactMessage;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.ContactMessageRepository;

import lombok.AllArgsConstructor;

@Service

@AllArgsConstructor
public class ContactMessageService {

	@Autowired
	private ContactMessageRepository repository;
	
	public void createContactMessage(ContactMessage contactMessage) {
		repository.save(contactMessage);
	}
	
	public List<ContactMessage> getAll(){
		return repository.findAll();
	}
	
	
	public ContactMessage getContactMessage(Long id) {
		return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
	}
}
