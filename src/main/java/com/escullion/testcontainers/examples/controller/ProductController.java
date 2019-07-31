package com.escullion.testcontainers.examples.controller;

import com.escullion.testcontainers.examples.model.Product;
import com.escullion.testcontainers.examples.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

  private final ProductRepository repo;

  @GetMapping
  public ResponseEntity findAll() {
    List<Product> response = repo.findAll();
    return ResponseEntity.ok(response);
  }

  @GetMapping("{id}")
  public ResponseEntity findById(@PathVariable Long id) {
    Optional<Product> response = repo.findById(id);
    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity create(@RequestBody Product product) {
    Product response = repo.save(product);
    return ResponseEntity.ok(response);
  }

  @GetMapping("search")
  public ResponseEntity findByName(@RequestParam String name) {
    Optional<Product> response =repo.findByName(name);
    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
