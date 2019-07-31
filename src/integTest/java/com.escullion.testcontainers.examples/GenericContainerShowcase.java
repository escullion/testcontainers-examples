package com.escullion.testcontainers.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

public class GenericContainerShowcase {

  @ClassRule
  public static GenericContainer redis = new GenericContainer("redis:3.0.2")
          .withExposedPorts(6379);

  @Test
  public void testRedisCacheSetup() {
    List<Integer> exposedPorts = redis.getExposedPorts();
    assertThat(exposedPorts.get(0)).isEqualTo(6379);
    assertThat(redis.isRunning()).isTrue();
  }
}
