package com.redhat.coolstore.productcataloglab1;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.redhat.coolstore.productcataloglab1.service.ProductCatalogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ProductCatalogLab1ApplicationTests {

  @Autowired
  private ProductCatalogService productCatalogService;

  @Test
  public void testDefaultProductList() {
    String message = productCatalogService.sayHello();
    assertTrue(message != null);
    assertEquals(message, "Hey Developer!");
  }
}
