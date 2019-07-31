package com.escullion.testcontainers.examples;

import java.io.File;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class DockerComposeShowcase {

  @ClassRule
  public static DockerComposeContainer environment = new DockerComposeContainer(
      new File("src/integTest/resources/docker-compose-backend.yml"))
      .withExposedService("db", 21017)
      .withExposedService("cache", 6379)
      .withExposedService("search", 9200)
      .waitingFor("db", Wait.forHttp("/readiness").forPort(32881));

  @Test
  public void testComponentsSetup() {
    environment.getServiceHost("db", 21017);
    environment.getServiceHost("cache", 6379);
    environment.getServiceHost("search", 9200);
  }
}
