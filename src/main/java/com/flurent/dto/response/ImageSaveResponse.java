package com.flurent.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageSaveResponse extends FlurentResponse {

	private String imageId;
	
	public ImageSaveResponse(String imageId, String message, boolean success) {
			super(success,message);
			this.imageId=imageId;
	
	
	}
	
	
	
	
	
	
}
