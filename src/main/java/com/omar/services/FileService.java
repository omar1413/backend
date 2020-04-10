package com.omar.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.omar.errors.FileStorageException;

@Service
public class FileService {

	@Value("${app.upload.dir:${user.home}}")
	public String userDirectory;

	private String uploadDirectory = userDirectory + File.separator + "images";

	public String uploadFile(MultipartFile file) throws FileStorageException {

		uploadDirectory = userDirectory + File.separator + "images";

		try {
			Path copyLocation = Paths
					.get(uploadDirectory + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

			return copyLocation.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileStorageException(
					"Could not store file " + file.getOriginalFilename() + ". Please try again!");
		}
	}

}
