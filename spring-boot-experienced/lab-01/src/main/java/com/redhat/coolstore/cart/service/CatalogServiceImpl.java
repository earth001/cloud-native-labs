package com.redhat.coolstore.cart.service;

import com.redhat.coolstore.cart.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CatalogServiceImpl implements CatalogService {

  @Value("${catalog.service.url}")
  private String catalogServiceUrl;

  @Override
  public Product getProduct(String itemId) {
    // create rest template
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Product> entity;
    try {
      // call remote catalog service
      entity = restTemplate.getForEntity(catalogServiceUrl + "/product/" + itemId, Product.class);
      return entity.getBody();
    } catch (HttpClientErrorException e) {
      if (e.getRawStatusCode() == 404) {
        return null;
      } else {
        throw e;
      }
    }
  }

}
