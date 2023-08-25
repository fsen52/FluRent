package com.flurent.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flurent.domain.ImageFile;
import com.flurent.dto.ImageFileDTO;
import com.flurent.dto.response.ImageSaveResponse;
import com.flurent.dto.response.ResponseMessage;
import com.flurent.service.ImageFileService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class ImageFileController {

	private ImageFileService imageFileService;
	
	
	
	@PostMapping("/upload")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ImageSaveResponse> uploadFile(@RequestParam("file") MultipartFile file  ){
		
		String imageId= imageFileService.saveImage(file);
		
		ImageSaveResponse response = new ImageSaveResponse();
		
		response.setImageId(imageId);
		response.setMessage(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
		
	}
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> getImageFileById(@PathVariable String id){
		ImageFile image = imageFileService.getImageFileById(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+
		image.getName()).body(image.getData());
	}
	
	
	@GetMapping("/display/{id}")
	public ResponseEntity<byte[]> displayImageFileById(@PathVariable String id){
		ImageFile image = imageFileService.getImageFileById(id);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_JPEG);
		
		return new ResponseEntity<>(image.getData(),header,HttpStatus.OK);	
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ImageFileDTO>> getAllImages(){
		
		List<ImageFileDTO> imageList = imageFileService.getAllImages();
		
		return ResponseEntity.status(HttpStatus.OK).body(imageList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
