package com.omega.stocksync.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import com.omega.stocksync.controller.impl.ProductControllerImpl;
import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.service.ProductService;
import com.omega.stocksync.service.StockSyncService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
class ProductControllerTests {

  private ProductController productController;
  private StockSyncService stockSyncService;
  private ProductService productService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    stockSyncService = mock(StockSyncService.class);
    productService = mock(ProductService.class);
    productController = new ProductControllerImpl(stockSyncService, productService);
  }

  @Test
  void testTriggerSync() {
    doNothing().when(stockSyncService).syncAllVendors();
    ResponseEntity<String> response = productController.triggerSync();
    verify(stockSyncService).syncAllVendors();
    assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status code should be OK");
  }

  @Test
  void testGetAllProducts() throws Exception {
    ProductDto product1 = new ProductDto("SKU123", "Widget A", 10, "Vendor A");
    ProductDto product2 = new ProductDto("SKU124", "Gizmo B", 20, "Vendor");
    List<ProductDto> mockProducts = Arrays.asList(product1, product2);
    when(productService.getAllProducts()).thenReturn(mockProducts);
    ResponseEntity<List<ProductDto>> response = productController.getAllProducts();
    verify(productService).getAllProducts();
    assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status code should be OK");
    assertNotNull("The response body should not be null", response.getBody());
  }
}
