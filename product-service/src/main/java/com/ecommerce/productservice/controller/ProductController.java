package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductDetailsDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto addedProduct = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,
                                                    @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetailsDto> getProductDetailsById(@PathVariable Long productId) {
        ProductDetailsDto productDetails = productService.getProductDetailsById(productId);
        return ResponseEntity.ok(productDetails);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
        List<ProductDto> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDto>> filterProductsByCategory(@RequestParam Long categoryId) {
        List<ProductDto> products = productService.filterProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}/stock")
    public ResponseEntity<ProductDto> updateStock(@PathVariable Long productId,
                                                  @RequestParam int quantity) {
        ProductDto updatedProduct = productService.updateStock(productId, quantity);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductDto> updatePrice(@PathVariable Long productId,
                                                  @RequestParam Double price) {
        ProductDto updatedProduct = productService.updatePrice(productId, price);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
