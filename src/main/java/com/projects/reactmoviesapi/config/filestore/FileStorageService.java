package com.projects.reactmoviesapi.config.filestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projects.reactmoviesapi.exceptions.StorageException;

@Service
public class FileStorageService implements IFileStorageService {

  private final Path rootDir;

  public FileStorageService(StorageProperties properties) {
    this.rootDir = Paths.get(properties.getDestination());
  }

  @Override
  public String store(MultipartFile file) {
    if (file.isEmpty()) {
      throw new StorageException("Failed to store empty file");
    }
    String fileName = this.generateFileName(file.getOriginalFilename());
    Path destinationFile = rootDir.resolve(fileName);
    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = rootDir.resolve(filename).normalize();
      Resource resource = new UrlResource(file.toUri());

      if (!resource.exists() && !resource.isReadable()) {
        throw new StorageException("Could not read the file!");
      }

      return resource;
    } catch (MalformedURLException e) {
      throw new StorageException("Could not read the file!");
    }
  }

  @Override
  public void delete(String filename) {
    try {
      Path file = rootDir.resolve(filename);
      Files.deleteIfExists(file);
    } catch (IOException e) {
      throw new StorageException("Error: " + e.getMessage());
    }
  }

  private String generateFileName(String originalName) {
    return UUID.randomUUID().toString() + "_" + originalName;
  }
}
