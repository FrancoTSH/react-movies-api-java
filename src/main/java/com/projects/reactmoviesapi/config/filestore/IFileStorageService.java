package com.projects.reactmoviesapi.config.filestore;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
  public String store(MultipartFile file);

  public Resource load(String path);

  public void delete(String path);
}
