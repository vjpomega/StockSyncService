package com.omega.stocksync.controller;

import com.omega.stocksync.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Product API")
public interface ProductController {

  @Operation(
      summary = "Trigger manual sync",
      description = "Manually trigger stock synchronization from all vendors")
  ResponseEntity<String> triggerSync();

  @Operation(
      summary = "Get all products",
      description = "Returns all products with their current stock levels")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returned All products across multiple vendors",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductDto.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid Params", content = @Content),
        @ApiResponse(
            responseCode = "403",
            description = "Request not Authorize",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content),
      })
  ResponseEntity<List<ProductDto>> getAllProducts();
}
