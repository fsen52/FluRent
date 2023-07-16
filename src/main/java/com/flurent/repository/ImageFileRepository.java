package com.flurent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flurent.domain.ImageFile;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, String> {

	
	
	
	
	
}
