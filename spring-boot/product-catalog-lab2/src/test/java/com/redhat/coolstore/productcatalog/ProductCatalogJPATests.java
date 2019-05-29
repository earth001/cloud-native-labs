package com.redhat.coolstore.productcatalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductCatalogJPATests {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void testFindAll() {
    List<Product> productList = productRepository.findAll();
    assertEquals(productList.size(), 8);
  }

  @Test
  public void testFindByName() {
    Product product = productRepository.findByName("Oculus Rift");
    assertTrue(444435L == product.getItemId());
  }

  @Test
  public void testSaveAndDeleteProduct() {

    Product newProduct = new Product();
    newProduct.setName("Test Prod");
    newProduct.setDescription("This is a description");
    newProduct.setPrice(10.00d);

    Product product = productRepository.save(newProduct);
    long id = product.getItemId();

    assertNotEquals(Optional.empty(), productRepository.findById(id));

    productRepository.delete(product);

    assertEquals(Optional.empty(), productRepository.findById(id));
  }
}
