package com.omega.stocksync.service.impl;

import static com.omega.stocksync.constants.CsvConstants.*;
import static com.omega.stocksync.constants.ErrorConstants.ERR_CSV_NOT_FOUND;
import static com.omega.stocksync.constants.ErrorConstants.ERR_FAILED_TO_READ_CSV;

import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.service.VendorService;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("vendorBService")
public class VendorBServiceImpl implements VendorService {

  @Value("${vendor.b.path}")
  private String vendorBPath;

  @Override
  public List<ProductDto> fetch() {
    List<ProductDto> products = new ArrayList<>();

    try {
      if (!Files.exists(Paths.get(vendorBPath))) {
        throw new IllegalStateException(ERR_CSV_NOT_FOUND + vendorBPath);
      }

      try (Reader reader = new FileReader(vendorBPath);
          CSVParser csvParser =
              CSVFormat.DEFAULT
                  .builder()
                  .setHeader()
                  .setSkipHeaderRecord(true)
                  .setIgnoreHeaderCase(true)
                  .setTrim(true)
                  .build()
                  .parse(reader)) {

        for (CSVRecord row : csvParser) {
          String sku = row.get(COLUMN_SKU);
          String name = row.get(COLUMN_NAME);
          Integer stockQuantity = Integer.parseInt(row.get(COLUMN_STOCK_QTY));

          products.add(new ProductDto(sku, name, stockQuantity));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(ERR_FAILED_TO_READ_CSV + vendorBPath, e);
    }

    return products;
  }
}
