package com.escullion.testcontainers.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

public class ImageFromDockerFileShowcase {

  @Rule
  public GenericContainer genericContainer =
      new GenericContainer(new ImageFromDockerfile()
          .withDockerfileFromBuilder(builder ->
              builder
                  .from("alpine:3.2")
                  .run("apk add --update nginx")
                  .cmd("nginx", "-g", "daemon off;")
                  .build()))
          .withExposedPorts(80);

  @Test
  public void testGenericContainerSetup() {
    List<Integer> exposedPorts = genericContainer.getExposedPorts();
    assertThat(exposedPorts.get(0)).isEqualTo(80);
    assertThat(genericContainer.isRunning()).isTrue();
  }

}
