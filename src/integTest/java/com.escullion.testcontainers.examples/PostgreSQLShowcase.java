package com.escullion.testcontainers.examples;

import static org.assertj.core.api.Assertions.assertThat;

import com.escullion.testcontainers.examples.model.Product;
import com.escullion.testcontainers.examples.repository.ProductRepository;
import java.util.Optional;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgreSQLShowcase.Initializer.class})
public class PostgreSQLShowcase {

  @Autowired
  private ProductRepository repo;

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11")
      .withDatabaseName("product_db")
      .withUsername("postgres")
      .withPassword("postgres");

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgreSQLContainer.getUsername(),
          "spring.datasource.password=" + postgreSQLContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }

  @Test
  public void whenFindByNameCalledThenExpectProductReturned() {
    // Inserted by flyway automatically
    Optional<Product> products = repo.findByName("product1");
    assertThat(products).isPresent();
  }

  @Test
  public void whenSaveCalledAndFindByIdCalledThenExpectProductsReturned() {
    Product product = Product.builder()
        .id(2L)
        .name("product2")
        .description("example")
        .quantity(2)
        .build();

    Product createdProduct = repo.save(product);
    assertThat(createdProduct.getId()).isNotNull();

    Optional<Product> existingProduct = repo.findById(createdProduct.getId());
    assertThat(existingProduct).isPresent();
  }




}
