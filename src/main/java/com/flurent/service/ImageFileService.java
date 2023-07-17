package com.flurent.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.flurent.domain.ImageFile;
import com.flurent.dto.ImageFileDTO;
import com.flurent.exception.ImageFileException;
import com.flurent.exception.ResourceNotFoundException;
import com.flurent.exception.message.ErrorMessage;
import com.flurent.repository.ImageFileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageFileService {

	private ImageFileRepository imageFileRepository;

	public String saveImage(MultipartFile file) {

		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

		ImageFile imageFile = null;
		try {
			imageFile = new ImageFile(fileName, file.getContentType(), file.getBytes());
		} catch (IOException e) {

			throw new ImageFileException(e.getMessage());

		}

		imageFileRepository.save(imageFile);

		return imageFile.getId();
	}

	public ImageFile getImageFileById(String id) {
		ImageFile image = imageFileRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));

		return image;
	}

	public List<ImageFileDTO> getAllImages() {
		List<ImageFile> imageList = imageFileRepository.findAll();

		List<ImageFileDTO> files = imageList.stream().map(imFile -> {

			String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
					.path(imFile.getId()).toUriString();

			return new ImageFileDTO(imageUri, imFile.getName(), imFile.getType(), imFile.getData().length);
		}
		).collect(Collectors.toList());

		return files;
	}

}
