package com.omar.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.omar.errors.FileStorageException;

@Service
public class FileSystemStorageService implements StorageService {

	@Qualifier("rootPath")
	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(Path rootPath) {
		this.rootLocation = rootPath;
		init();
	}

	@Override
	public String store(MultipartFile file) {
		// String filename = StringUtils.cleanPath(file.getOriginalFilename());
		String filename = UUID.randomUUID() + ".jpg";
		try {
			if (file.isEmpty()) {
				throw new FileStorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new FileStorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
				return filename;
			}
		} catch (IOException e) {
			throw new FileStorageException("Failed to store file " + filename);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new FileStorageException("Failed to read stored files");
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new FileStorageException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new FileStorageException("Could not read file: " + filename);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new FileStorageException("Could not initialize storage");
		}
	}
}
