package com.wissen.dta.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Utils {
	private static final Log logger = LogFactory.getLog(Utils.class);
	@Value("${image.path}")
	private String assetsBasePath;

	public String getImageByUuid(String uuid, String ppoId) {
		String path = this.assetsBasePath + "/InputImages/images/" + uuid.replace("-", "/") + "/" + ppoId + ".png";
		logger.info("Attempting to retrieve image from path: " + path);
		File imageFile = new File(path);
		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			logger.error(errorMessage);
			return null;
		}
		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			logger.info("Successfully encoded the image for Pensioner ID: " + ppoId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + ppoId;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	public static String localDateTimeToDateTimeWithSlash(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(localDateTime);
	}
}
