package com.redhat.coolstore.productcatalog.service;

import com.redhat.coolstore.productcatalog.Product;
import com.redhat.coolstore.productcatalog.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCatalogService {

  @Value("${coolstore.message:Hello World!}")
  private String message;

  private ProductRepository productRepository;

  public ProductCatalogService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/products")
  public List<Product> list() {
    return productRepository.findAll();
  }
}
