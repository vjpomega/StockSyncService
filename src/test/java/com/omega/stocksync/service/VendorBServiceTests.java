package com.omega.stocksync.service;

import static org.junit.jupiter.api.Assertions.*;

import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.service.impl.VendorBServiceImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class VendorBServiceTests {

  @TempDir Path tempDir;
  private VendorBServiceImpl vendorBService;
  private String csvFilePath;

  @BeforeEach
  void setUp() {
    vendorBService = new VendorBServiceImpl();
  }

  @Test
  void testFetch_Success_WithValidCsv() throws IOException {
    csvFilePath =
        createTestCsvFile(
            "sku,name,stockQuantity\n"
                + "SKU001,Product A,100\n"
                + "SKU002,Product B,50\n"
                + "SKU003,Product C,200\n");
    ReflectionTestUtils.setField(vendorBService, "vendorBPath", csvFilePath);

    List<ProductDto> products = vendorBService.fetch();

    assertNotNull(products);
    assertEquals(3, products.size());

    ProductDto product1 = products.get(0);
    assertEquals("SKU001", product1.sku());
    assertEquals("Product A", product1.name());
    assertEquals(100, product1.stockQuantity());
    assertEquals("VENDOR_B", product1.vendorName());

    ProductDto product2 = products.get(1);
    assertEquals("SKU002", product2.sku());
    assertEquals("Product B", product2.name());
    assertEquals(50, product2.stockQuantity());
    assertEquals("VENDOR_B", product2.vendorName());

    ProductDto product3 = products.get(2);
    assertEquals("SKU003", product3.sku());
    assertEquals("Product C", product3.name());
    assertEquals(200, product3.stockQuantity());
    assertEquals("VENDOR_B", product3.vendorName());
  }

  @Test
  void testFetch_ThrowsException_WhenFileNotFound() {
    String nonExistentPath = tempDir.resolve("non-existent.csv").toString();
    ReflectionTestUtils.setField(vendorBService, "vendorBPath", nonExistentPath);

    assertThrows(IllegalStateException.class, () -> vendorBService.fetch());
  }

  @Test
  void testFetch_ThrowsException_WhenInvalidNumberFormat() throws IOException {
    csvFilePath =
        createTestCsvFile("SKU,Name,Stock Quantity\n" + "SKU001,Product A,INVALID_NUMBER\n");
    ReflectionTestUtils.setField(vendorBService, "vendorBPath", csvFilePath);

    assertThrows(RuntimeException.class, () -> vendorBService.fetch());
  }

  private String createTestCsvFile(String content) throws IOException {
    File csvFile = tempDir.resolve("test-vendor-b.csv").toFile();
    try (FileWriter writer = new FileWriter(csvFile)) {
      writer.write(content);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return csvFile.getAbsolutePath();
  }
}
