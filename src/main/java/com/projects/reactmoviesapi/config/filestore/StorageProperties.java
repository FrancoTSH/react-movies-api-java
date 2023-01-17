package com.projects.reactmoviesapi.config.filestore;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

  private String destination;
}
