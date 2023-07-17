package com.flurent.dto.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.flurent.domain.Car;
import com.flurent.domain.ImageFile;
import com.flurent.dto.CarDTO;

@Mapper(componentModel = "spring")
public interface CarMapper {

	// CarMapper INSTANCE = Mappers.getMapper(CarMapper.class); /* Wenn wir nicht
	// anotation benutzen möchten, können wir diese kode benutzen */

	@Mapping(target = "image", ignore = true)
	Car carDTOToCar(CarDTO carDTO);

	@Mapping(source = "image", target = "image", qualifiedByName = "getImageAsString")
	CarDTO carTOCarDTO(Car car);

	@Named("getImageAsString")
	public static Set<String> getImageId(Set<ImageFile> images) {
		Set<String> imageStrSet = new HashSet<>();
		imageStrSet = images.stream().map(image -> image.getId().toString()).collect(Collectors.toSet());
		return imageStrSet;
	}

	List<CarDTO> map(List<Car> cars);

}
