package com.flurent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileDTO {
	private String url;
	
	private String name;
	
	private String type;
	
	private long size;
}
